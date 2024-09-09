package com.deancatterson.app.controllers;

import com.deancatterson.app.entity.Team;
import com.deancatterson.app.exception.NoTeamFoundException;
import com.deancatterson.app.exception.NullTeamIdException;
import com.deancatterson.app.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// This Controller will receive requests from the FPL-Web-App UI
@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;
//    private TeamService teamService = new TeamService();

    @GetMapping("/team/{teamId}")
    public ResponseEntity<Team> getTeamById(@PathVariable("teamId") Integer teamId) throws NullTeamIdException, NoTeamFoundException {
        Team team = new Team();

        team = teamService.getTeamById(teamId);

        System.out.println("XXXXX team.getId(): " + team.getId());

        if (team.getId() == -1) {
//            throw new NullTeamIdException("Team ID is null");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(team, HttpStatus.OK);
    }
}
