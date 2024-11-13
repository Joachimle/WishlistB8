package beight.wishlist.repository;

import beight.wishlist.model.UserProfile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfileRowMapper implements RowMapper<UserProfile> {

    @Override
    public UserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new UserProfile(rs.getInt("userID"), rs.getString("username"), rs.getString("password"));
    }
}