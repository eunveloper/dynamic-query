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
public class UserInfo {

    @Id
    private String email;

    private String password;
    private String userNm;
    private String phone;
    private String company;
    private String country;
    private String status;
    private boolean inUse;
    private String accessRoles;
    private boolean marketingAgreement;
    private int checkCount;

}
