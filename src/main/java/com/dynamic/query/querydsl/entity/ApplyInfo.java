package com.dynamic.query.querydsl.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ApplyInfo {

    @Id
    private String applyType;

    private String applyName;
    private String description;
    private boolean inUse;
    private String mailType;
    private String contentPath;
    private String resolution;
    private boolean accountUse;
    private Integer applyOrder;

}
