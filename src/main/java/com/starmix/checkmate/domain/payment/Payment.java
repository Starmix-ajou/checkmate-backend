package com.starmix.checkmate.domain.payment;

import io.portone.sdk.server.payment.PaidPayment;
import io.portone.sdk.server.payment.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
@Getter
public class Payment {
    private String paymentId;
    private PaymentStatus status;
    private String orderName;
    private PaymentAmountInfo totalAmount;
    private String currency;
    private String payMethod;
    private LocalDateTime timestamp;
    private String projectId;

    public static Payment init(
            String paymentId,
            PaymentStatus status,
            PaidPayment paidPayment
    ) {
        return Payment.builder()
                .paymentId(paymentId)
                .status(status)
                .orderName(paidPayment.getOrderName())
                .totalAmount(PaymentAmountInfo.init(paidPayment.getAmount()))
                .currency(paidPayment.getCurrency().toString())
                .payMethod(extractProvider(Objects.requireNonNull(paidPayment.getMethod())))
                .timestamp(LocalDateTime.now())
                .projectId(extractProjectId(paidPayment.getCustomData()))
                .build();
    }

    private static String extractProvider(PaymentMethod paymentMethod) {
        if(paymentMethod == null) { return ""; }
        String paymentMethodString = paymentMethod.toString();

        String key = "provider=";
        int start = paymentMethodString.indexOf(key);
        if (start == -1) return null;

        start += key.length();
        int end = paymentMethodString.indexOf(',', start);
        if (end == -1) end = paymentMethodString.length();

        return paymentMethodString.substring(start, end);
    }

    private static String extractProjectId(String customData) {
        if(customData == null) { return null; }

        Pattern pattern = Pattern.compile("\"projectId\"\\s*:\\s*\"(.*?)\"");
        Matcher matcher = pattern.matcher(customData);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
