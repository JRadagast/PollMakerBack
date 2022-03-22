package com.demo.PollMakerBack.controller;

import com.demo.PollMakerBack.bean.Poll;
import com.demo.PollMakerBack.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class PollController {

    @Autowired
    private PollService service;

    @CrossOrigin
    @PostMapping("/pollmaker/getallwithids")
    public ResponseEntity<List<Poll>> getAllWithIds( @RequestBody List<Long> pollIds ) {

        ResponseEntity<List<Poll>> r = service.getAllWithIds(pollIds);

        return r;
    }

    @CrossOrigin
    @GetMapping("/pollmaker/getallbyuser")
    public ResponseEntity<List<Poll>> getAllByUserId( @RequestParam(name="iduser") Long iduser ) {

        ResponseEntity<List<Poll>> r = service.getAllByUserId(iduser);

        return r;
    }

    @CrossOrigin
    @GetMapping("/pollmaker/getbyid")
    public ResponseEntity<Poll> getById( @RequestParam(name="idpoll") Long idpoll ) {

        ResponseEntity<Poll> r = service.getById(idpoll);

        return r;
    }

    @CrossOrigin
    @PostMapping("/pollmaker/save-poll")
    public ResponseEntity<Poll> save( @RequestBody Poll poll ) {

        ResponseEntity<Poll> r = service.save(poll);

        return r;
    }

}
