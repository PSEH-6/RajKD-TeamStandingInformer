package com.publicissapient.teamstandinginformer.controller;

import com.publicissapient.teamstandinginformer.service.CountryService;
import com.publicissapient.teamstandinginformer.service.LeagueService;
import com.publicissapient.teamstandinginformer.service.StandingService;
import com.publicissapient.teamstandinginformer.vo.TeamStandingDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StandingController {
    private static final Logger LOGGER = LogManager.getLogger(StandingController.class);

    private final CountryService countryService;
    private final LeagueService leagueService;
    private final StandingService standingService;

    public StandingController(final CountryService countryService,
                              final LeagueService leagueService,
                              final StandingService standingService) {
        this.countryService = countryService;
        this.leagueService = leagueService;
        this.standingService = standingService;
    }

    @RequestMapping("/standing")
    public ResponseEntity<String> getTeamStanding(@RequestParam(value = "countryName") final String countryName,
                                                  @RequestParam(value = "leagueName") final String leagueName,
                                                  @RequestParam(value = "teamName") final String teamName) {

        LOGGER.info(String.format("countryName: %s, leagueName: %s, teamName: %s", countryName, leagueName, teamName));

        return countryService.getCountry(countryName)
                .map(country -> getLeagueAndTeamDetails(country.getCountryId(), leagueName, teamName))
                .getOrElse(() -> ResponseEntity.badRequest().body("Country does not exists."));
    }

    private ResponseEntity<String> getLeagueAndTeamDetails(String countryId, final String leagueName, final String teamName) {
        return leagueService.getLeague(countryId, leagueName)
                .map(league -> getTeamDetails(countryId, league.getLeagueId(), teamName))
                .getOrElse(() -> ResponseEntity.badRequest().body("League does not exists."));
    }

    private ResponseEntity<String> getTeamDetails(final String countryId, final String leagueId, final String teamName) {
        return standingService.getTeamStandingDetails(leagueId, teamName)
                .map(teamStandingDetails -> {
                    teamStandingDetails.setCountryId(countryId);
                    return ResponseEntity.ok(responseBuilder(teamStandingDetails));
                })
                .getOrElse(() -> ResponseEntity.badRequest().body("Team does not exists."));
    }

    private String responseBuilder(final TeamStandingDetails teamStandingDetails) {
        final String response = "<ul>\n" +
                "<li>Country ID & Name : (%s) - %s</li>\n" +
                "<li>League ID & Name: (%s) - %s</li>\n" +
                "<li>Team ID & Name : (%s) - %s</li>\n" +
                "<li>Overall League Position: %s</li>\n" +
                "</ul>";

        return String.format(response,
                teamStandingDetails.getCountryId(),
                teamStandingDetails.getCountryName(),
                teamStandingDetails.getLeagueId(),
                teamStandingDetails.getLeagueName(),
                teamStandingDetails.getTeamName(),
                teamStandingDetails.getTeamName(),
                teamStandingDetails.getOverallLeaguePosition());
    }
}
