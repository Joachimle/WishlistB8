package beight.wishlist.repository;

import beight.wishlist.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("USERPROFILE_REPOSITORY_DB")
public class UserProfileRepositoryDB implements UserProfileRepository {

    private final JdbcTemplate database;

    @Autowired
    public UserProfileRepositoryDB(JdbcTemplate database) {
        this.database = database;
    }

    @Override
    public UserProfile createUserProfile(String username, String password) {
        String query = "INSERT INTO userProfile (username, password) VALUES (?, ?)";
        int rowsAffected = database.update(query, username, password);
        if (rowsAffected == 0) return null;
        return readUserProfileByUsername(username);
    }

    @Override
    public Boolean readIfUsernameExists(String username) {
        String query = "SELECT count(username) FROM userProfile WHERE username = ?";
        Integer count = database.queryForObject(query, Integer.class, username);
        if (count == null) return null;
        return count > 0;
    }

    @Override
    public UserProfile readUserProfileByUsername(String username) {
        String query = "SELECT * FROM userProfile WHERE username = ?";
        return database.queryForObject(query, new UserProfileRowMapper(), username);
    }

    @Override
    public UserProfile readUserProfileByUserID(int userID) {
        String query = "SELECT * FROM userProfile WHERE userID = ?";
        return database.queryForObject(query, new UserProfileRowMapper(), userID);
    }

    @Override
    public UserProfile updateUsername(int userID, String newUsername) {
        String query = "UPDATE userProfile SET username = ? WHERE userID = ?";
        int rowsAffected = database.update(query, newUsername, userID);
        return rowsAffected > 0 ? readUserProfileByUserID(userID) : null;
    }

    @Override
    public UserProfile updatePassword(int userID, String newPassword) {
        String query = "UPDATE userProfile SET password = ? WHERE userID = ?";
        int rowsAffected = database.update(query, newPassword, userID);
        return rowsAffected > 0 ? readUserProfileByUserID(userID) : null;
    }

    @Override
    public boolean deleteUserProfile(int userID) {
        String query = "DELETE FROM userProfile WHERE userID = ?";
        int rowsAffected = database.update(query, userID);
        return rowsAffected > 0;
    }
}
