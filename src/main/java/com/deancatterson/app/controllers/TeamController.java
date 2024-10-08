package com.deancatterson.app.controllers;

import com.deancatterson.app.entity.DetailedTeamInfo;
import com.deancatterson.app.entity.Team;
import com.deancatterson.app.entity.TeamHistory;
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

    @GetMapping("/team/{teamId}")
    public ResponseEntity<Team> getTeamById(@PathVariable("teamId") Integer teamId) throws NullTeamIdException, NoTeamFoundException {
        Team team = teamService.getTeamById(teamId);

        if (team.getId() == -1) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(team, HttpStatus.OK);
    }


    @GetMapping("/team/detailed/{teamId}")
    public ResponseEntity<DetailedTeamInfo> getDetailedTeamHistoryById(@PathVariable("teamId") Integer teamId) throws NullTeamIdException, NoTeamFoundException {
        System.out.println("XXXXX in TeamController");
        DetailedTeamInfo detailedTeamInfo = teamService.getDetailedTeamInfo(teamId);

        return new ResponseEntity<>(detailedTeamInfo, HttpStatus.OK);
    }


    @GetMapping("/team/history/{teamId}")
    public ResponseEntity<TeamHistory> getTeamHistoryById(@PathVariable("teamId") Integer teamId) throws NullTeamIdException, NoTeamFoundException {
        TeamHistory teamHistory = teamService.getTeamHistoryById(teamId);

        return new ResponseEntity<>(teamHistory, HttpStatus.OK);
    }


    }
