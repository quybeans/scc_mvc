package com.scc.ticketmanagement.utilities;

import com.scc.ticketmanagement.Entities.TicketEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by user on 11/17/2016.
 */
public class TicketSpecification implements Specification<TicketEntity> {

    private FilterCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<TicketEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (getCriteria().getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.<String> get(getCriteria().getKey()), getCriteria().getValue().toString());
        }
        else if (getCriteria().getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.<String> get(getCriteria().getKey()), getCriteria().getValue().toString());
        }
        else if (getCriteria().getOperation().equalsIgnoreCase(":")) {
            if (root.get(getCriteria().getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(getCriteria().getKey()), "%" + getCriteria().getValue() + "%");
            } else {
                return builder.equal(root.get(getCriteria().getKey()), getCriteria().getValue());
            }
        }
        return null;
    }

    public FilterCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(FilterCriteria criteria) {
        this.criteria = criteria;
    }

    public TicketSpecification(FilterCriteria criteria) {
        this.criteria = criteria;
    }
}
