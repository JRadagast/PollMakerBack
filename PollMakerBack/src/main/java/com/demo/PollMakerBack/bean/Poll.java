package com.demo.PollMakerBack.bean;

import java.util.List;


public class Poll {

    private Long pollId;
    private String title;
    private String description;
    private List<PollOption> pollOptions;
    private PollAnswer pollAnswer;
    private Boolean requiresAuthentication;
    private Usuario user;

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PollOption> getPollOptions() {
        return pollOptions;
    }

    public void setPollOptions(List<PollOption> pollOptions) {
        this.pollOptions = pollOptions;
    }

    public PollAnswer getPollAnswer() {
        return pollAnswer;
    }

    public void setPollAnswer(PollAnswer pollAnswer) {
        this.pollAnswer = pollAnswer;
    }

    public Boolean getRequiresAuthentication() {
        return requiresAuthentication;
    }

    public void setRequiresAuthentication(Boolean requiresAuthentication) {
        this.requiresAuthentication = requiresAuthentication;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "pollId=" + pollId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pollOptions=" + pollOptions +
                ", pollAnswer=" + pollAnswer +
                ", requiresAuthentication=" + requiresAuthentication +
                '}';
    }
}
