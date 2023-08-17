package com.example.restservicecors;

public class User {

	private final int id;
	private final String name;
	private final String surname;
	private final String number;
	private final String email;
	private final String hash;

	public User() {
		this.id = -1;
		this.name = "";
		this.surname = "";
		this.number = "";
		this.email = "";
		this.hash = "";
	}

	public User(int id, String name, String surname, String number, String email, String hash) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.number = number;
		this.email = email;
		this.hash = hash;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getNumber() {
		return number;
	}

	public String getEmail() {
		return email;
	}

	public String getHash() {
		return hash;
	}
}