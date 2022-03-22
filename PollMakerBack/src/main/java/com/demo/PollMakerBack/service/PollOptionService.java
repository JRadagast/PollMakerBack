package com.demo.PollMakerBack.service;

import com.demo.PollMakerBack.bean.Poll;
import com.demo.PollMakerBack.bean.PollOption;
import com.demo.PollMakerBack.repository.PollOptionRepository;
import com.demo.PollMakerBack.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PollOptionService {

    @Autowired
    private PollOptionRepository repository;

    public ResponseEntity<List<PollOption>>  getAllFromPoll(Long idPoll){
        try {
            List<PollOption> ls = repository.getAllFromPoll(idPoll);

            ResponseEntity<List<PollOption>> response = new ResponseEntity<List<PollOption>>(ls, HttpStatus.OK);

            return response;
        } catch (SQLException ex){

            ResponseEntity<List<PollOption>> response = new ResponseEntity<List<PollOption>>(HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<PollOption> save(PollOption pollOption){
        try {
            PollOption p = repository.getById(pollOption.getPollOptionId());
            if (p != null){
                p = repository.update(pollOption);
            } else {
                p = repository.save(pollOption);
            }

            ResponseEntity<PollOption> response = new ResponseEntity<PollOption>(p, HttpStatus.OK);

            return response;
        } catch (SQLException ex){

            ResponseEntity<PollOption> response = new ResponseEntity<PollOption>(HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

}
