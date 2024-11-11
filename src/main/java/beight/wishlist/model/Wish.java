package beight.wishlist.model;

public class Wish {
    String wishName;
    double wishPrice;
    String wishLink;
    String wishDescription;

    public Wish(String wishName, double wishPrice, String wishLink, String wishDescription) {
        this.wishName = wishName;
        this.wishPrice = wishPrice;
        this.wishLink = wishLink;
        this.wishDescription = wishDescription;
    }

    public String getWishName() {
        return wishName;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
    }

    public double getWishPrice() {
        return wishPrice;
    }

    public void setWishPrice(double wishPrice) {
        this.wishPrice = wishPrice;
    }

    public String getWishLink() {
        return wishLink;
    }

    public void setWishLink(String wishLink) {
        this.wishLink = wishLink;
    }

    public String getWishDescription() {
        return wishDescription;
    }

    public void setWishDescription(String wishDescription) {
        this.wishDescription = wishDescription;
    }
}
