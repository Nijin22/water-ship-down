package controllers;

import model.java.*;
import model.java.exceptions.ShipNotPlacableException;

import java.util.HashMap;
import play.mvc.*;
import views.html.*;

public class Application extends Controller{
	private static HashMap<Integer, WebSocket.Out<String>> hostsWaitingForGuest = new HashMap<Integer, WebSocket.Out<String>>();
    
    
    public static Result start() {
    	return ok(start.render());
    }
    
    public static Result refreshAvailableGames() {
    	MatchController matchController = MatchController.getInstance();
    	HashMap<Integer, Match> openMatches = matchController.getOpenMatches();
    	//System.out.println(openMatches.toString());
        
        String result = "[";
        
        boolean firstTime = true;
        for(Integer key : openMatches.keySet()){
          result += "{\"key\" : " + key + ", \"hostname\" : " + "\"" + openMatches.get(key).getHost().getName() + "\""+ "},";
        }
        
        // TODO: specify last element - "," Problem
        result += "{\"key\" : 0 , \"hostname\" : 0}]";
        
        System.out.print(result);
        
    	return ok(result);
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
    	wsHost.write(username);
    	wsHost.close(); //Close WebSocket
    	hostsWaitingForGuest.remove(new Integer(matchID)); //Remove host from list of webSockets
    	
    	session("matchID", matchID); //Save the unique match ID to a session cookie.
    	session("isHost", "false"); //To identify whether the user is host or guest, we set this session cookie.
        
        Map host = match.getHost();
        String hostname = host.getName();
        
        
    	return redirect("/placeShips");
    }
    
    public static Result placeShips(){
    	MatchController matchController = MatchController.getInstance();
  		Match match = matchController.getMatchByID(Integer.parseInt(session("matchID")));
  		String user1;
  		String user2;
  		if (session("isHost").equals("true")) {
			user1 = match.getHost().getName();
			user2 = match.getGuest().getName();
		} else {
			user2 = match.getHost().getName();
			user1 = match.getGuest().getName();
		}
    	return ok(placeShips.render(user1,user2));
    }
    
    // TODO!!! validate positions!
    public static Result validateShipPosition(String shipType, String x, String y, String string_orientation){
    	MatchController matchController = MatchController.getInstance();
  		Match match = matchController.getMatchByID(Integer.parseInt(session("matchID")));
  		Map map;
  		if (session("isHost").equals("true")) {
			map = match.getHost();
		} else {
			map = match.getGuest();
		}
  		
  		//parse type
  		Ship.type type = null;
  		switch (shipType) {
  			case "carrier":
  				type = type.CARRIER;
  				break;
  			case "battleship":
  				type = type.BATTLESHIP;
  				break;
  			case "destroyer1":
  				type = type.DESTROYER1;
  				break;
  			case "destroyer2":
  				type = type.DESTROYER2;
  				break;
  			case "submarine1":
  				type = type.SUB1;
  				break;
  			default:
  				type = type.SUB2;
  				break;
  				
  		}
  		
  		//parse orientation
  		Ship.orientation orientation = null;
  		switch (string_orientation) {
		case "h":
			orientation = orientation.HORIZONTAL;
			System.out.println("HORIZONTAL!!!!");
			break;
		default:
			orientation = orientation.VERTICAL;
			break;
		}
  		
  		try {
			map.placeShips(type, Integer.parseInt(y)-1, Integer.parseInt(x)-1, orientation); //"-1" because the client starts counting at 1|1, the map at 0|0
		} catch (ShipNotPlacableException e) {
			return ok("false");
		}
     
      return ok("true");
    }
    
    public static Result playing(){
        MatchController matchController = MatchController.getInstance();
  		Match match = matchController.getMatchByID(Integer.parseInt(session("matchID")));
  		String user1;
  		String user2;
  		if (session("isHost").equals("true")) {
			user1 = match.getHost().getName();
			user2 = match.getGuest().getName();
		} else {
			user2 = match.getHost().getName();
			user1 = match.getGuest().getName();
		}
    	return ok(playing.render(user1,user2));
    }
   
    public static Result loadMaps(){
    	MatchController matchController = MatchController.getInstance();
  		Match match = matchController.getMatchByID(Integer.parseInt(session("matchID")));
  		Map myMap;
  		Map enemyMap;
  		if (session("isHost").equals("true")) {
  			myMap = match.getHost();
  			enemyMap = match.getGuest();
		} else {
			enemyMap = match.getHost();
			myMap = match.getGuest();
		}
  		int myShotsBase = myMap.getNumberShipsAlive();
  		
  		//Iterate over friendly fields
  		String jsonFields = "";
  		int y = 0;
  		while (y < myMap.getSize()) {
			int x = 0;
			while (x < myMap.getSize()) {
				//Now we have a single map field.
				MapField field = myMap.getMapField(y, x);
				String hasShip;
				if (field.getShip()==null) {
					hasShip = "false";
				} else {
					hasShip = "true";
				}
				jsonFields += "{"+
						"\"y\":"+y+","+
						"\"x\":"+x+","+
						"\"status\":\""+field.getStatus().toString()+"\","+
						"\"ship\":"+hasShip+""+
						"},";
				x++;
			}
			y++;
		}
  		jsonFields = jsonFields.substring(0, jsonFields.length() - 1);
  		String jsonMyMap = "\"myMap\":{\"fields\":["+jsonFields+"]}";
  		
  		//Iterate over enemy fields
  		jsonFields = "";
  		y = 0;
  		while (y < myMap.getSize()) {
			int x = 0;
			while (x < myMap.getSize()) {
				//Now we have a single map field.
				MapField field = myMap.getMapField(y, x);
				jsonFields += "{"+
						"\"y\":"+y+","+
						"\"x\":"+x+","+
						"\"status\":\""+field.getStatus().toString()+"\""+
						"},";
				x++;
			}
			y++;
		}
  		jsonFields = jsonFields.substring(0, jsonFields.length() - 1);
  		String jsonEnemyMap = "\"enemyMap\":{\"fields\":["+jsonFields+"]}";  		
  		
  		//TODO: jsonSpecialActions füllen - ist momentan noch Platzhalter
  		String jsonSpecialActions = "\"mySpecialActions\":{\"FIRE_TWICE\":"+myMap.sa_fire_twice+",\"THREE_BONUS_SHOTS\":"+myMap.sa_three_bonus_shots+",\"AUTO_ROCKET\":"+myMap.sa_auto_rocket+",\"ENEMY_PASSES\":"+myMap.sa_enemy_passes+"}";
  		
  		String json = "{"+jsonMyMap+","+jsonEnemyMap+","+jsonSpecialActions+","+"\"myShotsBase\":"+Integer.toString(myShotsBase)+"}";
  		return ok(json);
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
