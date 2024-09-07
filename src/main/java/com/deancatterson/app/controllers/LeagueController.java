package com.deancatterson.app.controllers;

import com.deancatterson.app.Team;
import com.deancatterson.app.exception.NoTeamFoundException;
import com.deancatterson.app.exception.NullLeagueIdException;
import com.deancatterson.app.exception.NullTeamIdException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// This Controller receives requests from the FPL-Web-App UI
@RestController
public class LeagueController {
    // TODO: view mini league info feature

    @GetMapping("/league/{leagueId}")
    public static List<Team> getListOfAllTeamsInLeague(String leagueId) throws NullLeagueIdException {
        if (leagueId == null) {
            throw new NullLeagueIdException("League ID is null");
        }

        List<Team> teams = new ArrayList<>();

        return teams;
    }

}
