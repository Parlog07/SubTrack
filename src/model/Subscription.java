package model;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Subscription {

    private String id;
    private String serviceName;
    private double monthlyAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private SubscriptionStatus status;

    public Subscription(String serviceName,
                        double monthlyAmount,
                        LocalDate startDate,
                        LocalDate endDate,
                        SubscriptionStatus status) {

        this.id = UUID.randomUUID().toString();
        this.serviceName = serviceName;
        this.monthlyAmount = monthlyAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(double monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }
}