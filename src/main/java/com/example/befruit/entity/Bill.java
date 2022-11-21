package com.example.befruit.entity;

import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bill")
public class Bill extends Base<String> implements Serializable {

	private Long shippingCost;
	private Long total;
	private String description;
	private String address;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdDate;
	@Column(name = "status")
	private Integer status;
	@ManyToOne(targetEntity = ShippingStatus.class)
	@JoinColumn(name = "shipping_status_id", nullable = false)
	private ShippingStatus shippingStatus;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;


	@OneToMany(mappedBy = "bill",
			cascade = CascadeType.ALL,
			targetEntity = OrderDetail.class
	)

	private List<OrderDetail> orderDetails = new ArrayList<>();

	@ManyToOne(targetEntity = Voucher.class)
	@JoinColumn(name = "voucher_id")
	private Voucher voucher;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_id", referencedColumnName = "id")
	private Payment payment;

	@PrePersist
	private void onCreate() {
		createdDate = new Date();
	}
//
//    @ManyToOne(targetEntity = ShippingStatus.class)
//    @JoinColumn(name = "shipping_status_id", nullable = false)
//    private ShippingStatus shippingStatus;


}
