package com.demo.PollMakerBack.controller;

import com.demo.PollMakerBack.bean.Usuario;
import com.demo.PollMakerBack.service.JwtUserDetailsService;
import com.demo.PollMakerBack.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @CrossOrigin
    @RequestMapping(value = "/pollmaker/authenticate", method = RequestMethod.POST)
    public ResponseEntity<Usuario> createAuthenticationToken(@RequestBody Usuario authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        Usuario user = userDetailsService.getByUsername(userDetails.getUsername());
        user.setJwttoken(token);

        return ResponseEntity.ok( user );
    }

    @CrossOrigin
    @RequestMapping(value = "/pollmaker/cadastrar", method = RequestMethod.POST)
    public ResponseEntity<Usuario> saveUser(@RequestBody Usuario user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    @CrossOrigin
    @RequestMapping(value = "/pollmaker/getuserbyid", method = RequestMethod.GET)
    public ResponseEntity<Usuario> getUserById(@RequestParam(name="iduser") Long iduser) throws Exception {
        return ResponseEntity.ok(userDetailsService.getUserById(iduser));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
