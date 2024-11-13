package beight.wishlist.repository;

import beight.wishlist.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

@Repository("USERPROFILE_REPOSITORY_DB")
public class UserProfileRepositoryDB implements UserProfileRepository {

    private final JdbcTemplate database;

    @Autowired
    public UserProfileRepositoryDB(JdbcTemplate database) {
        this.database = database;
    }

    @Override
    public UserProfile createUserProfile(String username, String password) {
        String query = "INSERT INTO user_profile (username, password) VALUES (?, ?)";
        database.update(query, username, password);
        return null; //TODO: return null?
    }

    @Override
    public Boolean readIfUsernameExists(String username) {
        String query = "SELECT * FROM userProfile WHERE username = ?";
        RowMapper<UserProfile> rowMapper = new BeanPropertyRowMapper<>(UserProfile.class);
        return database.queryForObject(query, rowMapper, username) != null;
    }

    @Override
    public UserProfile readUserProfileByUsername(String username) {
        String query = "SELECT * FROM userProfile WHERE username = ?";
        RowMapper<UserProfile> rowMapper = new BeanPropertyRowMapper<>(UserProfile.class);
        return database.queryForObject(query, rowMapper, username);
    }

    @Override
    public UserProfile readUserProfileByUserID(int userID) {
        String query = "SELECT * FROM userProfile WHERE userID = ?";
        RowMapper<UserProfile> rowMapper = new BeanPropertyRowMapper<>(UserProfile.class);
        return database.queryForObject(query, rowMapper, userID);
    }

    @Override
    public UserProfile updateUsername(int userID, String newUsername) {
        String query = "UPDATE userProfile SET username = ? WHERE userID = ?";
        database.update(query, newUsername, userID);
        return null; //TODO: return null?
    }

    @Override
    public UserProfile updatePassword(int userID, String newPassword) {
        String query = "UPDATE userProfile SET password = ? WHERE userID = ?";
        database.update(query, newPassword, userID);
        return null; //TODO: return null?
    }

    @Override
    public boolean deleteUserProfile(int userID) {
        String query = "DELETE FROM userProfile WHERE userID = ?";
        database.update(query, userID);
        return true;
    }
}
