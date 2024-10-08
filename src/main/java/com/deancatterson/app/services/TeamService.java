package com.deancatterson.app.services;

import com.deancatterson.app.entity.DetailedTeamInfo;
import com.deancatterson.app.entity.Team;
import com.deancatterson.app.entity.TeamHistory;
import com.deancatterson.app.exception.NoTeamFoundException;
import org.json.JSONArray;
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
    private String fplTeamHistoryUrl = "/history/";

    public DetailedTeamInfo getDetailedTeamInfo(Integer teamId) throws NoTeamFoundException {
        System.out.println("YYYYY in TeamService. teamId: " + teamId);

        DetailedTeamInfo detailedTeamInfo = new DetailedTeamInfo();
        Team team = new Team();
        TeamHistory history = new TeamHistory();

        try {
            team = getTeamById(teamId);
            history = getTeamHistoryById(teamId);
        } catch (Exception e) {
            System.out.println("YYYYY Exception: " + e);
            return detailedTeamInfo;
        }

        detailedTeamInfo.setTeam(team);
        detailedTeamInfo.setHistory(history);

        return detailedTeamInfo;
    }


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

            team = extractTeamDetailsFromJsonResponse(responseJSON);
        }

        return team;
    }

    public TeamHistory getTeamHistoryById(Integer teamId) throws NoTeamFoundException {
        String uri = fplBaseUrl + fplTeamUrl + teamId + fplTeamHistoryUrl;

        String responseBody;

        TeamHistory teamHistory = new TeamHistory();

        try {
            responseBody = client.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            // error retrieving team. Set teamId to -1
//            teamHistory.setId(-1);
            return teamHistory;
        }

        if (responseBody == null) {
            throw new NoTeamFoundException("No team was found.");
        } else {
            JSONObject responseJSON = new JSONObject(responseBody);

            teamHistory = extractTeamHistoryDetailsFromJsonResponse(responseJSON);
        }

        return teamHistory;
    }

    protected TeamHistory extractTeamHistoryDetailsFromJsonResponse(JSONObject jsonObject) {
        System.out.println("ZZZZZ jsonObject: " + jsonObject);
        TeamHistory teamHistory = new TeamHistory();

        JSONArray currentSeasonArray = jsonObject.getJSONArray("current");

        Integer currentSeasonHighestOverallRank = 999999999;
        Integer currentSeasonHighestWeeklyRank = 999999999;
        Integer currentSeasonHighestWeeklyPoints = 0;

        JSONObject gameweek;

        for (int i = currentSeasonArray.length() - 1; i > -1; i--) {
            gameweek = currentSeasonArray.getJSONObject(i);

            if (gameweek.isNull("overall_rank") || gameweek.isNull("rank") || gameweek.isNull("points")) {
                // Skip the gameweek with a null value
                i--;
            } else {

                if (gameweek.getInt("overall_rank") < currentSeasonHighestOverallRank) {
                    currentSeasonHighestOverallRank = gameweek.getInt("overall_rank");
                }

                if (gameweek.getInt("rank") < currentSeasonHighestWeeklyRank) {
                    currentSeasonHighestWeeklyRank = gameweek.getInt("rank");
                }

                if (gameweek.getInt("points") > currentSeasonHighestWeeklyPoints) {
                    currentSeasonHighestWeeklyPoints = gameweek.getInt("points");
                }
            }
        }


        JSONArray pastSeasonsArray = jsonObject.getJSONArray("past");
        Integer numberOfCompletedSeasons = pastSeasonsArray.length();


        Integer highestYearlyFinishRank = 999999999;
        String highestYearlyFinishRankSeason = "1970/71";

        Integer highestYearlyFinishPoints = -1;
        String highestYearlyFinishPointsSeason = "1970/71";

        Integer averageYearlyRank = 0;
        Integer averageYearlyPoints = 0;
        
        long rankCounter = 0;
        long pointsCounter = 0;
        
        JSONObject season;

        for (int i = 0; i < pastSeasonsArray.length(); i++) {
            season = pastSeasonsArray.getJSONObject(i);

            if (season.getInt("rank") < highestYearlyFinishRank) {
                highestYearlyFinishRank = season.getInt("rank");
                highestYearlyFinishRankSeason = season.getString("season_name");
            }
            
            if (season.getInt("total_points") > highestYearlyFinishPoints) {
                highestYearlyFinishPoints = season.getInt("total_points");
                highestYearlyFinishPointsSeason = season.getString("season_name");
            }
            
            rankCounter += season.getInt("rank");
            pointsCounter += season.getInt("total_points");
        }

        JSONArray chipsArray = jsonObject.getJSONArray("chips");
        String[] chips = new String[chipsArray.length()];
        JSONObject chip;

        for (int i = 0; i < chipsArray.length(); i++) {
            chip = chipsArray.getJSONObject(i);

            chips[i] = chip.getString("name");
        }


            teamHistory.setCurrentSeasonHighestOverallRank(currentSeasonHighestOverallRank);

        teamHistory.setCurrentSeasonHighestWeeklyRank(currentSeasonHighestWeeklyRank);

        teamHistory.setCurrentSeasonHighestWeeklyPoints(currentSeasonHighestWeeklyPoints);
        teamHistory.setHighestYearlyFinishRank(highestYearlyFinishRank);

        teamHistory.setHighestYearlyFinishRankSeason(highestYearlyFinishRankSeason);
        teamHistory.setHighestYearlyFinishPoints(highestYearlyFinishPoints);
        teamHistory.setHighestYearlyFinishPointsSeason(highestYearlyFinishPointsSeason);

        teamHistory.setnumberOfCompletedSeasons(numberOfCompletedSeasons);

        teamHistory.setChips(chips);

        if (numberOfCompletedSeasons > 0) {
            teamHistory.setAverageYearlyRank((int) (rankCounter / numberOfCompletedSeasons));
            teamHistory.setAverageYearlyPoints((int) (pointsCounter / numberOfCompletedSeasons));
        } else {
            teamHistory.setAverageYearlyRank(1);
            teamHistory.setAverageYearlyPoints(1);
        }
        return teamHistory;
    }


    protected Team extractTeamDetailsFromJsonResponse(JSONObject jsonObject) {
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
