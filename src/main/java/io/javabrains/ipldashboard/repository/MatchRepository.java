package io.javabrains.ipldashboard.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.javabrains.ipldashboard.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

	List<Match> findByTeam1OrTeam2OrderByDateDesc(String team1,String team2,Pageable pagable);
//	List<Match> findByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(String team1,LocalDate date1,LocalDate date2,
//			String team2,LocalDate date3,LocalDate date4);
	
	@Query("select m from Match m where (m.team1=:teamName or m.team2=:teamName) and m.date between :startD and :endD order by m.date desc")
	List<Match> getMatchesByTeamBetweenDates(@Param("teamName") String team,@Param("startD") LocalDate start,@Param("endD")LocalDate end);
	
	default List<Match> findLatestMatchesByName(String teamName,int count){
		return findByTeam1OrTeam2OrderByDateDesc(teamName,teamName,PageRequest.of(0, count));
	}
}
