package com.demo.PollMakerBack;

import com.demo.PollMakerBack.bean.BarChart;
import com.demo.PollMakerBack.bean.Poll;
import com.demo.PollMakerBack.bean.PollAnswer;
import com.demo.PollMakerBack.bean.PollOption;
import com.demo.PollMakerBack.controller.PollAnswerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PollOptionControllerTests {

	@Autowired
	private PollAnswerController controller;

	@Test
	public void contextLoads(){
		assertThat(controller).isNotNull();
	}

	@Test
	public void getChartData() {
		ResponseEntity<List<BarChart>> response = controller.getChartData(1L);
		assertThat(response.getBody().size()).isGreaterThanOrEqualTo(0);
	}

	@Test
	public void getById() {
		ResponseEntity<PollAnswer> response = controller.getById(3L);
		assertThat( response.getBody() ).isOfAnyClassIn( PollAnswer.class );
		assertThat( response.getBody().getPollAnswerId() ).isEqualTo( 3L );
	}

	@Test
	public void getAllByPollIdAndUserId() {
		ResponseEntity<PollAnswer> response = controller.getAllByPollIdAndUserId( 11L, 4L );
		assertThat( response.getBody() ).isOfAnyClassIn(PollAnswer.class);
		assertThat( response.getBody().getPollAnswerId() ).isEqualTo( 3L );
	}
}
