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
    public String saveNewWishList(HttpSession session, @RequestParam String title, @RequestParam String description) {
        if (isNotLoggedIn(session)) return "redirect:/";
        if (wishListService.createWishList(session, title, description)) return "redirect:/min-side";
        return "redirect:/opret-onskeliste";
    }

    @GetMapping("/onskeliste/{id}")
    public String showWishList(HttpSession session, @PathVariable int id, Model model) {
        model.addAttribute("wishList", wishListService.readWishList(id));
        if (isNotLoggedIn(session)) {
            model.addAttribute("wishes", wishListService.readWishes(id));
            return "external_wish_list";
        }
        if (isNotYourWishList(session, id)) {
            model.addAttribute("reservations", wishListService.readReservations(session, id));
            return "reservation";
        }
        model.addAttribute("wishes", wishListService.readWishes(id));
        return "wishList";
    }

    @GetMapping("/onskeliste/{id}/tilfoj-onske")
    public String createWish(HttpSession session, @PathVariable int id, Model model) {
        if (isNotYourWishList(session, id)) return "redirect:/";
        model.addAttribute("message", takeDanishMessage(session));
        model.addAttribute("wishListID", id);
        return "wish";
    }

    @PostMapping("/onskeliste/{id}/onske-tilfojet")
    public String saveNewWish(HttpSession session, @PathVariable int id, @RequestParam String title, @RequestParam String number, @RequestParam String price, @RequestParam String link, @RequestParam String description) {
        if (isNotYourWishList(session, id)) return "redirect:/";
        if (wishListService.createWish(id, title, number, price, link, description)) return "redirect:/onskeliste/" + id;
        return "redirect:/onskeliste/" + id + "/tilfoj-onske";
    }

    @GetMapping("/onskeliste/{id}/rediger-onskeliste")
    public String updateWishList(HttpSession session, @PathVariable int id, Model model) {
        if (isNotYourWishList(session, id)) return "redirect:/";
        model.addAttribute("message", takeDanishMessage(session));
        model.addAttribute("wishList", wishListService.readWishList(id));
        return "update_wish_list";
    }

    @PostMapping("/onskeliste/{id}/onskeliste-redigeret")
    public String saveWishlist(HttpSession session, @PathVariable int id, @RequestParam String title, @RequestParam String description) {
        if (isNotYourWishList(session, id)) return "redirect:/";
        if (wishListService.updateWishList(id, title, description)) return "redirect:/onskeliste/" + id;
        return "redirect:/onskeliste/" + id + "/rediger-onskeliste";
    }

    @GetMapping("/onskeliste/{wishListID}/rediger-onske/{wishID}")
    public String updateWish(HttpSession session, @PathVariable int wishListID, @PathVariable int wishID, Model model) {
        if (isNotYourWishList(session, wishListID)) return "redirect:/";
        model.addAttribute("message", takeDanishMessage(session));
        model.addAttribute("wish", wishListService.readWish(wishID));
        model.addAttribute("wishListID", wishListID);
        return "update_wish";
    }

    @PostMapping("/onskeliste/{wishListID}/onske-redigeret/{wishID}")
    public String saveWish(HttpSession session, @PathVariable int wishListID, @PathVariable int wishID, @RequestParam String title, @RequestParam String number, @RequestParam String price, @RequestParam String link, @RequestParam String description) {
        if (isNotYourWishList(session, wishListID)) return "redirect:/";
        if (wishListService.updateWish(wishID, title, number, price, link, description)) return "redirect:/onskeliste/" + wishListID;
        return "redirect:/onskeliste/" + wishListID + "/rediger-onske/" + wishID;
    }

    @PostMapping("/onskeliste/{id}/slet-onskeliste")
    public String deleteWishList(HttpSession session, @PathVariable int id, @RequestParam(required = false) String confirm) {
        if(isNotYourWishList(session, id)) return "redirect:/";
        if(wishListService.deleteWishList(session, id, confirm)) return "redirect:/min-side";
        return "redirect:/onskeliste/" + id + "/rediger-onskeliste";
    }

    @PostMapping("/onskeliste/{wishListID}/slet-onske/{wishID}")
    public String deleteWish(HttpSession session, @PathVariable int wishListID, @PathVariable int wishID, @RequestParam(required = false) String confirm) {
        if (isNotYourWishList(session, wishListID)) return "redirect:/";
        if (wishListService.deleteWish(session, wishID, confirm)) return "redirect:/onskeliste/" + wishListID;
        return "redirect:/onskeliste/" + wishListID + "/rediger-onske/" + wishID;
    }

    @PostMapping("/reservations")
    public String reservation(HttpSession session, @RequestParam int wishListID, @RequestParam int wishID, @RequestParam String number) {
        wishListService.reserveNumber(session, wishID, number);
        return "redirect:/onskeliste/" + wishListID;
    }

    @PostMapping("/reservation")
    public String reservation(HttpSession session, @RequestParam int wishListID, @RequestParam int wishID, @RequestParam(defaultValue = "off") String reserve, @RequestParam(defaultValue = "off") String unreserve) {
        wishListService.reserveSingle(session, wishID, reserve, unreserve);
        return "redirect:/onskeliste/" + wishListID;
    }

    private boolean isNotLoggedIn(HttpSession session) {
        return session.getAttribute("userProfile") == null;
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
