package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class MainController extends Controller {
    
    public static Result index() {
        return ok(views.html.index.render("hallo"));
    }
    
    public static Result neu() {
        return ok(views.html.neu.render());
    }
    
}
