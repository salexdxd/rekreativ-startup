package com.example.RekreativStartup.model;

import javax.persistence.*;

@Entity
public class Matches {

    @Id
    @GeneratedValue
    private Long id;
    
//    @Column(name = "team_a")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teamA")
    private Team teamA;

//    @Column(name = "team_b")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teamB")
    private Team teamB;

    @Column(name = "team_a_score")
    private Integer teamAScore;

    @Column(name = "team_b_score")
    private Integer teamBScore;

    @Column(name = "winner")
    private String winner;

    public Matches(){
        super();
    }

    public Matches(Long id, Team teamA, Team teamB) {
        this.id = id;
        this.teamA = teamA;
        this.teamB = teamB;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public Integer getTeamAScore() {
        return teamAScore;
    }

    public void setTeamAScore(Integer teamAScore) {
        this.teamAScore = teamAScore;
    }

    public Integer getTeamBScore() {
        return teamBScore;
    }

    public void setTeamBScore(Integer teamBScore) {
        this.teamBScore = teamBScore;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
