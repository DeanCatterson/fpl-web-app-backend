package com.deancatterson.app.entity;

public class TeamHistory {
    Integer currentSeasonHighestOverallRank;
    Integer currentSeasonHighestWeeklyRank;
    Integer currentSeasonHighestWeeklyPoints;

    Integer highestYearlyFinishRank;
    String highestYearlyFinishRankSeason;

    Integer highestYearlyFinishPoints;
    String highestYearlyFinishPointsSeason;

    Integer averageYearlyRank;
    Integer averageYearlyPoints;

    Integer numberOfCompletedSeasons;


    public Integer getCurrentSeasonHighestOverallRank() {
        return currentSeasonHighestOverallRank;
    }

    public void setCurrentSeasonHighestOverallRank(Integer currentSeasonHighestOverallRank) {
        this.currentSeasonHighestOverallRank = currentSeasonHighestOverallRank;
    }

    public Integer getCurrentSeasonHighestWeeklyRank() {
        return currentSeasonHighestWeeklyRank;
    }

    public void setCurrentSeasonHighestWeeklyRank(Integer currentSeasonHighestWeeklyRank) {
        this.currentSeasonHighestWeeklyRank = currentSeasonHighestWeeklyRank;
    }

    public Integer getCurrentSeasonHighestWeeklyPoints() {
        return currentSeasonHighestWeeklyPoints;
    }

    public void setCurrentSeasonHighestWeeklyPoints(Integer currentSeasonHighestWeeklyPoints) {
        this.currentSeasonHighestWeeklyPoints = currentSeasonHighestWeeklyPoints;
    }

    public Integer getHighestYearlyFinishRank() {
        return highestYearlyFinishRank;
    }

    public void setHighestYearlyFinishRank(Integer highestYearlyFinishRank) {
        this.highestYearlyFinishRank = highestYearlyFinishRank;
    }

    public Integer getHighestYearlyFinishPoints() {
        return highestYearlyFinishPoints;
    }

    public void setHighestYearlyFinishPoints(Integer highestYearlyFinishPoints) {
        this.highestYearlyFinishPoints = highestYearlyFinishPoints;
    }

    public String getHighestYearlyFinishPointsSeason() {
        return highestYearlyFinishPointsSeason;
    }

    public void setHighestYearlyFinishPointsSeason(String highestYearlyFinishPointsSeason) {
        this.highestYearlyFinishPointsSeason = highestYearlyFinishPointsSeason;
    }

    public String getHighestYearlyFinishRankSeason() {
        return highestYearlyFinishRankSeason;
    }

    public void setHighestYearlyFinishRankSeason(String highestYearlyFinishRankSeason) {
        this.highestYearlyFinishRankSeason = highestYearlyFinishRankSeason;
    }

    public Integer getnumberOfCompletedSeasons() {
        return numberOfCompletedSeasons;
    }

    public void setnumberOfCompletedSeasons(Integer numberOfCompletedSeasons) {
        this.numberOfCompletedSeasons = numberOfCompletedSeasons;
    }

    public Integer getAverageYearlyRank() {
        return averageYearlyRank;
    }

    public void setAverageYearlyRank(Integer averageYearlyRank) {
        this.averageYearlyRank = averageYearlyRank;
    }

    public Integer getAverageYearlyPoints() {
        return averageYearlyPoints;
    }

    public void setAverageYearlyPoints(Integer averageYearlyPoints) {
        this.averageYearlyPoints = averageYearlyPoints;
    }
}
