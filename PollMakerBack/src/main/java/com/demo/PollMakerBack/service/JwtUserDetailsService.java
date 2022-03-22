package com.demo.PollMakerBack.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.demo.PollMakerBack.bean.Usuario;
import com.demo.PollMakerBack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = null;
        try{
            user = userRepository.getByUsername(username);
        } catch (SQLException exception ){
            exception.printStackTrace();
        }

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Usuario não encontrado com o login: " + username);
        }
    }

    public Usuario getUserById( Long iduser ) {
        Usuario user = null;
        try{
            user = userRepository.getById(iduser);
        } catch (SQLException exception ){
            exception.printStackTrace();
        }

        return user;
    }

    public Usuario getByUsername( String username ) {
        Usuario user = null;
        try{
            user = userRepository.getByUsername(username);
        } catch (SQLException exception ){
            exception.printStackTrace();
        }

        return user;
    }

    public Usuario save(Usuario user) throws Exception {
        Usuario us = null;
        try {
            us = userRepository.getByUsername(user.getUsername());
        } catch ( SQLException ex ){
            ex.printStackTrace();
        }

        if (us != null){
            throw new Exception("Usuario já existe!");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setUsername(user.getUsername());
        newUsuario.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUsuario);
    }
}