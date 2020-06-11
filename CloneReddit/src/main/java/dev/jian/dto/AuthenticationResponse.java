package dev.jian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// This class is used for sending out the authentication to the client
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

	private String authenticationToken;
	private String username;
	
}
