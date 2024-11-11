package beight.wishlist.controller;

import beight.wishlist.model.UserProfile;
import beight.wishlist.model.WishList;
import beight.wishlist.service.WishListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }


    @GetMapping("/opret-oenske")
    public String createWish(HttpSession session) {
        if(isNotLoggedIn(session)){
            return "redirect:/";
        }
        return "wish";
    }

    @GetMapping("/se-oenskeliste")
    public String viewWishList(HttpSession session) {
        if(isNotLoggedIn(session)){
            return "redirect:/";
        }
        return "wishlist";
    }


    @PostMapping("/oenske-oprettet")
    public String addWish(HttpSession session,
                          @RequestParam("wishName") String wishName,
                          @RequestParam("wishPrice") String wishPrice,
                          @RequestParam("wishLink") String wishLink,
                          @RequestParam("wishDescription") String wishDescription) {
        if(isNotLoggedIn(session)){
            return "redirect:/";
        }
        if(wishListService.createWish(session,wishName,wishPrice,wishLink,wishDescription)) return "redirect:/se-oenskeliste";
        return "redirect:/opret-oenske";
    }



    private boolean isNotLoggedIn(HttpSession session) {
        return session.getAttribute("userProfile") == null;
    }

    private String getUsername(HttpSession session) {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        return userProfile.username();
    }
}
