package com.dynamic.query.querydsl.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyDto {

    private String applyType;

    private String applyName;
    private String description;
    private boolean inUse;
    private String mailType;

}
