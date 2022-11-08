package edu.alevel.danylenko.diploma.bot;

import edu.alevel.danylenko.diploma.entity.telegram.TelegramUpdate;
import edu.alevel.danylenko.diploma.handler.TelegramMessageHandler;
import edu.alevel.danylenko.diploma.service.BotServices;
import edu.alevel.danylenko.diploma.service.LocaleMessageService;
import edu.alevel.danylenko.diploma.service.TelegramUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class JobFinderBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(JobFinderBot.class);
    final BotCredentials botCredentials;
    final BotServices botServices;
    final TelegramUpdateService telegramUpdateService;
    final LocaleMessageService localMessage;
    final List<TelegramMessageHandler> telegramMessageHandlers;


    public static final String START_COMMAND = "/start";
    public static final String HELP_COMMAND = "/help";
    public static final String PROFILE_COMMAND = "/profile";

    public String mode;

    @Autowired
    public JobFinderBot(TelegramUpdateService telegramUpdateService, BotCredentials botCredentials, BotServices botServices, LocaleMessageService localMessage, @Lazy List<TelegramMessageHandler> telegramMessageHandlers) {
        this.botCredentials = botCredentials;
        this.telegramUpdateService = telegramUpdateService;
        this.botServices = botServices;
        this.localMessage = localMessage;
        this.telegramMessageHandlers = telegramMessageHandlers;
        this.mode = "";
        List<BotCommand> commandsList = new ArrayList<>();
        commandsList.add(new BotCommand(START_COMMAND, localMessage.getMessage("start.command")));
        commandsList.add(new BotCommand(PROFILE_COMMAND, localMessage.getMessage("profile.command")));
        commandsList.add(new BotCommand(HELP_COMMAND, localMessage.getMessage("help.command")));
        try {
            this.execute(new SetMyCommands(commandsList, new BotCommandScopeDefault(), null ));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return botCredentials.getUsername();
    }

    @Override
    public String getBotToken() {
        return botCredentials.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            TelegramUpdate telegramUpdate = telegramUpdateService.save(update);

            telegramMessageHandlers.forEach(
                    telegramMessageHandler -> telegramMessageHandler.handle(telegramUpdate));
        }
    }

        public void sendMessage ( long chatId, String reply, ReplyKeyboard keyboard){
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(reply);
            message.setReplyMarkup(keyboard);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        public void sendMessage ( long chatId, String reply){
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(reply);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        public ReplyKeyboardMarkup getMainKeyboard () {
            KeyboardRow buttons = new KeyboardRow();
            var addVacancyButton = new KeyboardButton("Розмістити вакансію");
            var findVacancyButton = new KeyboardButton("Знайти вакансію");
            List<KeyboardRow> buttonRows = new ArrayList<>();
            buttons.add(addVacancyButton);
            buttons.add(findVacancyButton);
            buttonRows.add(buttons);
            ReplyKeyboardMarkup mainKeyboard = new ReplyKeyboardMarkup();
            mainKeyboard.setKeyboard(buttonRows);
            mainKeyboard.setResizeKeyboard(true);
            mainKeyboard.setOneTimeKeyboard(true);
            return mainKeyboard;
        }
}
