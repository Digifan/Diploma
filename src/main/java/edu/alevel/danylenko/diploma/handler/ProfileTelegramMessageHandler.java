package edu.alevel.danylenko.diploma.handler;

import edu.alevel.danylenko.diploma.bot.JobFinderBot;
import edu.alevel.danylenko.diploma.entity.telegram.TelegramUpdate;
import edu.alevel.danylenko.diploma.service.BotServices;
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
public class ProfileTelegramMessageHandler implements TelegramMessageHandler {

    private final JobFinderBot jobFinderBot;
    private final BotServices botServices;
    private final LocaleMessageService localMessage;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {
        if (!telegramUpdate.getMessage().getText().startsWith(JobFinderBot.PROFILE_COMMAND)) {
            return;
        }
        long chatId = telegramUpdate.getMessage().getChat().getId();
        long userId = telegramUpdate.getMessage().getFrom().getId();
        if (botServices.findCustomerById(userId).getVacancies().size() > 0) {
            KeyboardRow buttons = new KeyboardRow();
            var addVacancyButton = new KeyboardButton(localMessage.getMessage("add.button"));
            var findVacancyButton = new KeyboardButton(localMessage.getMessage("find.button"));
            var deleteVacancyButton = new KeyboardButton(localMessage.getMessage("delete.button"));
            List<KeyboardRow> buttonRows = new ArrayList<>();
            buttons.add(addVacancyButton); buttons.add(findVacancyButton);
            buttons.add(deleteVacancyButton);
            buttonRows.add(buttons);
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setKeyboard(buttonRows);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(true);
        jobFinderBot.sendMessage(chatId, localMessage.getMessage("find.list")+ "\n" +
                botServices.findCustomerById(userId).getVacancies().toString(), replyKeyboardMarkup); }
        else jobFinderBot.sendMessage(chatId, localMessage.getMessage("find.no_profile"), jobFinderBot.getMainKeyboard());
    }
}
