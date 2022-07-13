package com.dynamic.query.querydsl.obj;

import com.dynamic.query.querydsl.Constant;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Path;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchCondition {

    private Map<EntityPath, List<Condition>> conditionsMap;

    public SearchCondition addCondition(EntityPath entityPath, Path param,
                                        Object value, Constant.Method method) {
        if (conditionsMap == null) {
            this.conditionsMap = new HashMap<>();
        }

        List<Condition> conditions;
        if (conditionsMap.get(entityPath) != null) {
            conditions = conditionsMap.get(entityPath);
        } else {
            conditions = new ArrayList<>();
        }

        Condition condition = new Condition();
        condition.createCondition(param, value, method);

        conditions.add(condition);
        conditionsMap.put(entityPath, conditions);

        return this;
    }

}
