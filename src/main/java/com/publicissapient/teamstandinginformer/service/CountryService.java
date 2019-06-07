package com.publicissapient.teamstandinginformer.service;

import com.publicissapient.teamstandinginformer.vo.TeamStandingDetails;
import javaslang.collection.List;
import javaslang.control.Option;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CountryService extends APIFootballBaseService {
    public CountryService(final RestTemplate restTemplate) {
        super(restTemplate);
    }

    public Option<TeamStandingDetails> getCountry(final String countryName) {
        return List
                .of((TeamStandingDetails[]) callApi("action=get_countries", TeamStandingDetails[].class))
                .filter(country -> country.isSameCountry(countryName)).peekOption();
    }

}
