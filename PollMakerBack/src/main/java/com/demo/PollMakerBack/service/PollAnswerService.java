package com.demo.PollMakerBack.service;

import com.demo.PollMakerBack.bean.BarChart;
import com.demo.PollMakerBack.bean.Poll;
import com.demo.PollMakerBack.bean.PollAnswer;
import com.demo.PollMakerBack.repository.PollAnswerRepository;
import com.demo.PollMakerBack.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PollAnswerService {

    @Autowired
    private PollAnswerRepository repository;
    /*
    public ResponseEntity<List<PollAnswer>>  getAllByUserId(Long iduser ){
        try {
            List<PollAnswer> ls = repository.getAllByUserId( iduser );

            ResponseEntity<List<PollAnswer>> response = new ResponseEntity<List<PollAnswer>>(ls, HttpStatus.OK);

            return response;
        } catch (SQLException ex){

            ResponseEntity<List<PollAnswer>> response = new ResponseEntity<List<PollAnswer>>(HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }
    */

    public ResponseEntity<PollAnswer> getByPollIdAndUserId( Long idpoll, Long iduser ){
        try {
            PollAnswer ls = repository.getByPollIdAndUserId( idpoll, iduser );

            ResponseEntity<PollAnswer> response = new ResponseEntity<PollAnswer>(ls, HttpStatus.OK);

            return response;
        } catch (SQLException ex){

            ResponseEntity<PollAnswer> response = new ResponseEntity<PollAnswer>(HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<PollAnswer> getById(Long idPoll){
        try {
            PollAnswer p = repository.getById(idPoll);

            ResponseEntity<PollAnswer> response = new ResponseEntity<PollAnswer>(p, HttpStatus.OK);

            return response;
        } catch (SQLException ex){

            ResponseEntity<PollAnswer> response = new ResponseEntity<PollAnswer>(HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<PollAnswer> save(PollAnswer pollAnswer){
        try {
            PollAnswer p = repository.getByPollIdAndUserId(pollAnswer.getPollId(), pollAnswer.getIduser());
            if (p != null){
                p = repository.update(pollAnswer);
            } else {
                p = repository.save(pollAnswer);
            }

            ResponseEntity<PollAnswer> response = new ResponseEntity<PollAnswer>(p, HttpStatus.OK);

            return response;
        } catch (SQLException ex){

            ResponseEntity<PollAnswer> response = new ResponseEntity<PollAnswer>(HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<List<BarChart>> getChartData(Long idPoll){
        try {
            List<BarChart> p = repository.getChartData(idPoll);

            ResponseEntity<List<BarChart>> response = new ResponseEntity<List<BarChart>>(p, HttpStatus.OK);

            return response;
        } catch (SQLException ex){

            ResponseEntity<List<BarChart>> response = new ResponseEntity<List<BarChart>>(HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

}
