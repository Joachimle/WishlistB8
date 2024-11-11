package beight.wishlist.model;

import java.util.ArrayList;
import java.util.List;

public class WishList {
    List<Wish> wishes;

    public WishList() {
        this.wishes = new ArrayList<>();
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public void add(Wish newWish) {
        wishes.add(newWish);
    }
}
