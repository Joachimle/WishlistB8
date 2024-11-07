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
    public String frontpage(@RequestParam(defaultValue = "") String message, Model model) {
        model.addAttribute("message", message);
        return "frontpage";
    }

    @GetMapping("/create_user")
    public String createUser() {
        return "create_user";
    }

    @GetMapping("/homepage")
    public String homepage(@RequestParam(defaultValue = "") String message, Model model, HttpSession session) {
        if (isNotLoggedIn(session)) {
            return "frontpage";
        }
        model.addAttribute("message", message);
        model.addAttribute("username", session.getAttribute("user"));
        return "userpage";
    }

    @GetMapping("/update_username")
    public String updateUsername(Model model, HttpSession session) {
        if (isNotLoggedIn(session)) {
            return "frontpage";
        }
        model.addAttribute("username", session.getAttribute("user"));
        return "update_username";
    }

    @PostMapping("/save_user")
    public String saveUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        String message = userProfileService.createUserProfile(username, password);
        return "redirect:/" + "?message=" + message;
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        if(userProfileService.login(username, password)){
            session.setAttribute("user", username);
            return "redirect:/homepage";
        }
        return "redirect:/";
    }

    @PostMapping("/save_username")
    public String saveUsername(@RequestParam("username") String username, HttpSession session) {
        if (isNotLoggedIn(session)) {
            return "redirect:/";
        }
        String message = userProfileService.updateUsername((String) session.getAttribute("user"), username);
        if (message.equals("Brugernavn ændret.")) {
            session.setAttribute("user", username);
        }
        return "redirect:/homepage" + "?message=" + message;
    }

    private boolean isNotLoggedIn(HttpSession session) {
        return session.getAttribute("user") == null;
    }
}
