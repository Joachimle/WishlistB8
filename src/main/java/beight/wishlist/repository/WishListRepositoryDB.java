package beight.wishlist.repository;

import beight.wishlist.model.Reservation;
import beight.wishlist.model.Wish;
import beight.wishlist.model.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("WISHLIST_REPOSITORY_DB")
public class WishListRepositoryDB implements WishListRepository {

    private final JdbcTemplate database;

    @Autowired
    public WishListRepositoryDB(JdbcTemplate database) {
        this.database = database;
    }

    @Override
    public boolean createWishList(int userID, String title, String description) {
        return false;
    }

    @Override
    public boolean createWish(int wishListID, String title, int numberOfUnits, int pricePerUnit, String link, String description) {
        return false;
    }

    @Override
    public List<WishList> readWishLists(int userID) {
        return List.of();
    }

    @Override
    public WishList readWishList(int wishListID) {
        return null;
    }

    @Override
    public List<Wish> readWishes(int wishListID) {
        return List.of();
    }

    @Override
    public Wish readWish(int wishID) {
        return null;
    }

    @Override
    public List<Reservation> readReservations(int userID, int wishListID) {
        return List.of();
    }

    @Override
    public Reservation readReservation(int wishID, int userID) {
        return null;
    }

    @Override
    public boolean updateWishList(int wishListID, String title, String description) {
        return false;
    }

    @Override
    public boolean updateWish(int wishID, String title, int numberOfUnits, int pricePerUnit, String link, String description) {
        return false;
    }

    @Override
    public boolean createOrUpdateReservation(int wishID, int userID, int numberOfUnits) {
        return false;
    }

    @Override
    public boolean deleteWishList(int wishListID) {
        return false;
    }

    @Override
    public boolean deleteWish(int wishID) {
        return false;
    }
}
