package service;

import dao.SubscriptionDAO;
import model.Subscription;
import model.SubscriptionStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SubscriptionService {

    private SubscriptionDAO subscriptionDAO;

    public SubscriptionService(SubscriptionDAO subscriptionDAO) {
        this.subscriptionDAO = subscriptionDAO;
    }

    public void createSubscription(Subscription subscription) {
        subscriptionDAO.create(subscription);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionDAO.findAll();
    }

    public Optional<Subscription> getSubscriptionById(String id) {
        return subscriptionDAO.findById(id);
    }

    public boolean updateSubscription(Subscription subscription) {
        return subscriptionDAO.update(subscription);
    }

    public boolean deleteSubscription(String id) {
        return subscriptionDAO.delete(id);
    }

    public boolean cancelSubscription(String id) {

        Optional<Subscription> result = subscriptionDAO.findById(id);

        if (result.isPresent()) {
            Subscription subscription = result.get();
            subscription.setStatus(SubscriptionStatus.CANCELLED);
            return subscriptionDAO.update(subscription);
        }

        return false;
    }

    public List<Subscription> getActiveSubscriptions() {
        return subscriptionDAO.findActiveSubscriptions();
    }

    public List<Subscription> getSubscriptionsByType(String type) {
        return subscriptionDAO.findByType(type);
    }

    public LocalDate generateNextDueDate(Subscription subscription) {
        return subscription.getStartDate().plusMonths(1);
    }
}