package io.javabrains.ipldashboard.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.ipldashboard.model.Match;
import io.javabrains.ipldashboard.model.Team;
import io.javabrains.ipldashboard.repository.MatchRepository;
import io.javabrains.ipldashboard.repository.TeamRepository;

@RestController

public class TeamController {

	@Autowired
	TeamRepository teamRepository;
	@Autowired
	MatchRepository matchRepository;

	@CrossOrigin
	@GetMapping("team/{teamName}")
	public Team getTeam(@PathVariable String teamName) {
		Team team = teamRepository.findByTeamName(teamName);
		// Pageable pagable = PageRequest.of(0, 4);
		List<Match> matches = matchRepository.findLatestMatchesByName(teamName, 3);
		team.setMatches(matches);
		return team;

	}

	@CrossOrigin
	@GetMapping("team/{teamName}/matches")
	public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year) {
		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year + 1, 1, 1);
	//	List<Match> matches = matchRepository.findByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(teamName,startDate, endDate, teamName,
				//startDate, endDate);
		List<Match> matches = matchRepository.getMatchesByTeamBetweenDates(teamName,startDate, endDate);
		return matches;

	}
}
