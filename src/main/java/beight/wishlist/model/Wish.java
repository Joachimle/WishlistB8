package beight.wishlist.model;

import java.util.Objects;

public record Wish(int wishID, String title, int numberOfUnits, int pricePerUnit, String link, String description) {
    public Wish {
        Objects.requireNonNull(title);
        Objects.requireNonNull(link);
        Objects.requireNonNull(description);
    }
}