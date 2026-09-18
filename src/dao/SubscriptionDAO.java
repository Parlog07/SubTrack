package dao;

import model.Subscription;
import model.SubscriptionStatus;
import model.SubscriptionWithCommitment;
import model.SubscriptionWithoutCommitment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubscriptionDAO {

    private List<Subscription> subscriptions = new ArrayList<>();


    // CREATE
    public void create(Subscription subscription) {
        subscriptions.add(subscription);
    }


    public Optional<Subscription> findById(String id) {

        return subscriptions.stream()
                .filter(subscription -> subscription.getId().equals(id))
                .findFirst();
    }


    public List<Subscription> findAll() {
        return new ArrayList<>(subscriptions);
    }


    public boolean update(Subscription updatedSubscription) {

        Optional<Subscription> result =
                findById(updatedSubscription.getId());

        if (result.isPresent()) {

            Subscription subscription = result.get();

            subscription.setServiceName(
                    updatedSubscription.getServiceName()
            );

            subscription.setMonthlyAmount(
                    updatedSubscription.getMonthlyAmount()
            );

            subscription.setStartDate(
                    updatedSubscription.getStartDate()
            );

            subscription.setEndDate(
                    updatedSubscription.getEndDate()
            );

            subscription.setStatus(
                    updatedSubscription.getStatus()
            );

            return true;
        }

        return false;
    }


    public boolean delete(String id) {

        return subscriptions.removeIf(
                subscription -> subscription.getId().equals(id)
        );
    }


    public List<Subscription> findActiveSubscriptions() {

        return subscriptions.stream()
                .filter(subscription ->
                        subscription.getStatus()
                                == SubscriptionStatus.ACTIVE
                )
                .collect(Collectors.toList());
    }

    public List<Subscription> findByType(String type) {

        return subscriptions.stream()
                .filter(subscription -> {

                    if (type.equalsIgnoreCase("WITH_COMMITMENT")) {

                        return subscription
                                instanceof SubscriptionWithCommitment;
                    }

                    if (type.equalsIgnoreCase("WITHOUT_COMMITMENT")) {

                        return subscription
                                instanceof SubscriptionWithoutCommitment;
                    }

                    return false;
                })
                .collect(Collectors.toList());
    }
}