package com.dynamic.query.querydsl.obj;

import com.dynamic.query.querydsl.Constant;
import com.querydsl.core.types.Path;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Condition {

    private Path param;
    private Object value;
    private Constant.Method method;

    public void createCondition(Path param, Object value, Constant.Method method) {
        this.param = param;
        this.value = value;
        this.method = method;
    }
}
