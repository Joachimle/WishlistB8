package beight.wishlist.repository;

import beight.wishlist.model.UserProfile;

public interface UserProfileRepository {

    void createUserProfile(String username, String password);

    UserProfile readUserProfile(String username);

    void updateUsername(String oldUsername, String newUsername);


//    void deleteUser();
}
