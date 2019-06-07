package com.publicissapient.teamstandinginformer.controller;

import com.publicissapient.teamstandinginformer.service.CountryService;
import com.publicissapient.teamstandinginformer.service.LeagueService;
import com.publicissapient.teamstandinginformer.service.StandingService;
import com.publicissapient.teamstandinginformer.vo.TeamStandingDetails;
import javaslang.control.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StandingController.class, secure = false)
public class StandingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @MockBean
    private LeagueService leagueService;

    @MockBean
    private StandingService standingService;

    @Test
    public void showCorrectTeamStandingInfoForValidRequest() throws Exception {
        when(countryService.getCountry("France"))
                .thenReturn(Option.some(new TeamStandingDetails("173", "France", null, null, null, null)));

        when(leagueService.getLeague("173", "Ligue 2"))
                .thenReturn(Option.some(new TeamStandingDetails("173", "France", "128", "Ligue 2", null, null)));

        when(standingService.getTeamStandingDetails("128", "Brest"))
                .thenReturn(Option.some(new TeamStandingDetails("173", "France", "128", "Ligue 2", "128", "Brest")));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/standing?countryName=France&leagueName=Ligue 2&teamName=Brest")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(200));
        assertThat(mvcResult.getResponse().getContentAsString(), is("<ul>\n" +
                "<li>Country ID & Name : 173 - France</li>\n" +
                "<li>League ID & Name: 128 - Ligue 2</li>\n" +
                "<li>Team ID & Name : 128 - 128</li>\n" +
                "<li>Overall League Position: Brest</li>\n" +
                "</ul>"));
    }

    @Test
    public void failWith400WhenCountryDoesNotExists() throws Exception {
        when(countryService.getCountry("France"))
                .thenReturn(Option.none());

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/standing?countryName=France&leagueName=Ligue 2&teamName=Brest")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(400));
        assertThat(mvcResult.getResponse().getContentAsString(), is("Country does not exists."));
    }

    @Test
    public void failWith400WhenLeagueDoesNotExists() throws Exception {
        when(countryService.getCountry("France"))
                .thenReturn(Option.some(new TeamStandingDetails("173", "France", null, null, null, null)));

        when(leagueService.getLeague("173", "Ligue 2"))
                .thenReturn(Option.none());

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/standing?countryName=France&leagueName=Ligue 2&teamName=Brest")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(400));
        assertThat(mvcResult.getResponse().getContentAsString(), is("League does not exists."));
    }

    @Test
    public void failWith400WhenTeamDoesNotExists() throws Exception {
        when(countryService.getCountry("France"))
                .thenReturn(Option.some(new TeamStandingDetails("173", "France", null, null, null, null)));

        when(leagueService.getLeague("173", "Ligue 2"))
                .thenReturn(Option.some(new TeamStandingDetails("173", "France", "128", "Ligue 2", null, null)));

        when(standingService.getTeamStandingDetails("128", "Brest"))
                .thenReturn(Option.none());

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/standing?countryName=France&leagueName=Ligue 2&teamName=Brest")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(400));
        assertThat(mvcResult.getResponse().getContentAsString(), is("Team does not exists."));
    }

}