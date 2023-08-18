package com.example.restservicecors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
public class UserController {

	private final User JanKowalski = new User(0, "Jan", "Kowalski", "881056092", "JanKowalski@gmail.com", "#");

	@CrossOrigin(origins = "http://localhost:8082")

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

	@GetMapping("/getUser")
	public User user() {
		System.out.println("==== get user ====");
		return JanKowalski;
	}

	@PostMapping("/sendParam")
	@ResponseBody
	public String sendParam(@RequestParam String string) {
		System.out.println("==== receive parameter ====");
		System.out.println(string);
		return string;
	}

	@PostMapping("/sendString")
	@ResponseBody
	public String sendString(@RequestBody String string) {
		System.out.println("==== receive string ====");
		System.out.println(string);
		return string;
	}
}