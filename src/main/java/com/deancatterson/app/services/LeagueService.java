package com.deancatterson.app.services;

import com.deancatterson.app.entity.Team;
import com.deancatterson.app.exception.NoTeamFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeagueService {

    @Value("${fpl-api.baseUrl}")
    private String fplBaseUrl;

    private String fplLeagueUrl = "leagues-classic/";

    public List<Team> getTeamsInLeagueByLeagueId(Integer leagueId) {
        String uri = fplBaseUrl + fplLeagueUrl + leagueId + "/";

        String responseBody;

        // TODO: refactor method to return a list of
        List<Team> teamList = new ArrayList<>();

        return teamList;
    }
}
