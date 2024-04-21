package com.machine.coding.trello;

import com.machine.coding.trello.service.InputHandlerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@SpringBootApplication
public class TrelloApplication implements CommandLineRunner {

	InputHandlerService inputHandlerService;

	public TrelloApplication(InputHandlerService inputHandlerService) {
		this.inputHandlerService = inputHandlerService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TrelloApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		File file = ResourceUtils.getFile("classpath:input-file.txt");
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			while(true) {
				String line = bufferedReader.readLine();
				if (line == null) {
					break;
				}
				inputHandlerService.handleInput(line);
			}
		}
	}
}
