package com.dynamic.query.querydsl.conf;

import com.dynamic.query.querydsl.Constant;
import com.dynamic.query.querydsl.obj.Condition;
import com.dynamic.query.querydsl.obj.SearchCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class DynamicRepository<Response> {

    private final JPAQueryFactory queryFactory;
    private final DynamicQuery dynamicQuery;

    public List<Response> searchAllByConditions(Class<Response> response,
                                                EntityPath entityPath,
                                                SearchCondition searchCondition) {
        return searchAllByDynamicQuery(response, entityPath, searchCondition).fetch();
    }

    private JPAQuery<Response> searchAllByDynamicQuery(Class<Response> response,
                                                       EntityPath entityPath,
                                                       SearchCondition searchCondition) {
        JPAQuery<Response> query = null;
        try {
            query = queryFactory
                    .select(createSelectExpressionsQuery(response.newInstance(), entityPath))
                    .from(entityPath)
                    .where(createWhereConditionsQuery(searchCondition));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("query = " + query);
        return query;
    }

    private Expression<Response> createSelectExpressionsQuery(Response response, EntityPath entityPath) {
        ArrayList<Expression> expressions = new ArrayList<>();
        /* 조회 엔티디 리스트 만큼 Expression 을 만들어서 리스펀스 필드와 매핑되게 select 문을 구성한다. */
        expressions.addAll(createExpressionByEntityPaths(response, entityPath));

         /* Projections 방식으로 select 문을 구성한다.
            -> 카멜케이스 맞춰서 자동 매핑이라 간편하고 생성자 타입보다 타입에 의존관계가 적어 유지보수가 간단함 */
        return (Expression<Response>) Projections.fields(response.getClass(),
                expressions.toArray(new Expression[expressions.size()]));
    }

    private List<Expression> createExpressionByEntityPaths(Response response, EntityPath entityPath) {
        List<Expression> expressions = new ArrayList<>();

        /* 필드들의 이름을 가져온다. private 상태의 필드의 접근을 위해 Accessible 값을 설정해줌 */
        Map<String, Boolean> mapperMap = new HashMap<>();
        Field[] responseFields = response.getClass().getDeclaredFields();
        for (Field field : responseFields) {
            field.setAccessible(true);
            mapperMap.put(field.getName(), true);
        }

        Field[] entityFields = entityPath.getClass().getDeclaredFields();
        for (Field field : entityFields) {
            field.setAccessible(true);
            /* 사용자가 설정한 필드명과 동일한 엔티티 컬럼명과 매칭하여 Expression 객체를 리스트에 담아 사용 */
            String fieldName = field.getName();
            if (mapperMap.get(fieldName) != null) {
                try {
                    expressions.add((Expression) field.get(entityPath));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return expressions;
    }

    private Predicate createWhereConditionsQuery(SearchCondition searchCondition) {
        BooleanBuilder builder = new BooleanBuilder();
        for (EntityPath entityPath : searchCondition.getConditionsMap().keySet()) {
            List<Condition> conditions = searchCondition.getConditionsMap().get(entityPath);
            createConditionsQueryByEntityConditions(builder, entityPath, conditions);

        }

        return builder.getValue();
    }

    private void createConditionsQueryByEntityConditions(BooleanBuilder builder, EntityPath entityPath, List<Condition> conditions) {
        Map<String, Condition> mapperMap = createParamConditionMapperMap(conditions);
        Field[] entityFields = entityPath.getClass().getDeclaredFields();
        for (Field field : entityFields) {
            field.setAccessible(true);

            String fieldName = field.getName();
            Condition condition = mapperMap.get(fieldName);
            if (condition != null) {
                createConditionsQuery(builder, condition);
            }
        }
    }

    private void createConditionsQuery(BooleanBuilder builder, Condition condition) {
        Path path = condition.getParam();
        Constant.Method method = condition.getMethod();
        Object value = condition.getValue();
        if (path instanceof StringPath) {
            switch (method) {
                case EQ:
                    builder.and(dynamicQuery.eqStringParam(
                            (StringPath) path, (String) value));
                    break;
            }
        }
        if (path instanceof BooleanPath) {
            switch (method) {
                case EQ:
                    builder.and(dynamicQuery.eqBooleanParam(
                            (BooleanPath) path, (Boolean) value));
                    break;

            }
        }
        if (path instanceof NumberPath) {
            switch (method) {
                case EQ:
                    builder.and(dynamicQuery.eqIntegerParam(
                            (NumberPath) path, (Integer) value));
                    break;
                case GT:
                    builder.and(dynamicQuery.gtIntegerParam(
                            (NumberPath) path, (Integer) value));
                    break;
            }
        }
    }

    private Map<String, Condition> createParamConditionMapperMap(List<Condition> param) {
        Map<String, Condition> mapperMap = new HashMap<>();

        for (Condition condition : param) {
            mapperMap.put(condition.getParam().toString().split("\\.")[1], condition);
        }

        return mapperMap;
    }

}