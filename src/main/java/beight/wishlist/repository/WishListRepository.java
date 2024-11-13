package beight.wishlist.repository;

import beight.wishlist.model.Reservation;
import beight.wishlist.model.Wish;
import beight.wishlist.model.WishList;

import java.util.List;

public interface WishListRepository {

    boolean createWishList(int userID, String title, String description);

    boolean createWish(int wishListID, String title, int numberOfUnits, int pricePerUnit, String link, String description);

    List<WishList> readWishLists(int userID);

    WishList readWishList(int wishListID);

    List<Wish> readWishes(int wishListID);

    Wish readWish(int wishID);

    List<Reservation> readReservations(int userID, int wishListID);

    Reservation readReservation(int wishID, int userID);

    boolean updateWishList(int wishListID, String title, String description);

    boolean updateWish(int wishID, String title, int numberOfUnits, int pricePerUnit, String link, String description);

    boolean createOrUpdateReservation(int wishID, int userID, int numberOfUnits);

    boolean deleteWishList(int wishListID);

    boolean deleteWish(int wishID);
}
