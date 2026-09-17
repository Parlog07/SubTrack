package model;

import java.time.LocalDate;

public class SubscriptionWithoutCommitment extends Subscription {

    public SubscriptionWithoutCommitment(
            String serviceName,
            double monthlyAmount,
            LocalDate startDate,
            LocalDate endDate,
            SubscriptionStatus status) {

        super(
                serviceName,
                monthlyAmount,
                startDate,
                endDate,
                status
        );
    }
}