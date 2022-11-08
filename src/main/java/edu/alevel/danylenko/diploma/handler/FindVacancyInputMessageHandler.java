package edu.alevel.danylenko.diploma.handler;

import edu.alevel.danylenko.diploma.bot.JobFinderBot;
import edu.alevel.danylenko.diploma.entity.Vacancy;
import edu.alevel.danylenko.diploma.entity.telegram.TelegramUpdate;
import edu.alevel.danylenko.diploma.service.JobFinderService;
import edu.alevel.danylenko.diploma.service.LocaleMessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;



@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FindVacancyInputMessageHandler implements TelegramMessageHandler {

    private final JobFinderBot jobFinderBot;
    private final JobFinderService botServices;
    private final LocaleMessageService localMessage;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {
        if (telegramUpdate.getMessage().getText() == null) {
            return;
        }
        String messageText = telegramUpdate.getMessage().getText();
        Long chatId = telegramUpdate.getMessage().getChat().getId();

        if (jobFinderBot.mode.equals("findVacancy") && !messageText.equals(localMessage.getMessage("find.button")))
        {
            List<Vacancy> vacancies = botServices.vacancyList().stream().
                    filter(e -> e.getTitle().toLowerCase().startsWith(messageText.toLowerCase())).toList();
            KeyboardRow buttons = new KeyboardRow();
            var saveVacancyButton = new KeyboardButton(localMessage.getMessage("save.button"));
            var findVacancyButton = new KeyboardButton(localMessage.getMessage("find.button"));
            List<KeyboardRow> buttonRows = new ArrayList<>();
            buttons.add(saveVacancyButton); buttons.add(findVacancyButton);
            buttonRows.add(buttons);
            ReplyKeyboardMarkup gotVacancyKeyboard = new ReplyKeyboardMarkup();
            gotVacancyKeyboard.setKeyboard(buttonRows);
            gotVacancyKeyboard.setResizeKeyboard(true);
            gotVacancyKeyboard.setOneTimeKeyboard(true);

            if (!vacancies.isEmpty()) jobFinderBot.sendMessage(chatId, localMessage.getMessage("find.result")+"\n" + vacancies, gotVacancyKeyboard);
            else jobFinderBot.sendMessage(chatId, localMessage.getMessage("find.empty"));
            jobFinderBot.mode = "";
        }
    }
}
