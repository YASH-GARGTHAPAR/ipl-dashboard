package io.javabrains.ipldashboard.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import io.javabrains.ipldashboard.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

	List<Match> findByTeam1OrTeam2OrderByDateDesc(String team1,String team2,Pageable pagable);
	
	default List<Match> findLatestMatchesByName(String teamName,int count){
		return findByTeam1OrTeam2OrderByDateDesc(teamName,teamName,PageRequest.of(0, count));
	}
}
