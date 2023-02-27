package com.likelion.Demojwt;

import com.likelion.Demojwt.Entity.User;
import com.likelion.Demojwt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemojwtApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemojwtApplication.class, args);
	}
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		//khi chuong trinh chay
		//insert vao csdl mot user
		User user = new User();
		user.setUsername("username");
		user.setPassword(passwordEncoder.encode("123"));
		userRepository.save(user);
		System.out.println(user);
	}
}
