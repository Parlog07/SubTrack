package util;

public class ValidationUtil {

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    public static boolean isValidId(String id) {
        return id != null && !id.trim().isEmpty();
    }

    public static boolean isValidCommitmentDuration(int months) {
        return months > 0;
    }
}