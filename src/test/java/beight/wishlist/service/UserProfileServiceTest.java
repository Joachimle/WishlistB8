package beight.wishlist.service;

import beight.wishlist.repository.UserProfileRepositoryStub;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserProfileServiceTest {

    @Test
    void login(){
        UserProfileService userProfileService = new UserProfileService(new UserProfileRepositoryStub());

        assertTrue(userProfileService.login("test","test"));
        assertFalse(userProfileService.login("fejl","fejl"));

    }
}
