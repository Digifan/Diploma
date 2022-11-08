package edu.alevel.danylenko.diploma.handler;

import edu.alevel.danylenko.diploma.entity.telegram.TelegramUpdate;

public interface TelegramMessageHandler {
    void handle(TelegramUpdate telegramUpdate);
}
