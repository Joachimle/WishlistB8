package beight.wishlist.repository;

import beight.wishlist.model.UserProfile;

public interface UserProfileRepository {

    UserProfile createUserProfile(String username, String password); // Returner null hvis det ikke lykkes at oprette.

    Boolean readIfUsernameExists(String username); // Returner null hvis der er sket en fejl.

    UserProfile readUserProfileByUsername(String username); // Returner null hvis der ikke var en brugerprofil med det brugernavn.

    UserProfile readUserProfileByUserID(int userID); // Returner null hvis der ikke var en brugerprofil med det brugerID.

    UserProfile updateUsername(int userID, String newUsername); // Returner null hvis det ikke lykkes at ændre.

    UserProfile updatePassword(int userID, String newPassword); // Returner null hvis det ikke lykkes at ændre.

    boolean deleteUserProfile(int userID);

}
