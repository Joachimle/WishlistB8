package beight.wishlist.repository;

import beight.wishlist.model.Reservation;
import beight.wishlist.model.Wish;
import beight.wishlist.model.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
        String sql = "INSERT INTO wishList (userID, title, description) VALUES (?, ?, ?)";
        int affectedRows = database.update(sql, userID, title, description);
        return affectedRows == 1;
    }

    @Override
    public boolean createWish(int wishListID, String title, int numberOfUnits, int pricePerUnit, String link, String description) {
        String sql = "INSERT INTO wish (wishListID, title, number, price, link, description) VALUES (?, ?, ?, ?, ?, ?)";
        int affectedRows = database.update(sql, wishListID, title, numberOfUnits, pricePerUnit, link, description);
        return affectedRows == 1;
    }

    @Override
    public List<WishList> readWishLists(int userID) {
        String query ="SELECT wishList FROM userProfile WHERE userID = ?;";
        RowMapper rowMapper = new BeanPropertyRowMapper(WishList.class);
        return database.query(query,rowMapper);
    }

    @Override
    public WishList readWishList(int wishListID) {
        String query ="SELECT * FROM wishList WHERE wishListID = ?;";
        RowMapper<WishList> rowMapper = new BeanPropertyRowMapper<>(WishList.class);
        return database.queryForObject(query,rowMapper,wishListID);
    }

    @Override
    public List<Wish> readWishes(int wishListID) {
        String query ="SELECT wish FROM wishList WHERE wishListID = ?;";
        RowMapper rowMapper = new BeanPropertyRowMapper(WishList.class);
        return database.query(query,rowMapper);
    }

    @Override
    public Wish readWish(int wishID) {
        String query = "SELECT * FROM wish WHERE wishID = ?;";
        RowMapper<Wish> rowMapper = new BeanPropertyRowMapper<>(Wish.class);
        return database.queryForObject(query,rowMapper,wishID);
    }

    @Override
    public List<Reservation> readReservations(int userID, int wishListID) {
        return List.of();
    }

    @Override
    public Reservation readReservation(int wishID, int userID) {
        String own = "SELECT number FROM reservation WHERE wishID = ? AND userID = ?";
        Integer yourReservations = database.queryForObject(own, Integer.class, userID, wishID);
        String others = "SELECT sum(number) FROM reservation WHERE wishID = ? AND NOT userID = ?";
        Integer otherReservations = database.queryForObject(others, Integer.class, userID, wishID);
        if (yourReservations == null || otherReservations == null) return null;
        return new Reservation(readWish(wishID), yourReservations, otherReservations);
    }

    @Override
    public boolean updateWishList(int wishListID, String title, String description) {
        String query= "UPDATE wishList" +
                "SET title = ?," +
                "SET description = ?;";
        database.update(query,title,description);
        return true;
    }

    @Override
    public boolean updateWish(int wishID, String title, int numberOfUnits, int pricePerUnit, String link, String description) {
        String query="UPDATE wish" +
                "SET title = ?," +
                "SET numberOfUnits = ?," +
                "SET pricePerUnit = ?," +
                "SET link = ?," +
                "SET description = ?;";
        database.update(query,title,numberOfUnits,pricePerUnit,link,description)
        return true;
    }

    @Override
    public boolean createOrUpdateReservation(int wishID, int userID, int numberOfUnits) {
        return false;
    }

    @Override
    public boolean deleteWishList(int wishListID) {
        String query="DELETE FROM wishList WHERE wishListID = ?";
        database.update(query,wishListID);
        return true;
    }

    @Override
    public boolean deleteWish(int wishID) {
        String query ="DELETE FROM wish WHERE wishID = ?";
        database.update(query,wishID);
        return true;
    }
}
