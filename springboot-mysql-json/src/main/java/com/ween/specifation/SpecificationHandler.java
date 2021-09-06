package com.ween.specifation;

import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

/**
 * @author wuwenhao
 */
public class SpecificationHandler {

    public Specification<T> analysisParam(T t){
        return (root,query,cb)->{
            return query.getRestriction();
        };
    }
}
