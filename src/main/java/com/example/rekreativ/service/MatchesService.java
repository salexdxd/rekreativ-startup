package com.example.rekreativ.service;

import com.example.rekreativ.dto.MatchesRequestDTO;
import com.example.rekreativ.model.Matches;
import com.example.rekreativ.model.Team;

public interface MatchesService {

    public void matchOutcome(Matches newMatch, Team existingTeamA, Team existingTeamB);

    public Matches createMatchup(String teamOne, String teamTwo, Integer teamOneScore, Integer teamTwoScore);

    public Matches save(MatchesRequestDTO matches);

    public Iterable<Matches> findAll();

    public Matches findMatchById(Long id);

    public void delete(Long id);
}
