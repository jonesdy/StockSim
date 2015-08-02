package com.jonesdy.data.access;

public class UserDao extends DatabaseDao
{
   private static final String SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
   private static final String SELECT_USER_BY_CONFIRM_CODE = "SELECT * FROM users WHERE confirmCode = ?";
   private static final String SET_CONFIRMED_BY_CONFIRM_CODE = "UPDATE users SET confirmed = true WHERE confirmCode = ?";
   private static final String INSERT_USER = "INSERT INTO users (username, password, email, confirmCode, enabled, confirmed) VALUES (?, ?, ?, ?, ?, ?)";
   private static final String DELETE_USER_BY_USERNAME = "DELETE FROM users WHERE username = ?";
}
