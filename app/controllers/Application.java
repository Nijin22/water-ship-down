package controllers;

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

}
