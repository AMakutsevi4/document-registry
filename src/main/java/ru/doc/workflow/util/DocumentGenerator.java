package ru.doc.workflow.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Slf4j
public class DocumentGenerator implements CommandLineRunner {

    @Value("${generator.n:10}")
    private int n;

    @Value("${generator.api-url}")
    private String apiUrl;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DocumentGenerator.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        RestTemplate restTemplate = new RestTemplate();
        log.info("Запуск генерации {} документов через API: {}", n, apiUrl);

        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= n; i++) {
            try {
                Map<String, String> request = Map.of(
                        "author", "Author demo",
                        "title", "Document " + i,
                        "initiator", "Generator-Tool"
                );
                restTemplate.postForEntity(apiUrl, request, String.class);

                if (i % 10 == 0) {
                    log.info("Прогресс: {}/{} создано", i, n);
                }
            } catch (Exception e) {
                log.error("Ошибка на документе {}: {}", i, e.getMessage());
            }
        }
        log.info("Готово! Затрачено времени: {} мс", System.currentTimeMillis() - startTime);
    }
}
