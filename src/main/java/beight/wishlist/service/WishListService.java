package beight.wishlist.service;

import beight.wishlist.model.WishList;
import beight.wishlist.repository.WishListRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public boolean createWish(HttpSession session, String wishName, String wishPrice, String wishLink, String wishDescription) {
        WishList wishList = wishListRepository.createWish(wishName, Double.parseDouble(wishPrice), wishLink, wishDescription);
        session.setAttribute("wishList", wishList);
        return true;
    }
}
