package edu.alevel.danylenko.diploma.handler;


import edu.alevel.danylenko.diploma.bot.JobFinderBot;
import edu.alevel.danylenko.diploma.entity.Customer;
import edu.alevel.danylenko.diploma.entity.Vacancy;
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
public class AddVacancyInputMessageHandler implements TelegramMessageHandler {

    private final JobFinderBot jobFinderBot;
    private final JobFinderService botServices;
    private final LocaleMessageService localMessage;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {
        String messageText = telegramUpdate.getMessage().getText();
        Long chatId = telegramUpdate.getMessage().getChat().getId();
        long customerId = telegramUpdate.getMessage().getFrom().getId();
        Customer customer = botServices.findCustomerById(customerId);
        if (jobFinderBot.mode.equals("addVacancy") && !messageText.equals(localMessage.getMessage("add.button"))) {

            String[] vacancyString = messageText.split(",");
            if (vacancyString.length == 6) {
                Vacancy vacancy = new Vacancy();
                vacancy.setTitle(vacancyString[0]);
                vacancy.setCompany(vacancyString[1]);
                vacancy.setPhone(vacancyString[2]);
                vacancy.setRegion(vacancyString[3]);
                vacancy.setCity(vacancyString[4]);
                vacancy.setSalary(vacancyString[5]);
                botServices.saveVacancy(vacancy);
                customer.getVacancies().add(vacancy);
                botServices.saveCustomer(customer);

            } else {jobFinderBot.sendMessage(chatId, localMessage.getMessage("add.error"));
                }
            jobFinderBot.mode = "";
        }
    }
}
