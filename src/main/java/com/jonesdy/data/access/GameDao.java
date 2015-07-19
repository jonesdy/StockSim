package com.jonesdy.data.access;

public class GameDao extends DatabaseDao
{
   private static final String SELECT_GAME_BY_GID = "select gid, title, startingMoney, privateGame, startTimestamp from games where gid = ?";
   private static final String SELECT_PUBLIC_GAMES = "select gid, title, startingMoney, privateGame, startTimestamp from games where privateGame = 0";
   private static final String SELECT_GAME_BY_TITLE = "select gid, title, startingMoney, privateGame, startTimestamp from games where title = ?";
   private static final String SELECT_GAMES_BY_USERNAME = "select gid, title, startingMoney, privateGame, startTimestamp from games g join players p on p.gid = g.gid where p.username = ?"; 
   private static final String INSERT_GAME = "insert into game (gid, title, startingMoney, privateGame, startTimestamp) values (?, ?, ?, ?, ?)";
}
