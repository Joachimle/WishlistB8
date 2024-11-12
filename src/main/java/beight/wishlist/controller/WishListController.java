package beight.wishlist.controller;

import beight.wishlist.model.UserProfile;
import beight.wishlist.service.ServiceMessage;
import beight.wishlist.service.WishListService;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping("/min-side")
    public String homepage(HttpSession session, Model model) {
        if (isNotLoggedIn(session)) return "redirect:/";
        model.addAttribute("username", getUsername(session));
        model.addAttribute("wishLists", wishListService.readWishLists(session));
        return "userpage";
    }

    @GetMapping("/opret-onskeliste")
    public String createWishList(HttpSession session, Model model) {
        if (isNotLoggedIn(session)) return "redirect:/";
        model.addAttribute("message", takeDanishMessage(session));
        return "create_wishlist";
    }

    @PostMapping("/onskeliste-oprettet")
    public String saveNewWishList(HttpSession session, @RequestParam("title") String title, @RequestParam("description") String description) {
        if (isNotLoggedIn(session)) return "redirect:/";
        if (wishListService.createWishList(session, title, description)) return "redirect:/min-side";
        return "redirect:/opret-onskeliste";
    }

    @GetMapping("/onskelister/{id}")
    public String showWishList(HttpSession session, @PathVariable int id, Model model) {
        if (isNotYourWishList(session, id)) return "redirect:/";
        model.addAttribute("wishList", wishListService.readWishList(id));
        model.addAttribute("wishes", wishListService.readWishes(id));
        return "wishList";
    }

    @GetMapping("/onskelister/{id}/tilfoj-onske")
    public String createWish(HttpSession session, @PathVariable int id, Model model) {
        if (isNotYourWishList(session, id)) return "redirect:/";
        model.addAttribute("message", takeDanishMessage(session));
        model.addAttribute("wishListID", id);
        return "wish";
    }

    @PostMapping("/onskelister/{id}/onske-tilfojet")
    public String saveNewWish(HttpSession session, @PathVariable int id, @RequestParam("title") String title, @RequestParam("price") String price, @RequestParam("link") String link, @RequestParam("description") String description) {
        if (isNotYourWishList(session, id)) return "redirect:/";
        if (wishListService.createWish(id, title, price, link, description)) return "redirect:/onskelister/" + id;
        return "redirect:/onskelister/" + id + "/tilfoj-onske";
    }

    @GetMapping("/onskelister/{id}/rediger-onskeliste")
    public String updateWishList(HttpSession session, @PathVariable int id, Model model) {
        if (isNotYourWishList(session, id)) return "redirect:/";
        model.addAttribute("wishList", wishListService.readWishList(id));
        return "update_wish_list";
    }

    @PostMapping("/onskelister/{id}/onskeliste-redigeret")
    public String saveWishlist(HttpSession session, @PathVariable int id, @RequestParam("title") String title, @RequestParam("description") String description) {
        if (isNotYourWishList(session, id)) return "redirect:/";
        if (wishListService.updateWishList(id, title, description)) return "redirect:/onskelister/" + id;
        return "redirect:/onskelister/" + id + "/rediger-onskeliste";
    }

    @GetMapping("/onsker/{id}/rediger-onske")
    public String updateWish(HttpSession session, @PathVariable int id, Model model) {
        if (isNotYourWish(session, id)) return "redirect:/";
        model.addAttribute("wish", wishListService.readWish(id));
        model.addAttribute("wishListID", wishListService.readWishListIDByWishID(id));
        return "update_wish";
    }

    @PostMapping("/onsker/{id}/onske-redigeret")
    public String saveWish(HttpSession session, @PathVariable int id, @RequestParam("title") String title, @RequestParam("price") String price, @RequestParam("link") String link, @RequestParam("description") String description) {
        if (isNotYourWish(session, id)) return "redirect:/";
        if (wishListService.updateWish(id, title, price, link, description)) return "redirect:/onskelister/" + wishListService.readWishListIDByWishID(id);
        return "redirect:/onsker/" + id + "/rediger-onske";
    }

    private boolean isNotLoggedIn(HttpSession session) {
        return session.getAttribute("userProfile") == null;
    }

    private boolean isNotYourWish(HttpSession session, int wishID) {
        return !wishListService.readIfOwnWish(session, wishID);
    }

    private boolean isNotYourWishList(HttpSession session, int wishListID) {
        return session.getAttribute(wishListID+"") == null;
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
