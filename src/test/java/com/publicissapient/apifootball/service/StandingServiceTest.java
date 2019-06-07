package com.publicissapient.apifootball.service;

import com.publicissapient.teamstandinginformer.service.StandingService;
import com.publicissapient.teamstandinginformer.vo.TeamStandingDetails;
import javaslang.control.Option;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StandingServiceTest {
    private StandingService standingService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = mock(RestTemplate.class);
        standingService = new StandingService(restTemplate);

        when(restTemplate.getForObject("https://apifootball.com/api/?action=get_standings&league_id=62&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978", TeamStandingDetails[].class))
                .thenReturn(new TeamStandingDetails[]{new TeamStandingDetails("169", "England", "62", "Premier League", "Arsenal", "4")});
    }

    @Test
    public void getLeagueIfExists() {
        Option<TeamStandingDetails> standingDetails = standingService.getTeamStandingDetails("62", "arsenal");

        assertThat(standingDetails.isDefined(), is(true));
        assertThat(standingDetails.get().getCountryName(), is("England"));
        assertThat(standingDetails.get().getTeamName(), is("Arsenal"));
        assertThat(standingDetails.get().getLeagueId(), is("62"));
        assertThat(standingDetails.get().getLeagueName(), is("Premier League"));
        assertThat(standingDetails.get().getOverallLeaguePosition(), is("4"));
    }

    @Test
    public void returnEmptyIfNoLeagueDoesNotExist() {
        Option<TeamStandingDetails> standingDetails = standingService.getTeamStandingDetails("62", "Some Other team");
        assertThat(standingDetails.isDefined(), is(false));
    }
}