package beight.wishlist.model;

import java.util.Objects;

public record WishList(int wishListID, String title, String description) {
    public WishList {
        Objects.requireNonNull(title);
        Objects.requireNonNull(description);
    }
}
