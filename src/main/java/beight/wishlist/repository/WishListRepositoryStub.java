package beight.wishlist.repository;

import beight.wishlist.model.Wish;
import beight.wishlist.model.WishList;
import org.springframework.stereotype.Repository;


@Repository("WISHLIST_REPOSITORY_STUB")
public class WishListRepositoryStub implements WishListRepository {

    private final WishList wishList;

    public WishListRepositoryStub() {
       wishList = new WishList();
    }


    @Override
    public WishList createWish(String wishName, double wishPrice, String wishLink, String wishDescription) {
        Wish newWish = new Wish(wishName, wishPrice, wishLink, wishDescription);
        wishList.add(newWish);
        return wishList;
    }


}
