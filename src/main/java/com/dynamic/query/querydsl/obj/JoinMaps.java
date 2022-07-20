package com.dynamic.query.querydsl.obj;

import com.dynamic.query.querydsl.Constant;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Path;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class JoinMaps {

    private List<JoinMap> joinMaps;
    private JoinMap joinMap;

    public JoinMaps addMap() {
        this.joinMaps.add(this.joinMap);
        return this;
    }

    public JoinMaps createMap(Constant.Join join) {
        if (joinMaps == null) {
            joinMaps = new ArrayList<>();
        }

        joinMap = new JoinMap();
        joinMap.setJoin(join);
        return this;
    }

    public JoinMaps basic(EntityPath entityPath, Path path) {
        this.joinMap.setBasicObjPath(entityPath);
        this.joinMap.setBasicObjPath(path);
        return this;
    }

    public JoinMaps target(EntityPath entityPath, Path path) {
        this.joinMap.setTargetEntityPath(entityPath);
        this.joinMap.setTargetObjPath(path);
        return this;
    }
}
