package com.demo.PollMakerBack.bean;

public class PollOption {

    private Long pollId;
    private Long pollOptionId;
    private String option;

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

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "PollOption{" +
                "pollId=" + pollId +
                ", pollOptionId=" + pollOptionId +
                ", option='" + option + '\'' +
                '}';
    }
}
