package com.deancatterson.app.controllers;

import com.deancatterson.app.Team;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

// This Controller receives requests from the FPL-Web-App UI
@Controller
@RequestMapping("team/{leagueId}")
public class LeagueController {
    // TODO: view mini league info feature

    public static List<Team> getListOfAllTeamsInLeague(String leagueId) {
        List<Team> teams = new ArrayList<>();

        return teams;
    }
}
