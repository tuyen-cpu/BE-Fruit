package com.example.befruit.dto;

import com.example.befruit.entity.Product;
import com.example.befruit.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO implements Serializable {
    private Long id;
    private String city;
    private String district;
    private String ward;
    private String street;
    private String description;
    private Integer status;
    private Long userId;

}
