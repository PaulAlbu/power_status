package com.power_status.power_status.controller;

import com.power_status.power_status.dto.IntrerupereAccidentala;
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
    public List<IntrerupereAccidentala> getAccidentalOutages() {
        return accidentOutClient.getAllPowerOut();
    }

}
