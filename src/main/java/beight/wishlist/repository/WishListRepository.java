package beight.wishlist.repository;

import beight.wishlist.model.Wish;
import beight.wishlist.model.WishList;

import java.util.List;

public interface WishListRepository {

    boolean createWishList(int userID, String title, String description);

    boolean createWish(int wishListID, String title, int price, String link, String description);

    List<WishList> readWishLists(int userID);

    WishList readWishList(int wishListID);

    List<Wish> readWishes(int wishListID);

    Wish readWish(int wishID);

    int readWishListIDByWishID(int wishID);

    int readUserIDByWishID(int wishID);

    boolean updateWishList(int wishListID, String title, String description);

    boolean updateWish(int wishID, String title, int price, String link, String description);
}
