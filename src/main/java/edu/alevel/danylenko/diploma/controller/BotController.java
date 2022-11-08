package edu.alevel.danylenko.diploma.controller;

import edu.alevel.danylenko.diploma.service.JobFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BotController {
    @Autowired
    private JobFinderService jobFinderService;


}
