package com.deancatterson.app.services;

import com.deancatterson.app.entity.Team;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeagueService {

    @Value("${fpl-api.baseUrl}")
    private String fplBaseUrl;

    WebClient client = WebClient.create();

    private String fplLeagueUrl = "leagues-classic/";

    public List<Team> getTeamsInLeagueByLeagueId(Integer leagueId) {
        String uri = fplBaseUrl + fplLeagueUrl + leagueId + "/standings/";

        String responseBody;

        List<Team> league = new ArrayList<>();

        try {
            responseBody = client.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            System.out.println("XXXXX Exception: " + e);
            return league;
        }
        JSONObject responseJSON = new JSONObject(responseBody);


        league = extractDetailsFromJsonResponse(responseJSON);

        return league;
    }

    protected List<Team> extractDetailsFromJsonResponse(JSONObject jsonObject) {

        JSONObject standingsJson = jsonObject.getJSONObject("standings");
        JSONArray resultsJsonArray = standingsJson.getJSONArray("results");

        List<Team> league = new ArrayList<>();

        for (int i = 0; i < resultsJsonArray.length(); i++) {
            Team team = new Team();

            JSONObject currentTeam = resultsJsonArray.getJSONObject(i);

            // Extract require fields from each entry in the results array
            Integer teamId = currentTeam.getInt("id");
            String managerName = currentTeam.getString("player_name");
            String teamName = currentTeam.getString("entry_name");

            // TODO: below fields will cause an error for newly created teams, which have points & rank set to null.
            Integer overallPoints = currentTeam.getInt("total");
            Integer rank = currentTeam.getInt("rank");

            team.setId(teamId);
            team.setManagerName(managerName);
            team.setTeamName(teamName);
            team.setPoints(overallPoints);
            team.setRank(rank);

            league.add(team);
        }

        return league;
    }
}
