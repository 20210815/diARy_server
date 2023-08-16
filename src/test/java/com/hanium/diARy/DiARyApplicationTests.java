package com.hanium.diARy;

import com.hanium.diARy.auth.repository.AuthRepository;
import com.hanium.diARy.user.entity.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class DiARyApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private AuthRepository authRepository;

	private MockMvc mockMvc;

	@Before("")
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testLogin() throws Exception {
		String username = "testUser";
		String password = "testPassword";
		String email = "email11";

		String encodedPassword = new BCryptPasswordEncoder().encode(password);

		// 여기서 사용자를 등록하고 인증 요청을 보낸다고 가정합니다.
		User user = new User();
		user.setUsername(username);
		user.setPassword(encodedPassword);
		user.setEmail(email);
		authRepository.save(user);

		mockMvc.perform(MockMvcRequestBuilders.post("/login")
						.param("email", email)
						.param("password", password))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// 이제 로그인된 상태로 특정 작업을 수행할 수 있습니다.
	}

}
