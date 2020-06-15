package dev.jian.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// This class is used for sending out the authentication to the client
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {

	private String authenticationToken;
	private String username;
	// For token expiration
	private Instant expiresAt;
	private String refreshToken;
}
