package com.jonesdy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jonesdy.web.model.FormGame;

@Controller
@RequestMapping(value = "/newGame")
public class NewGameController
{
   @RequestMapping(method = RequestMethod.GET)
   public ModelAndView viewNewGame()
   {
      ModelAndView model = new ModelAndView();
      model.addObject("game", new FormGame());
      model.setViewName("newGame");
      return model;
   }

   @RequestMapping(method = RequestMethod.POST)
   public ModelAndView processNewGame(@ModelAttribute FormGame game)
   {
      ModelAndView model = new ModelAndView();

      if(game.getStartingMoney() <= 0)
      {
         model.addObject("failureReason", "Starting Money is less than or equal to 0.");
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
      return null;
   }
}
