package beight.wishlist.controller;

import beight.wishlist.model.UserProfile;
import beight.wishlist.service.UserProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService){
        this.userProfileService = userProfileService;
    }

    @GetMapping("/")
    public String frontpage() {
        return "frontpage";
    }

    @GetMapping("/create_user")
    public String createUser(Model model) {
        model.addAttribute("userProfile", new UserProfile());
        return "create_user";
    }

    @PostMapping("/save_user")
    public String saveUser(@ModelAttribute UserProfile userProfile) {
        userProfileService.createUserProfile(userProfile);
        return "redirect:/"; // TODO: Lav denne linje om.
    }
}
