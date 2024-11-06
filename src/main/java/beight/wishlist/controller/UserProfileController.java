package beight.wishlist.controller;

import beight.wishlist.model.UserProfile;
import beight.wishlist.service.UserProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/homepage")
    public String homepage(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "frontpage";
        }
        model.addAttribute("username", session.getAttribute("user"));
        return "userpage";
    }

    @GetMapping("/update_password")
    public String updatePassword(Model model, HttpSession session) {
        if(isNotLoggedIn(session)){
            return "frontpage";
        }
        model.addAttribute("password", session.getAttribute("password"));
        return "update_password";
    }

    @PostMapping("/save_password")
    public String savePassword(@RequestParam("new_password") String password, HttpSession session){
        if(isNotLoggedIn(session)){
            return "redirect:/";
        }
        userProfileService.updatePassword((String) session.getAttribute("password"), password);
        session.setAttribute("password", password);
        return "redirect:/homepage";
    }

    @PostMapping("/save_user")
    public String saveUser(@ModelAttribute UserProfile userProfile) {
        userProfileService.createUserProfile(userProfile);
        return "redirect:/"; // TODO: redirect til login? homepage? bekræftelsesside?
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        if(userProfileService.login(username, password)){
            session.setAttribute("user", username);
            session.setAttribute("password", password);
            return "redirect:/homepage";
        }
        return "redirect:/";
    }

    private boolean isNotLoggedIn(HttpSession session){
        return session.getAttribute("user") == null;
    }
}
