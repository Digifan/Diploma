package edu.alevel.danylenko.diploma.bot;

import lombok.Data;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
public class BotCredentials {
    private final String token = "YOUR_TOKEN";
    private final String username = "urvacancies_bot";


}
