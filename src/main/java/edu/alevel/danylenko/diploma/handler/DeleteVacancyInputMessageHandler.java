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


@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DeleteVacancyInputMessageHandler implements TelegramMessageHandler {

    private final JobFinderBot jobFinderBot;
    private final JobFinderService botServices;
    private final LocaleMessageService localMessage;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {
        if (telegramUpdate.getMessage().getText() == null) {
            return;
        }
        String messageText = telegramUpdate.getMessage().getText();
        long customerId = telegramUpdate.getMessage().getFrom().getId();
        Customer customer = botServices.findCustomerById(customerId);

        if (jobFinderBot.mode.equals("deleteVacancy") && !messageText.equals(localMessage.getMessage("delete.button"))) {

            customer.getVacancies().removeIf(vacancy -> vacancy.getId() == Integer.parseInt(messageText));
            botServices.saveCustomer(customer);
            jobFinderBot.mode = "";}

    }
}
