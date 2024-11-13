package beight.wishlist.repository;

import beight.wishlist.model.Reservation;
import beight.wishlist.model.Wish;
import beight.wishlist.model.WishList;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("WISHLIST_REPOSITORY_STUB")
public class WishListRepositoryStub implements WishListRepository {

    private record Reserved(int userID, int reservations) {}
    private final Map<Integer, List<WishList>> userToWishLists;
    private final Map<Integer, List<Wish>> wishListToWishes;
    private final Map<Integer, List<Reserved>> wishToReservations;
    private int nextWishList;
    private int nextWish;

    public WishListRepositoryStub() {
       userToWishLists = new HashMap<>();
       wishListToWishes = new HashMap<>();
       wishToReservations = new HashMap<>();
       nextWishList = 0;
       nextWish = 0;
    }

    @Override
    public boolean createWishList(int userID, String title, String description) {
        WishList wishList = new WishList(nextWishList++, title, description);
        userToWishLists.putIfAbsent(userID, new ArrayList<>());
        wishListToWishes.put(wishList.wishListID(), new ArrayList<>());
        return userToWishLists.get(userID).add(wishList);
    }

    @Override
    public boolean createWish(int wishListID, String title, int numberOfUnits, int pricePerUnit, String link, String description) {
        List<Wish> wishes = wishListToWishes.get(wishListID);
        Wish wish = new Wish(nextWish++, title, numberOfUnits, pricePerUnit, link, description);
        wishToReservations.put(wish.wishID(), new ArrayList<>());
        return wishes.add(wish);
    }

    @Override
    public List<WishList> readWishLists(int userID) {
        userToWishLists.putIfAbsent(userID, new ArrayList<>());
        return userToWishLists.get(userID);
    }

    @Override
    public WishList readWishList(int wishListID) {
        for (List<WishList> wishLists : userToWishLists.values()) {
            for (WishList wishList : wishLists) {
                if (wishList.wishListID() == wishListID) {
                    return wishList;
                }
            }
        }
        return null;
    }

    @Override
    public List<Wish> readWishes(int wishListID) {
        return wishListToWishes.get(wishListID);
    }

    @Override
    public Wish readWish(int wishID) {
        for (List<Wish> wishes : wishListToWishes.values()) {
            for (Wish wish : wishes) {
                if (wish.wishID() == wishID) {
                    return wish;
                }
            }
        }
        return null;
    }

    @Override
    public List<Reservation> readReservations(int userID, int wishListID) {
        List<Reservation> reservations = new ArrayList<>();
        for (Wish wish : wishListToWishes.get(wishListID)) {
            reservations.add(readReservation(wish.wishID(), userID));
        }
        return reservations;
    }

    @Override
    public Reservation readReservation(int wishID, int userID) {
        int your = 0;
        int other = 0;
        for (Reserved r : wishToReservations.get(wishID)) {
            if (r.userID == userID) your = r.reservations;
            else other += r.reservations;
        }
        return new Reservation(readWish(wishID), your, other);
    }

    @Override
    public boolean updateWishList(int wishListID, String title, String description) {
        for (Map.Entry<Integer, List<WishList>> entry : userToWishLists.entrySet()) {
            List<WishList> wishLists = entry.getValue();
            for (WishList wishList : wishLists) {
                if (wishList.wishListID() == wishListID) {
                    wishLists.remove(wishList);
                    wishLists.add(new WishList(wishListID, title, description));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateWish(int wishID, String title, int numberOfUnits, int pricePerUnit, String link, String description) {
        for (Map.Entry<Integer, List<Wish>> entry : wishListToWishes.entrySet()) {
            List<Wish> wishes = entry.getValue();
            for (Wish wish : wishes) {
                if (wish.wishID() == wishID) {
                    wishes.remove(wish);
                    wishes.add(new Wish(wishID, title, numberOfUnits, pricePerUnit, link, description));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean createOrUpdateReservation(int wishID, int userID, int numberOfUnits) {
        wishToReservations.putIfAbsent(wishID, new ArrayList<>());
        wishToReservations.get(wishID).removeIf(reserved -> reserved.userID() == userID);
        wishToReservations.get(wishID).add(new Reserved(userID, numberOfUnits));
        return true;
    }

    @Override
    public boolean deleteWishList(int wishListID) {
        for (Map.Entry<Integer, List<WishList>> entry : userToWishLists.entrySet()) {
            List<WishList> wishLists = entry.getValue();
            for (WishList wishList : wishLists) {
                if (wishList.wishListID() == wishListID) {
                    wishLists.remove(wishList);
                    wishListToWishes.remove(wishListID);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteWish(int wishID) {
        for (Map.Entry<Integer, List<Wish>> entry : wishListToWishes.entrySet()) {
            List<Wish> wishes = entry.getValue();
            for (Wish wish : wishes) {
                if (wish.wishID() == wishID) {
                    wishes.remove(wish);
                    return true;
                }
            }
        }
        return false;
    }
}
