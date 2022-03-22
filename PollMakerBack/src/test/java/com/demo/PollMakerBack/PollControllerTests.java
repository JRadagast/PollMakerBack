package com.demo.PollMakerBack;

import static org.assertj.core.api.Assertions.assertThat;

import com.demo.PollMakerBack.bean.Poll;
import com.demo.PollMakerBack.controller.PollController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PollControllerTests {

	@Autowired
	private PollController controller;

	@Test
	public void contextLoads(){
		assertThat(controller).isNotNull();
	}

	@Test
	public void getList() {
		ResponseEntity<List<Poll>> response = controller.getAllByUserId(1L);
		assertThat(response.getBody().size()).isGreaterThanOrEqualTo(0);
	}

	@Test
	public void getById() {
		ResponseEntity<Poll> response = controller.getById(1L);
		assertThat( response.getBody() ).isOfAnyClassIn( Poll.class );
		assertThat( response.getBody().getPollId() ).isEqualTo( 1L );
	}

	@Test
	public void getAllWithIds() {
		List<Long> ids = new ArrayList<>();
		ids.add(15L);
		ResponseEntity<List<Poll>> response = controller.getAllWithIds( ids );
		assertThat(response.getBody().size()).isGreaterThanOrEqualTo(0);
	}
}
