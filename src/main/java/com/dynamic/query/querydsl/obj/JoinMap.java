package com.dynamic.query.querydsl.obj;

import com.dynamic.query.querydsl.Constant;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Path;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinMap {

    private Constant.Join join;
    private EntityPath basicEntityPath;
    private Path basicObjPath;
    private EntityPath targetEntityPath;
    private Path targetObjPath;

}
