package beight.wishlist.repository;

import beight.wishlist.model.Wish;
import beight.wishlist.model.WishList;

public interface WishListRepository {

    WishList createWish(String wishName, double wishPrice, String wishLink, String wishDescription);

}
