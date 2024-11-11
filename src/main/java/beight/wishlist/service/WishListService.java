package beight.wishlist.service;

import beight.wishlist.model.UserProfile;
import beight.wishlist.model.Wish;
import beight.wishlist.model.WishList;
import beight.wishlist.repository.WishListRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public boolean createWishList(HttpSession session, String title, String description) {
        // Titel må ikke være tom osv.
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        return wishListRepository.createWishList(userProfile.userID(), title.isEmpty() ? "Ønskeliste uden titel" : title, description);
    }

    public boolean createWish(int wishListID, String title, String price, String link, String description) {
        int priceAsInt = 0;
        try {
            priceAsInt = Integer.parseInt(price);
        } catch (NumberFormatException _) {}
        return wishListRepository.createWish(wishListID, title.isEmpty() ? "Ønske uden titel" : title, priceAsInt, link, description);
    }

    public List<WishList> readWishLists(HttpSession session) {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        List<WishList> wishLists = wishListRepository.readWishLists(userProfile.userID());
        for (WishList wishList : wishLists) session.setAttribute(wishList.wishListID() + "", "s");
        return wishLists;
    }

    public WishList readWishList(int wishListID) {
        return wishListRepository.readWishList(wishListID);
    }

    public List<Wish> readWishes(int wishListID) {
        return wishListRepository.readWishes(wishListID);
    }
}
