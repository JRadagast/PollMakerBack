package com.demo.PollMakerBack.controller;

import com.demo.PollMakerBack.bean.BarChart;
import com.demo.PollMakerBack.bean.Poll;
import com.demo.PollMakerBack.bean.PollAnswer;
import com.demo.PollMakerBack.service.PollAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PollAnswerController {

    @Autowired
    private PollAnswerService service;

    @CrossOrigin
    @GetMapping("/pollmaker/answers/getanswerbypollanduser")
    public ResponseEntity<PollAnswer> getAllByPollIdAndUserId(@RequestParam(name="idpoll") Long idpoll, @RequestParam(name="iduser") Long iduser  ) {

        ResponseEntity<PollAnswer> r = service.getByPollIdAndUserId(idpoll, iduser);

        return r;
    }

    /*
    @CrossOrigin
    @GetMapping("/pollmaker/answers/getallbyuser")
    public ResponseEntity<List<PollAnswer>> getAllByUserId(@RequestParam(name="iduser") Long iduser ) {

        ResponseEntity<List<PollAnswer>> r = service.getAllByUserId(iduser);

        return r;
    }
    */

    @CrossOrigin
    @GetMapping("/pollmaker/answers/getbyid")
    public ResponseEntity<PollAnswer> getById( @RequestParam(name="idpollanswer") Long idpollanswer ) {

        ResponseEntity<PollAnswer> r = service.getById(idpollanswer);

        return r;
    }

    @CrossOrigin
    @PostMapping("/pollmaker/answers/save-answer")
    public ResponseEntity<PollAnswer> save( @RequestBody PollAnswer pollAnswer ) {

        ResponseEntity<PollAnswer> r = service.save(pollAnswer);

        return r;
    }

    @CrossOrigin
    @GetMapping("/pollmaker/answers/answersfrompoll")
    public ResponseEntity<List<BarChart>> getChartData(@RequestParam(name="idpoll") Long idpoll ) {

        ResponseEntity<List<BarChart>> r = service.getChartData(idpoll);

        return r;
    }

}
