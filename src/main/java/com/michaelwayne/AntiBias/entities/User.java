package com.michaelwayne.AntiBias.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int user_id;

	@Getter
	@Nonnull
	@Column(name = "email")
	private String email;
	@Nonnull
	@Column(name = "pass_hash")
	private String pass_hash;
	@Nonnull
	@Column(name = "salt")
	private String salt;

	public User(@NonNull String email,
				@NonNull String pass_hash) {

		this.email = email;
		this.pass_hash = pass_hash;

		this.salt = this.generateSalt();
	}

	@JsonCreator
	public User(int user_id,
				@NonNull String email) {
		this.user_id = user_id;
		this.email = email;
	}

	@Override
	public String toString() {
		return "User{" +
				"user_id=" + user_id +
				", email='" + email + '\'' +
				'}';
	}

	/**
	 * Generate a random salt for the user.
	 *
	 * @return The generated salt.
	 */
	private String generateSalt() {
		return "";
	}
}
