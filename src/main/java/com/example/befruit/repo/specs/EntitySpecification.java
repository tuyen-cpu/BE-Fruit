package com.example.befruit.repo.specs;

import com.example.befruit.entity.Filter;
import com.example.befruit.entity.Role;
import com.example.befruit.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class EntitySpecification<T> implements Specification<T> {
	private final List<Filter> list;

	public EntitySpecification() {
		this.list = new ArrayList<>();
	}

	public void add(Filter criteria) {
		list.add(criteria);
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		List<Predicate> predicates = new ArrayList<>();


		for (Filter criteria : list) {

			switch (criteria.getOperator()){
				case IN:
					if(criteria.getKey().startsWith("role")){
						Join<User, Role> join = root.join("roles");
						predicates.add(builder.like(
								join.get(splitString(criteria.getKey())), "%"+ criteria.getValue()+"%"));
					}else{
						predicates.add(builder.like(
								root.get(criteria.getKey()), "%"+ criteria.getValue()+"%"));
					}

					break;
				case GREATER_THAN:
					predicates.add(builder.greaterThan(
							root.get(criteria.getKey()), criteria.getValue().toString()));
					break;
				case LESS_THAN:
					predicates.add(builder.lessThan(
							root.get(criteria.getKey()), criteria.getValue().toString()));
					break;
				case GREATER_THAN_EQUAL:
					predicates.add(builder.greaterThanOrEqualTo(
							root.get(criteria.getKey()), criteria.getValue().toString()));
					break;
				case LESS_THAN_EQUAL:
					predicates.add(builder.lessThanOrEqualTo(
							root.get(criteria.getKey()), criteria.getValue().toString()));
					break;
				case NOT_EQUAL:
					predicates.add(builder.notEqual(
							root.get(criteria.getKey()), criteria.getValue()));
					break;
				case EQUAL:
					predicates.add(builder.equal(
							root.get(criteria.getKey()), criteria.getValue()));
					break;

//                case MATCH:
//                    predicates.add(builder.like(
//                            builder.lower(root.get(criteria.getKey())),
//                            "%" + criteria.getValue().toString().toLowerCase() + "%"));
//                    break;
//                case MATCH_END:
//                    predicates.add(builder.like(
//                            builder.lower(root.get(criteria.getKey())),
//                            criteria.getValue().toString().toLowerCase() + "%"));
//                    break;
//                case MATCH_START:
//                    predicates.add(builder.like(
//                            builder.lower(root.get(criteria.getKey())),
//                            "%" + criteria.getValue().toString().toLowerCase()));
//                    break;
//                case IN:
//                    predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
//                    break;
//                case NOT_IN:
//                    predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
//                    break;
				default:
					throw new RuntimeException("Operation not supported yet");

			}}
		query.distinct(true);
		return builder.and(predicates.toArray(new Predicate[0]));
	}
	private String splitString(String str){
		return str.split("_")[1];
	}
}