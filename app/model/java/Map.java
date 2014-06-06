package model.java;

import java.util.Random;

import model.java.MapField.Status;
import model.java.exceptions.ActionAlreadyUsedException;
import model.java.exceptions.NoUnshotShipOnMapException;
import model.java.exceptions.ShipAlreadyAddedException;
import model.java.exceptions.ShipNotPlacableException;

public class Map {
	private final int SIZE = 18; //Set in team meeting 2014-04-27 Probably better not to change...
	private boolean host; //True if host, false if guest
	private String name; //user name
	private Match match; //the match this map belongs to
	private ShipManager shipManager;
	private ActionManager actionManager; 
	private Coordinate hint = null; // Coordinate of topLeft of "hint-rectangle"
	private MapField[][] map = new MapField[SIZE][SIZE]; //First value is Y coordinates, second is X
	
	/**
	 * Returns the topleft of the first detected ship (which hasn't been fired upon yet!), useful for the GET_HINT special action
	 * @return topleft of first detected ship
	 * @throws NoUnshotShipOnMapException if someone calls this method while no ships are present on the map anymore (that's the game over condition!)
	 */
	private Coordinate findFirstShip() throws NoUnshotShipOnMapException{
		int cY = 0, cX = 0;
		while (cY < SIZE) {
			while (cX < SIZE) {
				if ((map[cY][cX].getShip()!=null) && (map[cY][cX].getStatus()==Status.UNKNOWN)) {
					//Found a field with a ship which hasn't been fired upon (UNKNOWN status)
					return new Coordinate(cY, cX);
				}
				cX++;
			}
			cY++;
		}
		//Should not happen unless someone calls this method while no ships are present on the map anymore (that's the game over condition!)
		throw new NoUnshotShipOnMapException();
	}
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * CODE TAKEN FROM: http://stackoverflow.com/a/363692/3298787
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	private static int randInt(int min, int max) {

	    // Usually this should be a field rather than a method variable so
	    // that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	/**
	 * Moves a coordinate by a random number (up to 2) to the top and left, unless X or Y would become negative.
	 * Makes sure the maximum of the new X and Y are Map.SIZE -2 (So the rectangle stays within the maps borders)
	 * Useful to randomize the radar rectangle 
	 * 
	 * @param coordinate the coordinate you want randomized
	 * @return the randomized coordinate
	 */
	private Coordinate randomizeCoordinateTopLeft(Coordinate coordinate){
		//Moves a coordinate by a random number (up to 2) to the top and left, unless X or Y would become negative.
		if (coordinate.y > 1) {
			coordinate.y = coordinate.y-randInt(0, 1);
		} else if (coordinate.y > 2) {
			coordinate.y = coordinate.y-randInt(0, 2);
		}
		if (coordinate.x > 1) {
			coordinate.x = coordinate.x-randInt(0, 1);
		} else if (coordinate.y > 2) {
			coordinate.x = coordinate.x-randInt(0, 2);
		}
		
		//Makes sure the maximum of the new X and Y are Map.SIZE -2
		if (coordinate.y > SIZE-2) {
			coordinate.y = SIZE-2;
		}
		if (coordinate.x > SIZE-2) {
			coordinate.x = SIZE-2;
		}
		
		
		return coordinate;
	}
	
	public Map(boolean host, String name, Match match){
		this.host = host;
		this.name = name;
		this.match = match;
		actionManager = new ActionManager(this);
		
		//START creating map and setting the ships on it to "null"
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
		
		//TODO:
		//Diese Methode verursacht Fehler und sollte wohl von Grund auf neu geschrieben werden.
		
		
		System.out.println("DEBUG: X = " + posX);
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
		System.out.println("DEBUG: X = " + posX);
		while (counter_Y < checkUntilY) { //Iterating over map fields to check if a ship is already placed within boundaries
			while (counter_X < checkUntilX) {
				System.out.println("DEBUG: Y: " + counter_Y + " X: " + counter_X);
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
	public MapField.Status getStatus(int x, int y){
		return map[x][y].getStatus();
	}
	public int getAvailableShips(){
		return shipManager.getAvailableShips();
	}
	/**
	 * 
	 * @return The coordinates of the hint topleft (if already reqeusted) or null (if hint wasn't requested yet)
	 */
	public Coordinate getHint(){
		return hint;
	}
	/**
	 * Call this method if the enemy uses the GET_HINT special action
	 * @return the coordinates of the hint topleft
	 * @throws NoUnshotShipOnMapException 
	 */
	public Coordinate doActionGetHint() throws NoUnshotShipOnMapException{
		//Call findfirstship
		hint = findFirstShip();
		
		//randomize the topleft of the radar
		hint = randomizeCoordinateTopLeft(hint);
		
		return hint;
	}
	public void setActionAsUsed(ActionManager.Action action) throws ActionAlreadyUsedException{
		actionManager.useAction(action);
	}
}
