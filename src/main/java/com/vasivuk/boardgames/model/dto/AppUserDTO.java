package com.vasivuk.boardgames.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
