package com.example.befruit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Integer status;
}
