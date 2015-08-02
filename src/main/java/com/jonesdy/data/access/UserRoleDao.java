package com.jonesdy.data.access;

public class UserRoleDao extends DatabaseDao
{
   private static final String INSERT_USER_ROLE = "INSERT INTO user_roles (username, role) VALUES (?, ?)";
   private static final String DELETE_USER_ROLES_BY_USERNAME = "DELETE FROM user_roles WHERE username = ?";
}
