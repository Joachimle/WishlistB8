package beight.wishlist.service;

import beight.wishlist.model.Reservation;
import beight.wishlist.model.UserProfile;
import beight.wishlist.model.Wish;
import beight.wishlist.model.WishList;
import beight.wishlist.repository.WishListRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

import static beight.wishlist.service.ServiceMessage.*;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public boolean createWishList(HttpSession session, String title, String description) {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        return wishListRepository.createWishList(userProfile.userID(), title.isEmpty() ? NO_TITLE_WISH_LIST.dansk : title, description);
    }

    public boolean createWish(int wishListID, String title, String numberOfUnits, String pricePerUnit, String link, String description) {
        int number = 0;
        try {
            number = Integer.parseInt(numberOfUnits);
        } catch (NumberFormatException _) {}
        int price = 0;
        try {
            price = Integer.parseInt(pricePerUnit);
        } catch (NumberFormatException _) {}
        return wishListRepository.createWish(wishListID, title.isEmpty() ? NO_TITLE_WISH.dansk : title, Math.max(number, 1), Math.max(price, 0), link, description);
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

    public Wish readWish(int wishID) {
        return wishListRepository.readWish(wishID);
    }

    public List<Reservation> readReservations(HttpSession session, int wishListID) {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        return wishListRepository.readReservations(userProfile.userID(), wishListID);
    }

    public boolean updateWishList(int wishListID, String title, String description) {
        return wishListRepository.updateWishList(wishListID, title.isEmpty() ? NO_TITLE_WISH_LIST.dansk : title, description);
    }

    public boolean updateWish(int wishID, String title, String numberOfUnits, String pricePerUnit, String link, String description) {
        int number = 0;
        try {
            number = Integer.parseInt(numberOfUnits);
        } catch (NumberFormatException _) {}
        int price = 0;
        try {
            price = Integer.parseInt(pricePerUnit);
        } catch (NumberFormatException _) {}
        return wishListRepository.updateWish(wishID, title.isEmpty() ? NO_TITLE_WISH.dansk : title, Math.max(number, 1), Math.max(price, 0), link, description);
    }

    public boolean deleteWishList(HttpSession session, int wishListID, String confirm) {
        if (confirm.equals("on")) {
            if (wishListRepository.deleteWishList(wishListID)) return true;
            session.setAttribute("message", UNEXPECTED_OUTPUT);
            return false;
        }
        session.setAttribute("message", DELETION_NOT_CONFIRMED);
        return false;
    }

    public boolean deleteWish(HttpSession session, int wishID, String confirm) {
        if (confirm.equals("on")) {
            if (wishListRepository.deleteWish(wishID)) return true;
            session.setAttribute("message", UNEXPECTED_OUTPUT);
            return false;
        }
        session.setAttribute("message", DELETION_NOT_CONFIRMED);
        return false;
    }

    public void reserve(HttpSession session, int wishID, int numberOfUnits) {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        Reservation r = wishListRepository.readReservation(wishID, userProfile.userID());
        // Vi korrigerer, så man ikke kan reservere flere end muligt.
        int yourReservations = r.otherReservations() + numberOfUnits > r.wish().numberOfUnits() ? r.wish().numberOfUnits() - r.otherReservations() : numberOfUnits;
        wishListRepository.createOrUpdateReservation(wishID, userProfile.userID(), yourReservations);
    }
}
