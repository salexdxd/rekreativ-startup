package com.example.RekreativStartup.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String teamName;


//    @ManyToMany(mappedBy="team")
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user; //= new ArrayList<>();

//    @ManyToOne(optional = true, fetch = FetchType.LAZY)
//    @JoinTable(name = "team_team",
//            joinColumns = { @JoinColumn(name = "parent_team_id", referencedColumnName = "id", insertable = false, updatable = false) },
//            inverseJoinColumns = { @JoinColumn(name = "child_team_id", referencedColumnName = "id", insertable = false, updatable = false) } )
//    private Team parentTeam;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "team_team",
//            joinColumns = {
//                    @JoinColumn(name = "parent_team_id",
//                            referencedColumnName = "id",
//                            insertable = false,
//                            updatable = false)
//            },
//            inverseJoinColumns = {
//                    @JoinColumn(name = "child_team_id",
//                            referencedColumnName = "id",
//                            insertable = false,
//                            updatable = false) } )
//    private List<Team> childTeam;


//    @ManyToMany
//    @JoinTable(
//            name = "team_enemy",
//            joinColumns = @JoinColumn(name = "team_id"),
//            inverseJoinColumns = @JoinColumn(name = "opponent_id"))
//    private Collection<Matchup> matchups = new ArrayList<>();

    @JsonIgnoreProperties(value = {"team"})
//    @OneToMany(mappedBy = "team", targetEntity = Teammate.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "team_teammates",
        joinColumns = { @JoinColumn(name = "team_id")},
        inverseJoinColumns = { @JoinColumn (name = "teammate_id")})
    private Collection<Teammate> teammates = new ArrayList<>();

    private String city;

    private Integer score;

    public Team(){
        super();
    }

    public Team(Long id, String teamName, String city, Integer score) {
        super();
        this.id = id;
        this.teamName = teamName;
        this.city = city;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Collection<Teammate> getTeammates() {
        return teammates;
    }

    public void setTeammates(Collection<Teammate> teammates) {
        this.teammates = teammates;
    }

    //    public void setMatchups(Collection<Matchup> matchups) {
//        this.matchups = matchups;
//    }
//
//    public Collection<Matchup> getMatchups() {
//        return matchups;
//    }


}


