package com.dynamic.query.querydsl.conf;

import com.dynamic.query.querydsl.Constant;
import com.dynamic.query.querydsl.SearchCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
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
                                                SearchCondition... conditions) {
        return searchAllByDynamicQuery(response, entityPath, conditions).fetch();
    }

    private JPAQuery<Response> searchAllByDynamicQuery(Class<Response> response,
                                                       EntityPath entityPath,
                                                       SearchCondition... conditions) {
        JPAQuery<Response> query = null;
        try {
            query = queryFactory
                    .select(createSelectExpressionsQuery(response.newInstance(), entityPath))
                    .from(entityPath)
                    .where(createWhereConditionsQuery(conditions));
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

    private Predicate createWhereConditionsQuery(SearchCondition... conditions) {
        BooleanBuilder builder = new BooleanBuilder();
        for (SearchCondition condition : conditions) {
            createConditionsQueryByMethod(builder, condition, condition.getMethod());
        }

        return builder.getValue();
    }

    private void createConditionsQueryByMethod(BooleanBuilder builder, SearchCondition condition, Constant.Method method) {
        Map<String, Object> mapperMap = createParamMapperMap(condition.getParam());
        EntityPath entityPath = condition.getPath();
        Field[] entityFields = entityPath.getClass().getDeclaredFields();
        for (Field field : entityFields) {
            field.setAccessible(true);
            try {
                createConditionsQuery(builder, field, field.get(entityPath), mapperMap, method);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, Object> createParamMapperMap(Object param) {
        Map<String, Object> mapperMap = new HashMap<>();

        Field[] responseFields = param.getClass().getDeclaredFields();
        for (Field field : responseFields) {
            field.setAccessible(true);
            try {
                Object value = field.get(param);
                if (value != null) {
                    mapperMap.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return mapperMap;
    }

    private void createConditionsQuery(BooleanBuilder builder, Field field, Object path, Map<String, Object> mapperMap,
                                       Constant.Method method) throws IllegalAccessException {
        String fieldName = field.getName();
        if (mapperMap.get(fieldName) != null) {
            if (path instanceof StringPath) {
                switch (method) {
                    case EQ:
                        builder.and(dynamicQuery.eqStringParam(
                                (StringPath) path, (String) mapperMap.get(fieldName)));
                        break;
                }
            }
            if (path instanceof BooleanPath) {
                switch (method) {
                    case EQ:
                        builder.and(dynamicQuery.eqBooleanParam(
                                (BooleanPath) path, (Boolean) mapperMap.get(fieldName)));
                        break;

                }
            }
            if (path instanceof NumberPath) {
                switch (method) {
                    case EQ:
                        builder.and(dynamicQuery.eqIntegerParam(
                                (NumberPath) path, (Integer) mapperMap.get(fieldName)));
                        break;
                    case GT:
                        builder.and(dynamicQuery.gtIntegerParam(
                                (NumberPath) path, (Integer) mapperMap.get(fieldName)));
                        break;
                }
            }
        }
    }

}