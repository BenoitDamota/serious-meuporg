package com.example.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Task;

/**
 * Created by Florentin NOËL on 11/05/17.
 */
@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
	
	/**
	 * Retourne la liste des taches aux enchères dasn la date de fin est supérieure à la date en parametre
	 * @param boardId : id du tableau
	 * @param dateEndBid : date de fin des enchères
	 * @return liste des taches
	 */
	@Query(value="SELECT t.* FROM task t WHERE t.is_bid = true AND t.board_id=?1 AND date_end_bid > ?2", nativeQuery = true)
	List<Task> findTaskBidByBoardIdAndDate(Long boardId, Date dateEndBid);

	/**
	 * Retourne la liste des tâches dont le titre contient la requête
	 * @param query requête
	 * @return liste de Task
	 */
	List<Task> findByTitleContaining(String query);
}
