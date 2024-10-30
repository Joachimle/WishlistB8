package beight.wishlist.service;

import beight.wishlist.model.UserProfile;
import beight.wishlist.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public void createUserProfile(UserProfile userProfile) {
        userProfileRepository.createUser(userProfile.getUsername(), userProfile.getPassword());
    }
}
