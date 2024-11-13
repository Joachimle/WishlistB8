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
        return null;
    }

    @Override
    public Boolean readIfUsernameExists(String username) {
        return null;
    }

    @Override
    public UserProfile readUserProfileByUsername(String username) {
        return null;
    }

    @Override
    public UserProfile readUserProfileByUserID(int userID) {
        return null;
    }

    @Override
    public UserProfile updateUsername(int userID, String newUsername) {
        return null;
    }

    @Override
    public UserProfile updatePassword(int userID, String newPassword) {
        return null;
    }

    @Override
    public boolean deleteUserProfile(int userID) {
        return false;
    }
}
