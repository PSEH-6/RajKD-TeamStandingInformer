package com.publicissapient.teamstandinginformer.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TeamStandingDetails {
    @JsonProperty(value = "country_id")
    private String countryId;

    @JsonProperty(value = "country_name")
    private String countryName;

    @JsonProperty(value = "league_id")
    private String leagueId;

    @JsonProperty(value = "league_name")
    private String leagueName;

    @JsonProperty(value = "team_name")
    private String teamName;

    @JsonProperty(value = "overall_league_position")
    private String overallLeaguePosition;

    public TeamStandingDetails(final String countryId, final String countryName,
                               final String leagueId, final String leagueName,
                               final String teamName, final String overallLeaguePosition) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.leagueId = leagueId;
        this.leagueName = leagueName;
        this.teamName = teamName;
        this.overallLeaguePosition = overallLeaguePosition;
    }

    public boolean isSameTeam(final String compareFrom) {
        return compareFrom.toLowerCase().contentEquals(this.getTeamName().toLowerCase());
    }

    public boolean isSameLeague(final String compareFrom) {
        return compareFrom.toLowerCase().contentEquals(this.getLeagueName().toLowerCase());
    }

    public boolean isSameCountry(final String compareFrom) {
        return compareFrom.toLowerCase().contentEquals(this.getCountryName().toLowerCase());
    }

}
