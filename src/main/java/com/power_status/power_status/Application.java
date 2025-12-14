package com.power_status.power_status;

import com.power_status.power_status.dto.IntrerupereAccidentala;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@SpringBootApplication
public class Application {

    public Application() {
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        WebClient.Builder builder = WebClient.builder().baseUrl("https://outages.distributie-energie.ro/api/incidents");

        List<IntrerupereAccidentala> intrerupereAccidentala = builder.build()
                .get()
                .uri("/TS/0")
                .retrieve()
                .bodyToFlux(IntrerupereAccidentala.class)
                .collectList()
                .block();

        intrerupereAccidentala.forEach(System.out::println);
        System.out.println(intrerupereAccidentala.get(0).getId());

//		private String location;

//		private List<IntrerupereAccidentala> getIntreruperi(String location) {
//			return builder.build()
//					.get()
//					.uri(location)
//					.exchangeToMono(response -> {
//						if (response.statusCode().is2xxSuccessful()) {
//							return response.bodyToFlux(IntrerupereAccidentala.class).collectList();
//						} else {
//							// fallback: empty list if API returns error
//							log.warn("API returned {} - returning empty list", response.statusCode());
//							return Mono.just(Collections.emptyList());
//						}
//					})
//					.onErrorResume(e -> {
//						log.error("Error fetching outages: {}", e.getMessage());
//						return Mono.just(Collections.emptyList());
//					})
//					.block();
//
    }

}
