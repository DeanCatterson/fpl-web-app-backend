package com.deancatterson.app.services;

import com.deancatterson.app.entity.Team;
import com.deancatterson.app.exception.NoTeamFoundException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TeamService {

    @Value("${fpl-api.baseUrl}")
    private String fplBaseUrl;

    WebClient client = WebClient.create();

    private String fplTeamUrl = "entry/";


    public Team getTeamById(Integer teamId) throws NoTeamFoundException {
        String uri = fplBaseUrl + fplTeamUrl + teamId + "/";

        String responseBody;

        Team team = new Team();

        try {
            responseBody = client.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            // error retrieving team. Set teamId to -1
            team.setId(-1);
            return team;
        }

        if (responseBody == null) {
            throw new NoTeamFoundException("No team was found.");
        } else {
            JSONObject responseJSON = new JSONObject(responseBody);

            team = extractDetailsFromJsonResponse(responseJSON);

            System.out.println("XXXXX team name: " + team.getTeamName());
        }

        return team;
    }

    protected Team extractDetailsFromJsonResponse(JSONObject jsonObject) {
        Team team = new Team();

        // Extract require fields from the massive json object returned from the fpl api
        Integer teamId = jsonObject.getInt("id");
        String managerFirstName = jsonObject.getString("player_first_name");
        String managerLastName = jsonObject.getString("player_last_name");
        String teamName = jsonObject.getString("name");

        // TODO: below fields will cause an error for newly created teams, which have points & rank set to null.
        Integer overallPoints = jsonObject.getInt("summary_overall_points");
        Integer overallRank = jsonObject.getInt("summary_overall_rank");

        team.setId(teamId);
        team.setManagerName(managerFirstName + " " + managerLastName);
        team.setTeamName(teamName);
        team.setPoints(overallPoints);
        team.setRank(overallRank);

        return team;
    }
}
