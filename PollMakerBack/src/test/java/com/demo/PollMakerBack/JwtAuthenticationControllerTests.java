package com.demo.PollMakerBack;

import com.demo.PollMakerBack.bean.Poll;
import com.demo.PollMakerBack.bean.Usuario;
import com.demo.PollMakerBack.controller.JwtAuthenticationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtAuthenticationControllerTests {

    @Autowired
    private JwtAuthenticationController controller;

    @Test
    public void contextLoads(){
        assertThat(controller).isNotNull();
    }

    @Test
    public void getById() throws Exception{
        ResponseEntity<Usuario> response = controller.getUserById(3L);
        assertThat( response.getBody() ).isOfAnyClassIn( Usuario.class );
        assertThat( response.getBody().getIdusuario() ).isEqualTo( 3L );
    }

    @Test
    public void getAllWithIds() throws Exception {
        Usuario user = new Usuario();
        user.setUsername("Teste");
        user.setPassword("teste");

        ResponseEntity<Usuario> response = controller.createAuthenticationToken( user );
        assertThat(response.getBody()).isOfAnyClassIn( Usuario.class );
        assertThat(response.getBody().getJwttoken()).isInstanceOf(String.class);
    }

}
