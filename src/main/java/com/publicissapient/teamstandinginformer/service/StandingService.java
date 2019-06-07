package com.publicissapient.teamstandinginformer.service;

import com.publicissapient.teamstandinginformer.vo.TeamStandingDetails;
import javaslang.collection.List;
import javaslang.control.Option;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StandingService extends APIFootballBaseService {
    public StandingService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public Option<TeamStandingDetails> getTeamStandingDetails(final String leagueId, final String teamName) {
        return List
                .of((TeamStandingDetails[]) callApi("action=get_standings&league_id=" + leagueId, TeamStandingDetails[].class))
                .filter(teamStanding -> teamStanding.isSameTeam(teamName)).peekOption();
    }

}
