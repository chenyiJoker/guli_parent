package com.cy.eduservice.entity.subject;

import lombok.Data;

import java.util.List;

@Data
public class OneSuject {
    private String id;
    private String title;
    private List<TwoSubject> children;
}
