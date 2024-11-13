package beight.wishlist.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.Objects;

public record UserProfile(int userID, String username, String password) {

    public UserProfile {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
    }

    public static RowMapper<UserProfile> ROW_MAPPER = (ResultSet rs, int rowNum) ->
            new UserProfile(rs.getInt("userID"),
                    rs.getString("username"),
                    rs.getString("password"));
}