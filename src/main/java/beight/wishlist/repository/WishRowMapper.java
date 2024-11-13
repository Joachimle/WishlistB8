package beight.wishlist.repository;

import beight.wishlist.model.Wish;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishRowMapper implements RowMapper<Wish> {

    @Override
    public Wish mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Wish(rs.getInt("wishID"),
                rs.getString("title"),
                rs.getInt("number"),
                rs.getInt("price"),
                rs.getString("link"),
                rs.getString("description"));
    }
}