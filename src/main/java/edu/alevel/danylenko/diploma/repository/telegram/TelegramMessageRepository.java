package edu.alevel.danylenko.diploma.repository.telegram;

import edu.alevel.danylenko.diploma.entity.telegram.TelegramMessage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramMessageRepository extends PagingAndSortingRepository<TelegramMessage, Integer> {
}
