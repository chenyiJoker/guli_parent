package com.cy.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cy.commonutils.JwtUtils;
import com.cy.commonutils.MD5;
import com.cy.educenter.entity.UcenterMember;
import com.cy.educenter.entity.vo.RegisterVo;
import com.cy.educenter.mapper.UcenterMemberMapper;
import com.cy.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.servicebase.exceptionHandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-04-09
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败");
        }

        UcenterMember member = baseMapper.selectOne(new LambdaQueryWrapper<UcenterMember>().eq(UcenterMember::getMobile, mobile));
        if (member == null) {
            throw new GuliException(20001, "手机号不存在");
        }

        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(20001, "密码错误");
        }

        if (member.getIsDisabled()) {
            throw new GuliException(20001, "用户被禁用");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "注册失败");
        }

        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "验证码错误");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<UcenterMember>().eq(UcenterMember::getMobile, mobile));
        if(count > 0) {
            throw new GuliException(20001,"手机号重复");
        }

        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);

    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        LambdaQueryWrapper<UcenterMember> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UcenterMember::getOpenid, openid);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer getRegisterCount(String day) {
        return baseMapper.getRegisterCount(day);
    }
}
