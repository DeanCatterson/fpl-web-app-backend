package com.deancatterson.app.services;

import com.deancatterson.app.Team;
import com.deancatterson.app.exception.NoTeamFoundException;
import com.deancatterson.app.fplconfig.FPLAPIConfig;
import org.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TeamService {
    // TODO: move url's to config file
//    @Value("${fpl-api.baseUrl}")
    private String fplBaseUrl = "https://fantasy.premierleague.com/api/";

    private String fplTeamUrl = "entry/";

    WebClient client = WebClient.create();

//    @Autowired
    private FPLAPIConfig fplapiConfig;


    public Team getTeamById(Integer teamId) throws NoTeamFoundException {
        String uri = fplBaseUrl + fplTeamUrl + teamId + "/";


        WebClient.ResponseSpec responseSpec = client.get()
                .uri(uri)
                .retrieve();

        String responseBody = responseSpec
                .bodyToMono(String.class)
                .block();

        Team team;

        if (responseBody == null) {
            throw new NoTeamFoundException("No team was found.");
        } else {
            JSONObject responseJSON = new JSONObject(responseBody);

            team = extractDetailsFromJsonResponse(responseJSON);
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
        Integer overallPoints = jsonObject.getInt("summary_overall_points");
        Integer overallRank = jsonObject.getInt("summary_overall_rank");

        team.setId(teamId);
        team.setManagerName(managerFirstName + " " + managerLastName);
        team.setTeamName(teamName);
        team.setPoints(overallPoints);
        team.setOverallRank(overallRank);

        return team;
    }
}
