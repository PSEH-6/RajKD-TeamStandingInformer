package com.publicissapient.teamstandinginformer.service;

import com.publicissapient.teamstandinginformer.vo.TeamStandingDetails;
import javaslang.collection.List;
import javaslang.control.Option;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LeagueService extends APIFootballBaseService {
    public LeagueService(final RestTemplate restTemplate) {
        super(restTemplate);
    }

    public Option<TeamStandingDetails> getLeague(final String countryId, final String leagueName) {
        return List
                .of((TeamStandingDetails[]) callApi("action=get_leagues&country_id=" + countryId, TeamStandingDetails[].class))
                .filter(league -> league.isSameLeague(leagueName)).peekOption();
    }

}
