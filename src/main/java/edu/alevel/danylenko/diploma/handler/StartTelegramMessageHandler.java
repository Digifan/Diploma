package edu.alevel.danylenko.diploma.handler;


import edu.alevel.danylenko.diploma.bot.JobFinderBot;
import edu.alevel.danylenko.diploma.entity.Customer;
import edu.alevel.danylenko.diploma.entity.telegram.TelegramUpdate;
import edu.alevel.danylenko.diploma.service.JobFinderService;
import edu.alevel.danylenko.diploma.service.LocaleMessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StartTelegramMessageHandler implements TelegramMessageHandler {

    private final JobFinderBot jobFinderBot;
    private final JobFinderService botServices;
    private final LocaleMessageService localMessage;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {
        if (!telegramUpdate.getMessage().getText().startsWith(JobFinderBot.START_COMMAND)) {
            return;
        }
        Long customerId = telegramUpdate.getMessage().getFrom().getId();

        if (Objects.isNull(botServices.findCustomerById(customerId))) {
            Customer customer = new Customer();
            customer.setId(customerId);
            customer.setNickname(telegramUpdate.getMessage().getFrom().getUserName());
            botServices.saveCustomer(customer);

        }

        jobFinderBot.sendMessage(telegramUpdate.getMessage().getChat().getId(),
                localMessage.getMessage("start1") +
                telegramUpdate.getMessage().getFrom().getUserName() + ". " +
                localMessage.getMessage("start2"), jobFinderBot.getMainKeyboard());
    }
}
