package edu.alevel.danylenko.diploma.repository.telegram;

import edu.alevel.danylenko.diploma.entity.telegram.TelegramUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramUserRepository extends PagingAndSortingRepository<TelegramUser, Long> {
}
