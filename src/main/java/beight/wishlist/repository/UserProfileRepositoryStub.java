package beight.wishlist.repository;
import beight.wishlist.model.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("USERPROFILE_REPOSITORY_STUB")
public class UserProfileRepositoryStub implements UserProfileRepository {

    private List<UserProfile> users = new ArrayList<>();

    @Override
    public void createUser(String username, String password) {
        users.add(new UserProfile(username, password));
    }

}
