package com.example.befruit.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User extends Base<String> implements Serializable {
	//    @JsonIgnore

	private String email;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	@Column(name = "verification_code", length = 64)
	private String verificationCode;
	private Boolean enabled;
	private String token;
	@Column(name = "expiry_date")
	private Instant expiryDate;
	@Column(columnDefinition = "integer default 1")
	private Integer status;
	@ManyToMany
			(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	List<Role> roles = new ArrayList<>();

	//    @OneToMany(cascade = CascadeType.ALL)
//    private List<CartItem> cartItems;
	public void addRole(Role role) {
		System.out.println("Progress add role.");
		this.roles.add(role);
		System.out.println("Added role.");
	}
}
