package beight.wishlist.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.Objects;

public record WishList(int wishListID, String title, String description) {

    public WishList {
        Objects.requireNonNull(title);
        Objects.requireNonNull(description);
    }

    public static RowMapper<WishList> ROW_MAPPER = (ResultSet rs, int rowNum) ->
            new WishList(rs.getInt("wishListID"),
                    rs.getString("title"),
                    rs.getString("description"));
}
