package beight.wishlist.service;

import beight.wishlist.model.UserProfile;
import beight.wishlist.repository.UserProfileRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import static beight.wishlist.service.ServiceMessage.*;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
        // Måske skal denne constructor laves om som i Ians pdf "Interface DI IoC" side 17
    }

    private boolean isWrongFormat(String string) {
        return string.length() < 2 || string.length() > 20 || !string.matches("[a-zA-Z0-9]+");
    }

    public boolean createUserProfile(HttpSession session, String username, String password1, String password2) {
        if (session == null) return false;
        ServiceMessage message;
        if (username == null || password1 == null || password2 == null) message = UNEXPECTED_INPUT;
        else if (isWrongFormat(username)) message = USERNAME_WRONG_FORMAT;
        else if (isWrongFormat(password1)) message = PASSWORD_WRONG_FORMAT;
        else if (!password1.equals(password2)) message = PASSWORDS_NOT_MATCHING;
        else {
            Boolean alreadyExists = userProfileRepository.readIfUsernameExists(username);
            if (alreadyExists == null) message = UNEXPECTED_OUTPUT;
            else if (alreadyExists) message = USERNAME_ALREADY_TAKEN;
            else {
                UserProfile userProfile = userProfileRepository.createUserProfile(username, password1);
                if (userProfile == null) message = UNEXPECTED_OUTPUT;
                else {
                    session.setAttribute("message", USER_CREATED);
                    session.setAttribute("userProfile", userProfile);
                    return true;
                }
            }
        }
        session.setAttribute("message", message);
        session.setAttribute("userProfile", null);
        return false;
    }

    public boolean login(HttpSession session, String username, String password) {
        if (session == null) return false;
        ServiceMessage message;
        if (username == null || password == null) message = UNEXPECTED_INPUT;
        else {
            Boolean userExists = userProfileRepository.readIfUsernameExists(username);
            if (userExists == null) message = UNEXPECTED_OUTPUT;
            else if (!userExists) message = USER_NOT_FOUND;
            else {
                UserProfile userProfile = userProfileRepository.readUserProfileByUsername(username);
                if (userProfile == null) message = UNEXPECTED_OUTPUT;
                else if (!userProfile.password().equals(password)) message = PASSWORD_INCORRECT;
                else {
                    session.setAttribute("message", LOGIN_SUCCESSFUL);
                    session.setAttribute("userProfile", userProfile);
                    return true;
                }
            }
        }
        session.setAttribute("message", message);
        session.setAttribute("userProfile", null);
        return false;
    }

    public boolean updateUsername(HttpSession session, String password, String newUsername) {
        if (session == null) return false;
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        ServiceMessage message;
        if (userProfile == null || password == null || newUsername == null) message = UNEXPECTED_INPUT;
        else if (!userProfile.password().equals(password)) message = PASSWORD_INCORRECT;
        else if (isWrongFormat(newUsername)) message = USERNAME_WRONG_FORMAT;
        else if (userProfile.username().equals(newUsername)) message = USERNAME_SIMILAR;
        else {
            Boolean alreadyExists = userProfileRepository.readIfUsernameExists(newUsername);
            if (alreadyExists == null) message = UNEXPECTED_OUTPUT;
            else if (alreadyExists) message = USERNAME_ALREADY_TAKEN;
            else {
                UserProfile updatedUserProfile = userProfileRepository.updateUsername(userProfile.userID(), newUsername);
                if (updatedUserProfile == null) message = UNEXPECTED_OUTPUT;
                else {
                    session.setAttribute("message", USERNAME_UPDATED);
                    session.setAttribute("userProfile", updatedUserProfile);
                    return true;
                }
            }
        }
        session.setAttribute("message", message);
        return false;
    }

    public boolean updatePassword(HttpSession session, String oldPassword, String newPassword1, String newPassword2) {
        if (session == null) return false;
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        ServiceMessage message;
        if (userProfile == null || oldPassword == null || newPassword1 == null || newPassword2 == null) message = UNEXPECTED_INPUT;
        else if (!userProfile.password().equals(oldPassword)) message = PASSWORD_INCORRECT;
        else if (isWrongFormat(newPassword1)) message = PASSWORD_WRONG_FORMAT;
        else if (!newPassword1.equals(newPassword2)) message = PASSWORDS_NOT_MATCHING;
        else if (userProfile.password().equals(newPassword1)) message = PASSWORD_SIMILAR;
        else {
            UserProfile updatedUserProfile = userProfileRepository.updatePassword(userProfile.userID(), newPassword1);
            if (updatedUserProfile == null) message = UNEXPECTED_OUTPUT;
            else {
                session.setAttribute("message", PASSWORD_UPDATED);
                session.setAttribute("userProfile", updatedUserProfile);
                return true;
            }
        }
        session.setAttribute("message", message);
        return false;
    }

    public boolean deleteUserProfile(HttpSession session, String password) {
        if (session == null) return false;
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        ServiceMessage message;
        if (userProfile == null || password == null) message = UNEXPECTED_INPUT;
        else if (!userProfile.password().equals(password)) message = PASSWORD_INCORRECT;
        else if (!userProfileRepository.deleteUserProfile(userProfile.userID())) message = UNEXPECTED_OUTPUT;
        else {
            session.setAttribute("message", USER_DELETED);
            session.setAttribute("userProfile", null);
            return true;
        }
        session.setAttribute("message", message);
        return false;
    }
}
