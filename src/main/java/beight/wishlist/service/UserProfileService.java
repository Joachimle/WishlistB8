package beight.wishlist.service;

import beight.wishlist.model.UserProfile;
import beight.wishlist.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
        // Måske skal denne constructor laves om som i Ians pdf "Interface DI IoC" side 17
    }

    public String createUserProfile(String username, String password) {
        if (username == null || username.isBlank()) {
            return "Brugernavn var tomt.";
        } else if (userProfileRepository.readUserProfile(username) == null) {
            userProfileRepository.createUserProfile(username, password);
            return "Bruger oprettet.";
        } else {
            return "Brugernavn findes allerede.";
        }
    }

    public boolean login(String username, String password) {
        UserProfile userProfile = userProfileRepository.readUserProfile(username);
        if (userProfile != null) {
            return userProfile.getPassword().equals(password);
        }
        return false;
    }

    public String updateUsername(String oldUsername, String newUsername) {
        if (newUsername == null || newUsername.isBlank()) {
            return "Brugernavn var tomt.";
        } else if (oldUsername.equals(newUsername)) {
            return "Nyt brugernavn var magen til gamle brugernavn.";
        } else if (userProfileRepository.readUserProfile(newUsername) == null) {
            userProfileRepository.updateUsername(oldUsername, newUsername);
            return "Brugernavn ændret.";
        } else {
            return "Brugernavn findes allerede.";
        }
    }

    public void updatePassword(String oldPassword, String newPassword) {
        userProfileRepository.updatePassword(oldPassword,newPassword);
    }
}
