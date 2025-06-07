package com.starmix.checkmate.infrastructure.config;

import io.portone.sdk.server.payment.PaymentClient;
import io.portone.sdk.server.webhook.WebhookVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortOneConfig {

    @Value("${portone.secret.secret}")
    private String secret;

    @Value("${portone.secret.store.id}")
    private String storeId;

    @Bean
    public PaymentClient paymentClient() {
        return new PaymentClient(secret, "https://api.portone.io", storeId);
    }

    @Bean
    public WebhookVerifier webhookVerifier() {
        return new WebhookVerifier(secret);
    }
}