package beight.wishlist.model;

import java.util.Objects;

public record Wish(int wishID, String title, int price, String link, String description) {
    public Wish {
        Objects.requireNonNull(title);
        Objects.requireNonNull(link);
        Objects.requireNonNull(description);
    }
}