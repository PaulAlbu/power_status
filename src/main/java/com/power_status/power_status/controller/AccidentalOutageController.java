package com.power_status.power_status.controller;

import com.power_status.power_status.dto.AccidentalPowerOutDto;
import com.power_status.power_status.service.external.AccidentalOutageServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccidentalOutageController {
    private final AccidentalOutageServiceClient accidentOutClient;

    public AccidentalOutageController(AccidentalOutageServiceClient accidentOutClient) {
        this.accidentOutClient = accidentOutClient;
    }

    @GetMapping("/getAccidentalOutages")
    public List<AccidentalPowerOutDto> getAllAccidentalOutages() {
        return accidentOutClient.getAllAccidentalPowerOut();
    }

}
