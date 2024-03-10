package com.teb.teborchestrator.model.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @Email
    @NotBlank
    @Size(min = 5, max = 255)
    private String email;

    @Size(min = 3, max = 255)
    private String password;
}
