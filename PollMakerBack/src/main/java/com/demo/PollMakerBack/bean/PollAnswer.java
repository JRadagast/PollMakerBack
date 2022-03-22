package com.demo.PollMakerBack.bean;

public class PollAnswer {

    private Long pollAnswerId;
    private Long pollId;
    private Long pollOptionId;
    private Long iduser;
    private String option;

    public Long getPollAnswerId() {
        return pollAnswerId;
    }

    public void setPollAnswerId(Long pollAnswerId) {
        this.pollAnswerId = pollAnswerId;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public Long getPollOptionId() {
        return pollOptionId;
    }

    public void setPollOptionId(Long pollOptionId) {
        this.pollOptionId = pollOptionId;
    }

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
