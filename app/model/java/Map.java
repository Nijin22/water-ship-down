package model.java;

import model.java.exceptions.ShipAlreadyAddedException;
import model.java.exceptions.ShipNotPlacableException;

public class Map {
	private final int SIZE = 18; //Set in team meeting 2014-04-27 Probably better not to change...
	private boolean host; //True if host, false if guest
	private String name; //user name
	private Match match; //the match this map belongs to
	private ShipManager shipManager;
	
	private MapField[][] map = new MapField[SIZE][SIZE]; //First value is Y coordinates, second is X
	public Map(boolean host, String name, Match match){
		this.host = host;
		this.name = name;
		this.match = match;
		int counter_y = 0;
		int counter_x = 0;
		while (counter_y < SIZE){
			while (counter_x < SIZE){
				map[counter_y][counter_x] = new MapField(counter_y, counter_x, null);
				counter_x++;
			}
			counter_y++;
			counter_x = 0;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Match getMatch() {
		return match;
	}
	public boolean isHost(){
		return host;
	}
	public void addShip(Ship.Type type, int posX, int posY, Ship.Orientation orientation) throws ShipAlreadyAddedException, ShipNotPlacableException{
		if (posX < 0 || posY < 0 || Ship.getSizeX(type, orientation) > SIZE || Ship.getSizeY(type, orientation) > SIZE){
			//Ship would be outside of map if it is placed here
			throw new ShipNotPlacableException(ShipNotPlacableException.Reason.INDEXOUTOFBOUNDS);
		}
		
		//START Checking if ship would collide with another ship
		//START Reading boundaries of rectangle surrounding the possible ship location. (Determined in https://docs.google.com/document/d/1TOjhRAXVTmiKjDHiTCq09nFR02474PPGkzWHhnrpe7A 2014-05-17 17:23) (Each ship has to have at least a 1 field big distance to the next ship) (This code is a little complicated because the limit does not exist when the ship touches the corners of the map)
		int checkUntilY = Ship.getSizeY(type, orientation);
		if (checkUntilY < SIZE) {checkUntilY++;}
		int checkUntilX = Ship.getSizeX(type, orientation);
		if (checkUntilX < SIZE) {checkUntilX++;}
		int counter_Y = posY;
		if (counter_Y > 0){counter_Y--;}
		int counter_X = posX-1;
		if (counter_X > 0){counter_X--;}
		//END Reading boundaries
		
		while (counter_Y < checkUntilY) { //Iterating over map fields to check if a ship is already placed within boundaries
			while (counter_X < checkUntilX) {
				if (map[counter_Y][counter_X].getShip() != null) {
					//another ship is already placed in this location
					throw new ShipNotPlacableException(ShipNotPlacableException.Reason.COLIDINGWITHOTHERSHIP);
				}
				counter_X++;
			}
			counter_Y++;
		}
		//END Checking for other ships
		
		
		
		Ship newShip = shipManager.addShip(type, posX, posY, this, orientation); //Can throw a ShipAlreadyAddedException
		
		//setting the ship attribute of MapFields to the newly generated ship
		int c_Y = posY;
		int c_X = posX;
		while (c_Y < Ship.getSizeY(type, orientation)) {
			while (c_X < Ship.getSizeX(type, orientation)) {
				map[c_Y][c_X].setShip(newShip);
				c_X++;
			}
			c_Y++;
		}
	}

	
}
