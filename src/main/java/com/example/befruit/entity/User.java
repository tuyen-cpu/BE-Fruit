package com.example.befruit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;


import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> roles = new ArrayList<>();
    public void addRole(Role role) {
        System.out.println("vào add role");
        this.roles.add(role);
        System.out.println("vào add role thnafh công");
    }
}
