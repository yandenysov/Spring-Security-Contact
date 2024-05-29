package com.example.App.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Клас DTO (Data Transfer Object)
// для передачі даних між клієнтом і сервером.
// Клас ContactDto для передачі даних між
// рівнем контролера та рівнем презентації.
// Клас використовується для зв'язування даних
// з формою у презентації.
public class ContactDto {

    private Long id;

    @NotEmpty(message = "First name should not be empty")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    private String lastName;

    @NotEmpty(message = "Phone number should not be empty")
    private String phone;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
