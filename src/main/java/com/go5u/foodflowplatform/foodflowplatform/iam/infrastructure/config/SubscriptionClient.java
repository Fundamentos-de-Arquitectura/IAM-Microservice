package com.go5u.foodflowplatform.foodflowplatform.iam.infrastructure.config;

import com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.dto.SubscriptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class SubscriptionClient {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionClient.class);
    private static final String SUBSCRIPTION_SERVICE = "http://subscription-service";

    private final RestClient restClient;

    public SubscriptionClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl(SUBSCRIPTION_SERVICE)
                .build();
    }

    /**
     * Crea una suscripción por defecto para un nuevo usuario
     * @param userId ID del usuario
     * @return Optional con la suscripción creada o vacío si falla
     */
    public Optional<SubscriptionResponse> createDefaultSubscription(Long userId) {
        try {
            logger.info("Creating default subscription for user: {}", userId);

            var request = new CreateSubscriptionRequest(userId);

            SubscriptionResponse response = restClient.post()
                    .uri("/api/v1/subscriptions/create-default")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(SubscriptionResponse.class);

            logger.info("Subscription created successfully: {}", response);
            return Optional.ofNullable(response);

        } catch (Exception e) {
            logger.error("Error creating subscription for user {}: {}", userId, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtiene una suscripción por ID
     * @param subscriptionId ID de la suscripción
     * @return Optional con la suscripción o vacío si no existe
     */
    public Optional<SubscriptionResponse> getSubscriptionById(Long subscriptionId) {
        try {
            logger.info("Fetching subscription by ID: {}", subscriptionId);

            SubscriptionResponse response = restClient.get()
                    .uri("/api/v1/subscriptions/by-id/{subscriptionId}", subscriptionId)
                    .retrieve()
                    .body(SubscriptionResponse.class);

            return Optional.ofNullable(response);

        } catch (Exception e) {
            logger.error("Error fetching subscription {}: {}", subscriptionId, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * DTO para crear suscripción por defecto
     */
    public record CreateSubscriptionRequest(Long userId) {}
}
