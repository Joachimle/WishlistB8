package beight.wishlist.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.Objects;

public record Wish(int wishID, String title, int numberOfUnits, int pricePerUnit, String link, String description) {

    public Wish {
        Objects.requireNonNull(title);
        Objects.requireNonNull(link);
        Objects.requireNonNull(description);
    }

    public static RowMapper<Wish> ROW_MAPPER = (ResultSet rs, int rowNum) ->
            new Wish(rs.getInt("wishID"),
                    rs.getString("title"),
                    rs.getInt("number"),
                    rs.getInt("price"),
                    rs.getString("link"),
                    rs.getString("description"));
}