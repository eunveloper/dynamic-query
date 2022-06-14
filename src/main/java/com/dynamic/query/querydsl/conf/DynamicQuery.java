package com.dynamic.query.querydsl.conf;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class DynamicQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    public BooleanExpression eqStringParam(StringPath stringPath, String parameter) {
        return parameter == null ? null : stringPath.eq(parameter);
    }

    public BooleanExpression eqBooleanParam(BooleanPath stringPath, Boolean parameter) {
        return parameter == null ? null : stringPath.eq(parameter);
    }

    public BooleanExpression eqIntegerParam(NumberPath stringPath, Integer parameter) {
        return parameter == null ? null : stringPath.eq(parameter);
    }

    public BooleanExpression eqLongParam(NumberPath<Long> stringPath, Long parameter) {
        return parameter == null ? null : stringPath.eq(parameter);
    }

}
