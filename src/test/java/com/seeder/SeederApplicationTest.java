package com.seeder;

import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.seeder.model.User;

@SpringBootTest
@AutoConfigureMockMvc
class SeederApplicationTest {

	@Test
	void main() {
		User user = new User();
		Assert.notNull(user);
	}
}
