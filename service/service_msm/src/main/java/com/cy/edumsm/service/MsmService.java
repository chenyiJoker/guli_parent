package com.cy.edumsm.service;

import java.util.Map;

public interface MsmService {
    boolean send(Map<String, Object> map, String phone);
}
