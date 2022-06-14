package com.dynamic.query.querydsl;

import com.querydsl.core.types.EntityPath;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EntityParam<C> {

    private EntityPath path;
    private Object param;
    private Object search;
    private Class<C> classNm;

}
