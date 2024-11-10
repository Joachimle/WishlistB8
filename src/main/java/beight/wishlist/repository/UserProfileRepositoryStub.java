package beight.wishlist.repository;
import beight.wishlist.model.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("USERPROFILE_REPOSITORY_STUB")
public class UserProfileRepositoryStub implements UserProfileRepository {

    private final List<UserProfile> userProfiles;

    public UserProfileRepositoryStub() {
        userProfiles = new ArrayList<>();
        createUserProfile("testU", "testP");
    }

    @Override
    public UserProfile createUserProfile(String username, String password) {
        UserProfile newUserProfile = new UserProfile(userProfiles.size(), username, password);
        userProfiles.add(newUserProfile);
        return newUserProfile;
    }

    @Override
    public UserProfile readUserProfileByUsername(String username) {
        for (UserProfile userProfile : userProfiles) {
            if (userProfile.username().equals(username)) return userProfile;
        }
        return null;
    }

    @Override
    public UserProfile readUserProfileByUserID(int userID) {
        for (UserProfile userProfile : userProfiles) {
            if (userProfile.userID() == userID) return userProfile;
        }
        return null;
    }

    @Override
    public UserProfile updatePassword(int userID, String newPassword) {
        UserProfile oldUserProfile = readUserProfileByUserID(userID);
        if (oldUserProfile == null) return null;
        userProfiles.remove(oldUserProfile);
        UserProfile newUserProfile = new UserProfile(userID, oldUserProfile.username(), newPassword);
        userProfiles.add(newUserProfile);
        return newUserProfile;
    }

    @Override
    public boolean deleteUserProfile(int userID) {
        return userProfiles.removeIf(user -> user.userID() == userID);
    }

    @Override
    public UserProfile updateUsername(int userID, String newUsername) {
        UserProfile oldUserProfile = readUserProfileByUserID(userID);
        if (oldUserProfile == null) return null;
        userProfiles.remove(oldUserProfile);
        UserProfile newUserProfile = new UserProfile(userID, newUsername, oldUserProfile.password());
        userProfiles.add(newUserProfile);
        return newUserProfile;
    }

    @Override
    public Boolean readIfUsernameExists(String username) {
        for (UserProfile userProfile : userProfiles) {
            if (userProfile.username().equals(username)) return true;
        }
        return false;
    }
}
