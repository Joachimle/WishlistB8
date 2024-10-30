package beight.wishlist.controller;

import beight.wishlist.service.UserProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService){
        this.userProfileService = userProfileService;
    }

    @GetMapping("/")
        public String createUserFrontpage(){
        return "frontpage";
    }
}
