package com.app.restaurante.dao;

import com.app.restaurante.model.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PasswordResetTokenDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<PasswordResetToken> rowMapper = new RowMapper<PasswordResetToken>() {
        @Override
        public PasswordResetToken mapRow(ResultSet rs, int rowNum) throws SQLException {
            PasswordResetToken token = new PasswordResetToken();
            token.setId(rs.getInt("id"));
            token.setEmail(rs.getString("email"));
            token.setToken(rs.getString("token"));
            token.setExpirationDate(rs.getTimestamp("expiration_date").toLocalDateTime());
            return token;
        }
    };

    public void save(PasswordResetToken token) {
        String sql = "INSERT INTO password_reset_token (email, token, expiration_date) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, token.getEmail(), token.getToken(), token.getExpirationDate());
    }

    public PasswordResetToken findByToken(String token) {
        String sql = "SELECT * FROM password_reset_token WHERE token = ?";
        List<PasswordResetToken> tokens = jdbcTemplate.query(sql, rowMapper, token);
        return tokens.isEmpty() ? null : tokens.get(0);
    }

    public void delete(String token) {
        String sql = "DELETE FROM password_reset_token WHERE token = ?";
        jdbcTemplate.update(sql, token);
    }
}
