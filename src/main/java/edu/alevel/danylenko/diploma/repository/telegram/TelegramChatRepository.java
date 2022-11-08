package edu.alevel.danylenko.diploma.repository.telegram;

import edu.alevel.danylenko.diploma.entity.telegram.TelegramChat;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface TelegramChatRepository extends PagingAndSortingRepository<TelegramChat, Long> {
    Optional<TelegramChat> findByUser_Id(Long userId);

}
