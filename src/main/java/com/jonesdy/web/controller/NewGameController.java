package com.jonesdy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.jonesdy.database.DatabaseHelper;
import com.jonesdy.database.model.DbGame;
import com.jonesdy.database.model.DbPlayer;
import com.jonesdy.web.model.FormGame;

@Controller
@RequestMapping(value = "/newGame")
public class NewGameController
{
   @RequestMapping(method = RequestMethod.GET)
   public ModelAndView viewNewGame()
   {
      ModelAndView model = new ModelAndView();

      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if(!(auth instanceof AnonymousAuthenticationToken))
      {
         model.addObject("game", new FormGame());
         model.setViewName("newGame");
      }

      return model;
   }

   @RequestMapping(method = RequestMethod.POST)
   public ModelAndView processNewGame(@ModelAttribute FormGame game)
   {
      ModelAndView model = new ModelAndView();

      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if(auth instanceof AnonymousAuthenticationToken)
      {
         model.addObject("failureReason", "You are not logged in.");
         model.setViewName("newGameFailure");
      }  
      else if(game.getStartingMoney() <= 0)
      {
         model.addObject("failureReason", "Starting Money is less than or equal to 0.");
         model.setViewName("newGameFailure");
      }
      else if(game.getTitle() == null || game.getTitle().length() <= 0)
      {
         model.addObject("failureReason", "Game Title is empty.");
         model.setViewName("newGameFailure");
      }
      else if(game.getTitle().length() > 60)
      {
         model.addObject("failureReason", "Game Title is too long (must be less than or equal to 60 characters).");
         model.setViewName("newGameFailure");
      }
      else if(DatabaseHelper.getDbGameByTitle(game.getTitle()) != null)
      {
         model.addObject("failureReason", "Game Title already exists.");
         model.setViewName("newGameFailure");
      }
      else
      {
         String newGameError = addNewGame(game);
         if(newGameError == null)
         {
            model.addObject("game", game);
            model.setViewName("newGameSuccess");
         }
         else
         {
            model.addObject("failureReason", newGameError);
            model.setViewName("newGameFailure");
         }
      }

      return model;
   }

   private String addNewGame(FormGame game)
   {
      DbGame dbGame = new DbGame();
      dbGame.setTitle(game.getTitle());
      dbGame.setStartingMoney(game.getStartingMoney() * 100);  // Convert dollars to cents
      dbGame.setPrivateGame(game.isPrivateGame());
      if(DatabaseHelper.addNewGame(dbGame))
      {
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         if(auth instanceof AnonymousAuthenticationToken)
         {
            return "You are not logged in.";
         }
         UserDetails user = (UserDetails)auth.getPrincipal();

         dbGame = DatabaseHelper.getDbGameByTitle(game.getTitle());
         if(dbGame == null)
         {
            return "Unable to add game to database.";
         }

         DbPlayer player = new DbPlayer();
         player.setUsername(user.getUsername());
         player.setGid(dbGame.getGid());
         player.setBalance(dbGame.getStartingMoney());
         player.setIsAdmin(true);
         if(!DatabaseHelper.addNewPlayer(player))
         {
            return "Unable to add user as a player in the game.";
         }

         return null;
      }
      else
      {
         return "Unable to add game.";
      }
   }
}
