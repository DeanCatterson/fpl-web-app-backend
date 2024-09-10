package com.deancatterson.app.controllers;

import com.deancatterson.app.entity.Team;
import com.deancatterson.app.exception.NullLeagueIdException;
import com.deancatterson.app.services.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// This Controller receives requests from the FPL-Web-App UI
@RestController
public class LeagueController {

    @Autowired
    private LeagueService leagueService;

    @GetMapping("/league/{leagueId}")
    public List<Team> getListOfAllTeamsInLeague(@PathVariable("leagueId") Integer leagueId) throws NullLeagueIdException {
        if (leagueId == null) {
            throw new NullLeagueIdException("League ID is null");
        }

        List<Team> teams = leagueService.getTeamsInLeagueByLeagueId(leagueId);

        return teams;
    }

}
