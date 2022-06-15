package com.dynamic.query.querydsl.entity;

import com.dynamic.query.querydsl.Constant;
import com.dynamic.query.querydsl.SearchCondition;
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
        UserDto userDto = new UserDto();
        userDto.setUserNm("김은혜");
        userDto.setCompany("exem");
        userDto.setInUse(true);

        UserDto userDto2 = new UserDto();
        userDto2.setCheckCount(5);

        SearchCondition searchCondition = new SearchCondition();
        searchCondition.setPath(qUser);
        searchCondition.setParam(userDto);
        searchCondition.setMethod(Constant.Method.EQ);

        SearchCondition searchCondition1 = new SearchCondition();
        searchCondition1.setPath(qUser);
        searchCondition1.setParam(userDto2);
        searchCondition1.setMethod(Constant.Method.GT);

        dynamicRepository.searchAllByConditions(UserDto.class, qUser, searchCondition, searchCondition1);
        //dynamicRepository.searchAllByConditions(ApplyDto.class, qApplyInfo, null);
        return null;
    }

}
