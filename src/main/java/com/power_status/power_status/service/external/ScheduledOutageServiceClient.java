package com.power_status.power_status.service.external;

import com.power_status.power_status.dto.ScheduledOutageDto;
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
public class ScheduledOutageServiceClient {
    private final WebClient webClient;
    private final String SCHEDULED_URI;
    private final String TODAY_URI;
    private final String NEXT_FIFTEEN_DAYS_URI;
    private static final Logger log = LoggerFactory.getLogger(ScheduledOutageServiceClient.class);

    public ScheduledOutageServiceClient(WebClient webClient,
                                        @Value("${scheduled-outage.incidents-uri}") String scheduledOutageUri,
                                        @Value("${scheduled-outage.today}")String today,
                                        @Value("${scheduled-outage.next-fifteen-days}")String nextFifteenDays) {
        this.webClient = webClient;
        SCHEDULED_URI = scheduledOutageUri;
        TODAY_URI = today;
        NEXT_FIFTEEN_DAYS_URI = nextFifteenDays;
    }

    private List<ScheduledOutageDto> getAllScheduledOutagesByLocation(String uri) {
        return webClient
                .get()
                .uri(uri)
                .exchangeToMono(
                        response -> {
                            if (response.statusCode().is2xxSuccessful()) {
                                return response.bodyToFlux(ScheduledOutageDto.class).collectList();
                            } else {
                                log.warn("API returned {} - returning empty list", response.statusCode());
                                return Mono.just(Collections.emptyList());
                            }
                        }
                )
                .onErrorResume( e-> {
                    log.error("Error fetching outages: {}", e.getMessage());
                    return Mono.just(Collections.emptyList());
                }).block();
    }

    public List<ScheduledOutageDto> getTodayAllScheduledOutages() {
        return Stream.of(
                getAllScheduledOutagesByLocation(SCHEDULED_URI + "MN/" + TODAY_URI),
                getAllScheduledOutagesByLocation(SCHEDULED_URI + "TS/" + TODAY_URI),
                getAllScheduledOutagesByLocation(SCHEDULED_URI + "TN/" + TODAY_URI)
        ).flatMap(List::stream).collect(Collectors.toList());
    }

    public List<ScheduledOutageDto> getNextFifteenAllScheduledOutages() {
        return Stream.of(
                getAllScheduledOutagesByLocation(SCHEDULED_URI + "MN/" + NEXT_FIFTEEN_DAYS_URI),
                getAllScheduledOutagesByLocation(SCHEDULED_URI + "TS/" + NEXT_FIFTEEN_DAYS_URI),
                getAllScheduledOutagesByLocation(SCHEDULED_URI + "TN/" + NEXT_FIFTEEN_DAYS_URI)
        ).flatMap(List::stream).collect(Collectors.toList());
    }
}
