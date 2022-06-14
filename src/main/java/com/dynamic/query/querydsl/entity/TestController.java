package com.dynamic.query.querydsl.entity;

import com.dynamic.query.querydsl.conf.DynamicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private DynamicRepository dynamicRepository;

    public static final QUserInfo qUser = new QUserInfo("user");
    public static final QApplyInfo qApplyInfo = new QApplyInfo("apply");

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/vnd.exem.app-v1+json")
    public Object applyByType() {
        dynamicRepository.searchAllByConditions(UserDto.class, qUser, null);
        dynamicRepository.searchAllByConditions(ApplyDto.class, qApplyInfo, null);
        return null;
    }

}
