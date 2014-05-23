package controllers;

import model.java.Map;
import model.java.Match;
import model.java.MatchController;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
       return ok(index.render("Your new application is ready."));
    }
    
    public static Result start() {
       return ok(start.render());
    }
    
    public static Result playing() {
       return ok(playing.render());
    }
    
    public static Result createGame(String username){
    	MatchController matchController = MatchController.getInstance();
    	int matchID = matchController.addMatch(username);
    	
    	
    	session("matchID", Integer.toString(matchID)); //Save the unique match ID to a session cookie.
    	session("isHost", "true"); //To identify whether the user is host or guest, we set this session cookie.
    	
    	return ok(createGame.render(username));
    	
    }
    
    public static Result debug(){
    	return ok(debug.render(session("matchID"), session("isHost")));
    }
    
    
//    public static Result placeShips(String username) {
//    	//TODO: GET username auslesen + Uniquie ID erzeugen + session cookie setzen
//    	MatchController matches = MatchController.getInstance();
//    	
//        return ok(placeShips.render(username));
//     }

}
