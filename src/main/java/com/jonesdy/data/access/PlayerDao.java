package com.jonesdy.data.access;

public class PlayerDao extends DatabaseDao
{
   private static final String SELECT_PLAYERS_BY_USERNAME = "SELECT pid, username, gid, balance, isAdmin, inviteCode, enabled FROM players WHERE username = ?";
   private static final String SELECT_PLAYER_BY_USERNAME_AND_GID = "SELECT pid, username, gid, balance, isAdmin, inviteCode, enabled FROM players WHERE username = ? AND gid = ?";
   private static final String SELECT_PLAYER_BY_INVITE_CODE = "SELECT * FROM players WHERE inviteCode = ?";
   private static final String INSERT_PLAYER = "INSERT INTO players (username, gid, balance, isAdmin, inviteCode, enabled) VALUES (?, ?, ?, ?, ?, ?)";
   private static final String SET_ENABLED_BY_INVITE_CODE = "UPDATE players SET enabled = true WHERE inviteCode = ?";
   private static final String DELETE_PLAYER_BY_INVITE_CODE = "DELETE FROM players WHERE inviteCode = ?";
}
