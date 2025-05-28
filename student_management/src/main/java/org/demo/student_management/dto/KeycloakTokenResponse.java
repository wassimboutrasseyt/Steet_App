package org.demo.student_management.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakTokenResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;
    private int refresh_expires_in;
}
