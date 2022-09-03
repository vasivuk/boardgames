package com.vasivuk.boardgames.model.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AppUserDTO {

    @NotBlank(message = "First name is mandatory")
    @Size(max = 30)
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    @Size(max = 30)
    private String lastName;
    @Email(message = "Email has to be of valid format")
    @NotBlank(message = "Email is mandatory")
    @EqualsAndHashCode.Include
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 5)
    private String password;

    private String country;
    private String city;
    private String address;
}
