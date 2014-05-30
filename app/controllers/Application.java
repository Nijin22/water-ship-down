package controllers;

import java.util.HashMap;
import model.java.Match;
import model.java.MatchController;
import play.mvc.*;
import views.html.*;

public class Application extends Controller{
	private static HashMap<Integer, WebSocket.Out<String>> hostsWaitingForGuest = new HashMap<Integer, WebSocket.Out<String>>();
    
    public static Result start() {
    	MatchController matchController = MatchController.getInstance();
    	HashMap<Integer, Match> openMatches = matchController.getOpenMatches();
    	//System.out.println(openMatches.toString());
    	return ok(start.render(openMatches));
    }
    
    public static Result joinGame(String username, String matchID){
    	MatchController matchController = MatchController.getInstance();
    	Match match = matchController.getMatchByID(Integer.parseInt(matchID));
    	match.addGuest(username);
    	
    	//Inform the host player that a guest has joined
    	WebSocket.Out<String> wsHost = hostsWaitingForGuest.get(new Integer(matchID));
    	if (wsHost == null) {
			//Game is not waiting for another player anymore
    		return redirect("/"); //Tell him to get back to homepage
		}
    	wsHost.write("OTHER PLAYER JOINED");
    	wsHost.close(); //Close WebSocket
    	hostsWaitingForGuest.remove(new Integer(matchID)); //Remove host from list of webSockets
    	
    	session("matchID", matchID); //Save the unique match ID to a session cookie.
    	session("isHost", "false"); //To identify whether the user is host or guest, we set this session cookie.
    	
    	return redirect("/placeShips");
    }
    
    public static Result placeShips(){
    	return ok(placeShips.render());
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
    
    // Websocket interface
    public static WebSocket<String> wsToInformAboutSecondPlayer(){
    	System.out.println("DEBUG: WebSocket wird erstellt");
    	System.out.println("DEBUG: matchID: " + session("matchID"));
        return new WebSocket<String>(){
            private Integer matchID = new Integer(session("matchID")); //"cache" here so it's available within the onReady method.
            // called when websocket handshake is done
            public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out){
            		System.out.println("DEBUG: WebSocket wurde erstellt");
            		
            		//Add the user which created this websocket to the lists of hosts waiting
                    hostsWaitingForGuest.put(matchID, out);
            }
        };   
    } 
    
    public static Result debug(){
    	return ok(debug.render(session("matchID"), session("isHost")));
    }
}
