package com.demo.PollMakerBack.service;

import com.demo.PollMakerBack.bean.Poll;
import com.demo.PollMakerBack.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PollService {

    @Autowired
    private PollRepository repository;

    public ResponseEntity<List<Poll>>  getAllByUserId( Long iduser ){
        try {
            List<Poll> ls = repository.getAllByUserId( iduser );

            ResponseEntity<List<Poll>> response = new ResponseEntity<List<Poll>>(ls, HttpStatus.OK);

            return response;
        } catch (SQLException ex){

            ResponseEntity<List<Poll>> response = new ResponseEntity<List<Poll>>(HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }
    public ResponseEntity<List<Poll>>  getAllWithIds( List<Long> idpolls ){
        try {
            List<Poll> ls = repository.getAllWithIds( idpolls );

            ResponseEntity<List<Poll>> response = new ResponseEntity<List<Poll>>(ls, HttpStatus.OK);

            return response;
        } catch (SQLException ex){

            ResponseEntity<List<Poll>> response = new ResponseEntity<List<Poll>>(HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<Poll>  getById(Long idPoll){
        try {
            Poll p = repository.getById(idPoll);

            ResponseEntity<Poll> response = new ResponseEntity<Poll>(p, HttpStatus.OK);

            return response;
        } catch (SQLException ex){

            ResponseEntity<Poll> response = new ResponseEntity<Poll>(HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<Poll> save(Poll poll){
        try {
            Poll p = repository.getById(poll.getPollId());
            if (p != null){
                p = repository.update(poll);
            } else {
                p = repository.save(poll);
            }

            ResponseEntity<Poll> response = new ResponseEntity<Poll>(p, HttpStatus.OK);

            return response;
        } catch (SQLException ex){

            ResponseEntity<Poll> response = new ResponseEntity<Poll>(HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

}
