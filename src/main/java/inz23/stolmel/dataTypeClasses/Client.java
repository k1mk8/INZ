package com.example.application;

public class Client {

	private int id;
	private String name;
	private String surname;
	private String number;
	private String email;
	private String hash;

	public Client() {
		this.id = -1;
		this.name = "";
		this.surname = "";
		this.number = "";
		this.email = "";
		this.hash = "";
	}

	public Client(int id, String name, String surname, String number, String email, String hash) {
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

	private void setId(int id) {
		this.id = id;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setSurname(String surname) {
		this.surname = surname;
	}

	private void setNumber(String number) {
		this.number = number;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	private void setHash(String hash) {
		this.hash = hash;
	}

	@Override
    public String toString() {
        return String.format("""
		{"id":"%d", "name":"%s", "surname":"%s", "number":"%s", "email":"%s", "hash":"%s"}""", 
		this.id, this.name, this.surname, this.number, this.email, this.hash);
    }
}