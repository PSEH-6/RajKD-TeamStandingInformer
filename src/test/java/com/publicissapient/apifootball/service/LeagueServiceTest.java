package com.publicissapient.apifootball.service;

import com.publicissapient.teamstandinginformer.service.LeagueService;
import com.publicissapient.teamstandinginformer.vo.TeamStandingDetails;
import javaslang.control.Option;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LeagueServiceTest {

    private LeagueService leagueService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = mock(RestTemplate.class);
        leagueService = new LeagueService(restTemplate);

        when(restTemplate.getForObject("https://apifootball.com/api/?action=get_leagues&country_id=169&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978", TeamStandingDetails[].class))
                .thenReturn(new TeamStandingDetails[]{new TeamStandingDetails("169", "England", "62", "Premier League", null, null)});
    }

    @Test
    public void getLeagueIfExists() {
        Option<TeamStandingDetails> league = leagueService.getLeague("169", "premier league");

        assertThat(league.isDefined(), is(true));
        assertThat(league.get().getCountryName(), is("England"));
        //assertThat(league.get().getCountryId(), is("169"));
        assertThat(league.get().getLeagueId(), is("62"));
        assertThat(league.get().getLeagueName(), is("Premier League"));
    }

    @Test
    public void returnEmptyIfNoLeagueDoesNotExist() {
        Option<TeamStandingDetails> league = leagueService.getLeague("169", "Some Other League");
        assertThat(league.isDefined(), is(false));
    }
}