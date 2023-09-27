package com.example.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
public class ClientController {

	private final Client JanKowalski = new Client(0, "Jan", "Kowalski", "881056092", "JanKowalski@gmail.com", "#");

	@GetMapping("/getId")
	public int id() {
		System.out.println("==== get id ====");
		return JanKowalski.getId();
	}

	@GetMapping("/getName")
	public String name() {
		System.out.println("==== get name ====");
		return JanKowalski.getName();
	}

	@GetMapping("/getSurname")
	public String surname() {
		System.out.println("==== get surname ====");
		return JanKowalski.getSurname();
	}

	@GetMapping("/getNumber")
	public String number() {
		System.out.println("==== get number ====");
		return JanKowalski.getNumber();
	}

	@GetMapping("/getEmail")
	public String email() {
		System.out.println("==== get email ====");
		return JanKowalski.getEmail();
	}

	@GetMapping("/getHash")
	public String hash() {
		System.out.println("==== get hash ====");
		return JanKowalski.getHash();
	}

	@GetMapping("/getClient")
	public Client client() {
		System.out.println("==== get user ====");
		return JanKowalski;
	}
}