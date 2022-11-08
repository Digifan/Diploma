package edu.alevel.danylenko.diploma.handler;

import edu.alevel.danylenko.diploma.bot.JobFinderBot;
import edu.alevel.danylenko.diploma.entity.telegram.TelegramUpdate;
import edu.alevel.danylenko.diploma.service.LocaleMessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DeleteVacancyButtonHandler implements TelegramMessageHandler {

    private final JobFinderBot jobFinderBot;
    private final LocaleMessageService localMessage;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {
        if (!telegramUpdate.getMessage().getText().startsWith(localMessage.getMessage("delete.button")))
               {
            return;
        }

        Long chatId = telegramUpdate.getMessage().getChat().getId();
        jobFinderBot.mode = "deleteVacancy";
        jobFinderBot.sendMessage(chatId, localMessage.getMessage("delete.desc"), jobFinderBot.getMainKeyboard());
    }
}
