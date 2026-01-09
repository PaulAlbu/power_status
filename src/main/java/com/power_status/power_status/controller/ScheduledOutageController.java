package com.power_status.power_status.controller;

import com.power_status.power_status.dto.ScheduledOutageDto;
import com.power_status.power_status.service.external.ScheduledOutageServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("scheduled-outages")
public class ScheduledOutageController {

    //To be removed
    private final ScheduledOutageServiceClient scheduledOutageServiceClient;

    public ScheduledOutageController(ScheduledOutageServiceClient scheduledOutageServiceClient) {
        this.scheduledOutageServiceClient = scheduledOutageServiceClient;
    }

    @GetMapping("/today")
    public List<ScheduledOutageDto> getTodayAllScheduledOutages() {
       return scheduledOutageServiceClient.getTodayAllScheduledOutages();
    }

    @GetMapping("/nextfifteendays")
    public List<ScheduledOutageDto> getNextFifteenAllScheduledOutages() {
        return scheduledOutageServiceClient.getNextFifteenAllScheduledOutages();
    }

}
