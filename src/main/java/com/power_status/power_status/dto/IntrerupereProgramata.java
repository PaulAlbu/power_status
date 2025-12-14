package com.power_status.power_status.dto;

import lombok.Data;

@Data
public class IntrerupereProgramata {
    private Long id;
    private String nivel;
    private String sucursala;
    private String judet;
    private String centru;
    private String linie;
    private String denumireInst;
    private String ansambluFunctional;
    private String catInstalatii;
    private String instalatie;
    private String dataProgramareStart;
    private String dataProgramareStop;
    private String durataProgramare;
    private String adreseLucrari;
    private String intervaluriProgramari;
}
