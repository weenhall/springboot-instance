package com.ween.specifation;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wuwenhao
 */
public class SpecificationHandler {

    public static <T> Specification<T>  analysisParam(T t, Map<String,Object> param){
       return (root,query,cb)->{
           List<Predicate> querys=new ArrayList<>();
           param.forEach((k,v)->{
               if(!ObjectUtils.isEmpty(v)){
                   querys.add(cb.equal(root.get(k),v));
               }
           });
           return query.where(querys.toArray(new Predicate[querys.size()])).getRestriction();
       };
    }
}
