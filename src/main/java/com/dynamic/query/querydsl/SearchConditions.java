package com.dynamic.query.querydsl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SearchConditions {

    private List<EntityParam> eqConditions;

}
