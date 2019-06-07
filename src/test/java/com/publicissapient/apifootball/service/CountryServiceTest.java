package com.publicissapient.apifootball.service;

import com.publicissapient.teamstandinginformer.service.CountryService;
import com.publicissapient.teamstandinginformer.vo.TeamStandingDetails;
import javaslang.control.Option;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountryServiceTest {

    private CountryService countryService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = mock(RestTemplate.class);
        countryService = new CountryService(restTemplate);

        when(restTemplate.getForObject("https://apifootball.com/api/?action=get_countries&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978", TeamStandingDetails[].class))
                .thenReturn(new TeamStandingDetails[]{new TeamStandingDetails("169", "England", null, null, null, null)});
    }

    @Test
    public void getCountryIfExists() {
        Option<TeamStandingDetails> country = countryService.getCountry("england");

        assertThat(country.isDefined(), is(true));
        assertThat(country.get().getCountryName(), is("England"));
        assertThat(country.get().getCountryId(), is("169"));
    }

    @Test
    public void returnEmptyIfItCountryDoesNotExist() {
        Option<TeamStandingDetails> country = countryService.getCountry("india");
        assertThat(country.isDefined(), is(false));
    }

}