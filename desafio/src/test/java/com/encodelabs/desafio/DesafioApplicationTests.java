package com.encodelabs.desafio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class DesafioApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> DesafioApplication.main(new String[] {}));
	}

}
