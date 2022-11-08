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
public class SaveVacancyButtonHandler implements TelegramMessageHandler {

    private final JobFinderBot jobFinderBot;
    private final LocaleMessageService localMessage;



    @Override
    public void handle(TelegramUpdate telegramUpdate) {
        if (!telegramUpdate.getMessage().getText().startsWith(localMessage.getMessage("save.button")))
            return;

        Long chatId = telegramUpdate.getMessage().getChat().getId();
        jobFinderBot.mode = "saveVacancy";
        jobFinderBot.sendMessage(chatId, localMessage.getMessage("save.desc"));
    }
}
