package com.power_status.power_status.dto;

import lombok.Data;

@Data
public class IntrerupereAccidentala {
    private Long id;
    private String judet;
    private String localitate;
    private String sat;
    private String tipArtera;
    private String artera;
    private String numar;
    private String bloc;
    private String adresa;
    private String dataStart;
    private String dataStop;

}
