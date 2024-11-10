package beight.wishlist.model;

import java.util.Objects;

public record UserProfile(int userID, String username, String password) {
    public UserProfile {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
    }
}