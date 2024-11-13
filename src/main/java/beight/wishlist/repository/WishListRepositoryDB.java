package beight.wishlist.repository;

import beight.wishlist.model.Reservation;
import beight.wishlist.model.Wish;
import beight.wishlist.model.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("WISHLIST_REPOSITORY_DB")
public class WishListRepositoryDB implements WishListRepository {

    private final JdbcTemplate database;

    @Autowired
    public WishListRepositoryDB(JdbcTemplate database) {
        this.database = database;
    }

    @Override
    public boolean createWishList(int userID, String title, String description) {
        String sql = "INSERT INTO wishList (userID, title, description) VALUES (?, ?, ?);";
        int rowsAffected = database.update(sql, userID, title, description);
        return rowsAffected > 0;
    }

    @Override
    public boolean createWish(int wishListID, String title, int numberOfUnits, int pricePerUnit, String link, String description) {
        String sql = "INSERT INTO wish (wishListID, title, number, price, link, description) VALUES (?, ?, ?, ?, ?, ?);";
        int rowsAffected = database.update(sql, wishListID, title, numberOfUnits, pricePerUnit, link, description);
        return rowsAffected > 0;
    }

    @Override
    public List<WishList> readWishLists(int userID) {
        String query ="SELECT * FROM wishList WHERE userID = ?;";
        return database.query(query, WishList.ROW_MAPPER, userID);
    }

    @Override
    public WishList readWishList(int wishListID) {
        String query ="SELECT * FROM wishList WHERE wishListID = ?;";
        return database.queryForObject(query, WishList.ROW_MAPPER,wishListID);
    }

    @Override
    public List<Wish> readWishes(int wishListID) {
        String query ="SELECT * FROM wish WHERE wishListID = ?;";
        return database.query(query, Wish.ROW_MAPPER, wishListID);
    }

    @Override
    public Wish readWish(int wishID) {
        String query = "SELECT * FROM wish WHERE wishID = ?;";
        return database.queryForObject(query, Wish.ROW_MAPPER,wishID);
    }

    @Override
    public List<Reservation> readReservations(int userID, int wishListID) {
        String wish = "SELECT wishID FROM wish WHERE wishListID = ?;";
        List<Integer> wishes = database.queryForList(wish, Integer.class, wishListID);
        List<Reservation> reservations = new ArrayList<>();
        for (Integer wishID : wishes) {
            Reservation reservation = readReservation(wishID, userID);
            if (reservation != null) reservations.add(reservation);
        }
        return reservations;
    }

    @Override
    public Reservation readReservation(int wishID, int userID) {
        String sql = "SELECT " +
                "SUM(CASE WHEN userID = ? THEN number ELSE 0 END) AS own, " +
                "SUM(CASE WHEN userID != ? THEN number ELSE 0 END) AS rest " +
                "FROM reservation WHERE wishID = ?;";
        Map<String, Integer> values = database.queryForObject(sql, new Object[] {userID, userID, wishID},
                (rs, rownum) -> Map.of("own", rs.getInt("own"), "rest", rs.getInt("rest")));
        if (values == null || values.size() != 2) return null;
        Wish wish = readWish(wishID);
        if (wish == null) return null;
        return new Reservation(wish, values.get("own"), values.get("rest"));
    }

    @Override
    public boolean updateWishList(int wishListID, String title, String description) {
        String query= "UPDATE wishList SET title = ?, description = ? WHERE wishListID = ?;";
        int rowsAffected = database.update(query,title, description, wishListID);
        return rowsAffected > 0;
    }

    @Override
    public boolean updateWish(int wishID, String title, int numberOfUnits, int pricePerUnit, String link, String description) {
        String query="UPDATE wish SET title = ?, number = ?, price = ?, link = ?, description = ? WHERE wishID = ?;";
        int rowsAffected = database.update(query,title, numberOfUnits, pricePerUnit, link, description, wishID);
        return rowsAffected > 0;
    }

    @Override
    public boolean createOrUpdateReservation(int wishID, int userID, int numberOfUnits) {
        String update = "UPDATE reservation SET number = ? WHERE wishID = ? AND userID = ?;";
        int rowsAffectedByUpdate = database.update(update, numberOfUnits, wishID, userID);
        if (rowsAffectedByUpdate > 0) return true;
        String create = "INSERT INTO reservation (wishID, userID, number) VALUES (?, ?, ?);";
        int rowsAffectedByCreate = database.update(create, wishID, userID, numberOfUnits);
        return rowsAffectedByCreate > 0;
    }

    @Override
    public boolean deleteWishList(int wishListID) {
        String query="DELETE FROM wishList WHERE wishListID = ?";
        int rowsAffected = database.update(query,wishListID);
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteWish(int wishID) {
        String query ="DELETE FROM wish WHERE wishID = ?";
        int rowsAffected = database.update(query,wishID);
        return rowsAffected > 0;
    }
}
