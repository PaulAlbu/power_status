package com.power_status.power_status.service.external;

import com.power_status.power_status.dto.AccidentalOutageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@Component
public class AccidentalOutageServiceClient {
    private final WebClient webClient;
    private final String ACCIDENTAL_OUTAGE_URI;
    private static final Logger log = LoggerFactory.getLogger(AccidentalOutageServiceClient.class);

    public AccidentalOutageServiceClient(WebClient webClient, @Value("${accidental-outage.incidents-uri}") String outageIncidentsUri) {
        this.webClient = webClient;
        this.ACCIDENTAL_OUTAGE_URI = outageIncidentsUri;
    }

    private List<AccidentalOutageDto> getAllAccidentalOutagesByLocation(String uri) {
        return webClient
                .get()
                .uri(uri)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToFlux(AccidentalOutageDto.class).collectList();
                    } else {
                        // fallback: empty list if API returns error
                        log.warn("API returned {} - returning empty list", response.statusCode());
                        return Mono.just(Collections.emptyList());
                    }
                })
                .onErrorResume(e -> {
                    log.error("Error fetching outages: {}", e.getMessage());
                    return Mono.just(Collections.emptyList());
                })
                .block();
    }

    public List<AccidentalOutageDto> getAllAccidentalOutages() {
        return Stream.of(
                getAllAccidentalOutagesByLocation(ACCIDENTAL_OUTAGE_URI + "MN/0"),
                getAllAccidentalOutagesByLocation(ACCIDENTAL_OUTAGE_URI + "TS/0"),
                getAllAccidentalOutagesByLocation(ACCIDENTAL_OUTAGE_URI +"TN/0")
        ).flatMap(List::stream).collect(Collectors.toList());
    }

}