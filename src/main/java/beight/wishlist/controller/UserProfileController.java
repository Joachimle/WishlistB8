package beight.wishlist.controller;

import beight.wishlist.model.UserProfile;
import beight.wishlist.service.ServiceMessage;
import beight.wishlist.service.UserProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/log-ind")
    public String loginPage(HttpSession session, Model model, @RequestParam(defaultValue = "frontpage") String onskeliste) {
        if (session.getAttribute("userProfile") != null) return "redirect:/min-side";
        model.addAttribute("message", takeDanishMessage(session));
        model.addAttribute("onskeliste", onskeliste);
        model.addAttribute("link", onskeliste.equals("frontpage") ? "/" : ("/onskeliste/" + onskeliste));
        return "login";
    }

    @GetMapping("/opret-bruger")
    public String createUserPage(HttpSession session, Model model, @RequestParam(defaultValue = "frontpage") String onskeliste) {
        model.addAttribute("message", takeDanishMessage(session));
        model.addAttribute("onskeliste", onskeliste);
        model.addAttribute("link", onskeliste.equals("frontpage") ? "/" : ("/onskeliste/" + onskeliste));
        return "create_user";
    }

    @GetMapping("/skift-brugernavn")
    public String updateUsernamePage(HttpSession session, Model model) {
        if (isNotLoggedIn(session)) return "redirect:/";
        model.addAttribute("message", takeDanishMessage(session));
        model.addAttribute("oldUsername", getUsername(session));
        return "update_username";
    }

    @GetMapping("/skift-adgangskode")
    public String updatePasswordPage(HttpSession session, Model model) {
        if (isNotLoggedIn(session)) return "redirect:/";
        model.addAttribute("message", takeDanishMessage(session));
        return "update_password";
    }

    @GetMapping("/slet-bruger")
    public String deleteUserPage(HttpSession session, Model model) {
        if (isNotLoggedIn(session)) return "redirect:/";
        model.addAttribute("message", takeDanishMessage(session));
        return "delete_user";
    }

    @PostMapping("/bruger-slettet")
    public String deleteUser(HttpSession session, Model model, @RequestParam String password) {
        if (isNotLoggedIn(session)) return "redirect:/";
        if (userProfileService.deleteUserProfile(session, password)) {
            model.addAttribute("message", takeDanishMessage(session));
            session.invalidate();
            return "message_frontpage";
        }
        return "redirect:/slet-bruger";
    }

    @PostMapping("/adgangskode-skiftet")
    public String savePassword(HttpSession session, Model model, @RequestParam("old_password") String oldPassword, @RequestParam("new_password_1") String newPassword1, @RequestParam("new_password_2") String newPassword2) {
        if(isNotLoggedIn(session)) return "redirect:/";
        if (userProfileService.updatePassword(session, oldPassword, newPassword1, newPassword2)) {
            model.addAttribute("message", takeDanishMessage(session));
            return "message_userpage";
        }
        return "redirect:/skift-adgangskode";
    }

    @PostMapping("/bruger-oprettet")
    public String saveUser(HttpSession session, @RequestParam String onskeliste, @RequestParam String username, @RequestParam("password_1") String password1, @RequestParam("password_2") String password2) {
        if (userProfileService.createUserProfile(session, username, password1, password2)) {
            if (onskeliste.equals("frontpage")) return "redirect:/min-side";
            return "redirect:/onskeliste/" + onskeliste;
        }
        return "redirect:/opret-bruger" + (onskeliste.equals("frontpage") ? "" : ("?onskeliste=" + onskeliste));
    }

    @PostMapping("/logget-ind")
    public String login(HttpSession session, @RequestParam String username, @RequestParam String password, @RequestParam String onskeliste) {
        if (userProfileService.login(session, username, password)) {
            if (onskeliste.equals("frontpage")) return "redirect:/min-side";
            return "redirect:/onskeliste/" + onskeliste;
        }
        return "redirect:/log-ind" + (onskeliste.equals("frontpage") ? "" : ("?onskeliste=" + onskeliste));
    }

    @PostMapping("/brugernavn-skiftet")
    public String saveUsername(HttpSession session, @RequestParam("new_username") String newUsername, @RequestParam("password") String password) {
        if (isNotLoggedIn(session)) return "redirect:/";
        if (userProfileService.updateUsername(session, password, newUsername)) return "redirect:/min-side";
        return "redirect:/skift-brugernavn";
    }

    @GetMapping("/log-ud")
    public String logout(HttpSession session, Model model, @RequestParam(defaultValue = "frontpage") String onskeliste) {
        model.addAttribute("message", ServiceMessage.LOGGED_OUT.dansk);
        session.invalidate();
        if (onskeliste.equals("frontpage")) return "message_frontpage";
        return "redirect:/onskeliste/" + onskeliste;
    }

    private boolean isNotLoggedIn(HttpSession session) {
        return session.getAttribute("userProfile") == null;
    }

    private String takeDanishMessage(HttpSession session) {
        ServiceMessage message = (ServiceMessage) session.getAttribute("message");
        if (message == null) return "";
        session.setAttribute("message", null);
        return message.dansk;
    }

    private String getUsername(HttpSession session) {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        return userProfile.username();
    }
}
