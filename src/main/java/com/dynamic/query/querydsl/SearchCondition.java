package com.dynamic.query.querydsl;

import com.querydsl.core.types.EntityPath;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchCondition {

    private EntityPath path;
    private Object param;
    private Constant.Method method;

}
