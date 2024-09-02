package kr.co.mrlee.story.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.mrlee.story.entity.SearchLogEntity;
import kr.co.mrlee.story.repository.resultset.GetRelationListResultSet;

@Repository
public interface SearchLogRepository extends JpaRepository<SearchLogEntity, Integer> {

	@Query(
        value = 
        "SELECT " + 
        "   relation_word as searchWord, " + 
        "   count(relation_word) AS count  " + 
        "FROM search_log  " + 
        "WHERE search_word = ?1  " + 
        " AND relation_word IS NOT NULL  " + 
        "GROUP BY relation_word  " + 
        "ORDER BY count DESC  " + 
        "LIMIT 15", 
        nativeQuery = true
    )
    List<GetRelationListResultSet> getRelationList(String searchWord);
}
