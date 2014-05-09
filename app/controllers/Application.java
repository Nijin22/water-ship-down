package controllers;

import model.java.Matches;
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
    
    public static Result placeShips(String username) {
    	//TODO: GET username auslesen + Uniquie ID erzeugen + session cookie setzen
    	Matches matches = Matches.getInstance();
    	
        return ok(placeShips.render(username));
     }

}
