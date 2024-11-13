package beight.wishlist.model;

import java.util.Objects;

public record Reservation(Wish wish, int yourReservations, int otherReservations) {
    public Reservation {
        Objects.requireNonNull(wish);
    }
}
