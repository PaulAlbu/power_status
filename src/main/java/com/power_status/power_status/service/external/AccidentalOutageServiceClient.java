package com.power_status.power_status.service.external;

import com.power_status.power_status.dto.IntrerupereAccidentala;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Component
public class AccidentalOutageServiceClient {
    private final WebClient webClient;

    public AccidentalOutageServiceClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://outages.distributie-energie.ro/api/incidents/")
                .build();
    }

    private List<IntrerupereAccidentala> getPowerOut(String uri) {
        return webClient
                .get()
                .uri(uri)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToFlux(IntrerupereAccidentala.class).collectList();
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

    public List<IntrerupereAccidentala> getAllPowerOut() {
        return Stream.of(
                getPowerOut("MN/0"),
                getPowerOut("TS/0"),
                getPowerOut("TN/0")
        ).flatMap(List::stream).collect(Collectors.toList());
    }

}