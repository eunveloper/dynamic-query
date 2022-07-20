package com.dynamic.query.querydsl.entity;

import com.dynamic.query.querydsl.Constant;
import com.dynamic.query.querydsl.conf.DynamicRepository;
import com.dynamic.query.querydsl.obj.JoinMaps;
import com.dynamic.query.querydsl.obj.SearchCondition;
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

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public String applyByType(String msg) {
        System.out.println("request in + " + msg);
        JoinMaps joinMaps = new JoinMaps();
        joinMaps
                .createMap(Constant.Join.INNER)
                .basic(qUser, qUser.email)
                .target(qApplyInfo, qApplyInfo.applyName)
                .addMap();

        SearchCondition searchCondition = new SearchCondition();
        searchCondition
                .addCondition(qUser, qUser.email, "eun7991e@ex-em.com", Constant.Method.EQ)
                .addCondition(qUser, qUser.userNm, "김은혜", Constant.Method.EQ)
                .addCondition(qUser, qUser.accessRoles , "admin", Constant.Method.EQ)
                .addCondition(qUser, qUser.checkCount, 5, Constant.Method.GT);

        dynamicRepository.searchAllByConditions(UserDto.class, qUser, joinMaps, searchCondition);
        return msg;
    }

}
