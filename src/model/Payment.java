package model;

import java.time.LocalDate;
import java.util.UUID;

public class Payment {

    private String paymentId;
    private String subscriptionId;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private String paymentType;
    private PaymentStatus status;

    public Payment(
            String subscriptionId,
            LocalDate dueDate,
            LocalDate paymentDate,
            String paymentType,
            PaymentStatus status) {

        this.paymentId = UUID.randomUUID().toString();
        this.subscriptionId = subscriptionId;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.paymentType = paymentType;
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}