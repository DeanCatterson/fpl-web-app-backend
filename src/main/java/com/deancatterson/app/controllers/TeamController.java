package com.deancatterson.app.controllers;

import com.deancatterson.app.Team;
import com.deancatterson.app.exception.NoTeamFoundException;
import com.deancatterson.app.exception.NullTeamIdException;
import com.deancatterson.app.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// This Controller will receive requests from the FPL-Web-App UI
@RestController
public class TeamController {

    private TeamService teamService = new TeamService();

    @GetMapping("/team/{teamId}")
    public Team getTeamById(@PathVariable("teamId") Integer teamId) throws NullTeamIdException, NoTeamFoundException {
        if (teamId == null) {
            throw new NullTeamIdException("Team ID is null");
        }

        return teamService.getTeamById(teamId);
    }
}
