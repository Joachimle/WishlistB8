package beight.wishlist.repository;
import beight.wishlist.model.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("USERPROFILE_REPOSITORY_STUB")
public class UserProfileRepositoryStub implements UserProfileRepository {

    //hardcode til brug af test
    private List<UserProfile> users = new ArrayList<>(List.of(new UserProfile("test","test")));

    @Override
    public void createUserProfile(String username, String password) {
        users.add(new UserProfile(username, password));
    }

    @Override
    public UserProfile readUserProfile(String username) {
        for (UserProfile userProfile : users) {
            if (userProfile.getUsername().equals(username)) {
                return userProfile;
            }
        }
        return null;
    }

    @Override
    public UserProfile readUserProfileByPassword(String password) {
        for (UserProfile userProfile : users) {
            if (userProfile.getPassword().equals(password)) {
                return userProfile;
            }
        }
        return null;
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword) {
        UserProfile userProfile = readUserProfileByPassword(oldPassword);
        userProfile.setPassword(newPassword);
        //readUserProfileByPassword(oldPassword).setPassword(newPassword);
    }

    @Override
    public void deleteUserProfile(String username) {
        users.forEach(System.out::println);
        UserProfile userProfile = readUserProfile(username);
        if (userProfile == null) {
            System.out.println("User not found");
        }
        users.remove(userProfile);
        users.forEach(System.out::println);
    }

    @Override
    public void updateUsername(String oldUsername, String newUsername) {
        readUserProfile(oldUsername).setUsername(newUsername);
    }

}
