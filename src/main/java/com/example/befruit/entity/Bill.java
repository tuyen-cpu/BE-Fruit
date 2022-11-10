package com.example.befruit.entity;
import java.util.ArrayList;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bill")
public class Bill implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long shippingCost;
    private Long total;
    private String description;
    private Date createdDate;
    @Column(name="status")
    private Integer status;
    @ManyToOne(targetEntity = ShippingStatus.class)
    @JoinColumn(name = "shipping_status_id", nullable = false)
    private ShippingStatus shippingStatus;

    @ManyToOne(targetEntity = Address.class)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;



    @OneToMany(mappedBy = "bill",
            cascade = CascadeType.ALL,
            targetEntity = OrderDetail.class
    )

    private List<OrderDetail> orderDetails = new ArrayList<>();

    @ManyToOne(targetEntity = Voucher.class)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

//
//    @ManyToOne(targetEntity = ShippingStatus.class)
//    @JoinColumn(name = "shipping_status_id", nullable = false)
//    private ShippingStatus shippingStatus;


}
