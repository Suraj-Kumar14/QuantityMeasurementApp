package com.app.quantitymeasurement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(unique = true)
	private String username;
	
	@NotBlank
	@Email
	@Column(unique = true)
	private String email;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AuthProvider provider;
	
	private String providerId;
	
	@Column(nullable = false)
	private boolean enabled = true;
}
