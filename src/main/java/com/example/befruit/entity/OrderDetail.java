package com.example.befruit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long price;
	private Integer quantity;
	private Integer discount;

	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "product_id", nullable = false)
	@JsonIgnore
	private Product product;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(targetEntity = Bill.class)
	@JoinColumn(name = "bill_id", nullable = false)
	private Bill bill;
}
