package com.ween.specifation;

import com.ween.entity.Event;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CustomSpecification {

    public static Specification<Event> multiParamQuery(String id,String city){
        return (root,query,cb)->{
            List<Predicate> querys=new ArrayList<>();
            if(!StringUtils.isEmpty(id)){
                querys.add(cb.equal(root.get("id"),id));
            }
            if(!StringUtils.isEmpty(city)){
                querys.add(  cb.equal(cb.function(
                        "JSON_EXTRACT",
                        String.class,
                        root.get("location"),
                        cb.literal("$.city")),city));
            }
            return  query.where(querys.toArray(new Predicate[querys.size()])).getRestriction();
        };
    }
}
