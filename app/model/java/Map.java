package model.java;


import model.java.Ship.type;
import model.java.exceptions.ShipNotPlacableException;
import model.java.exceptions.ShipNotPlacableException.Reason;

public class Map {
	private final int SIZE = 18; //Set in team meeting 2014-04-27 Probably better not to change...
	private boolean host; //True if host, false if guest
	private String name; //user name
	private Match match; //the match this map belongs to
	private MapField[][] map = new MapField[SIZE][SIZE]; //First value is Y coordinates, second is X
	
	private Ship carrier = null;
	private Ship battleship = null;
	private Ship destroyer1 = null;
	private Ship destroyer2 = null;
	private Ship sub1 = null;
	private Ship sub2 = null;
	
	public Ship getShipByType(type type){
		switch (type) {
		case CARRIER:
			return carrier;
		case BATTLESHIP:
			return battleship;
		case DESTROYER1:
			return destroyer1;
		case DESTROYER2:
			return destroyer2;
		case SUB1:
			return sub1;
		case SUB2:
			return sub2;
		default:
			//should never happen unless we only recompile the enums without this class
			throw new RuntimeException("Error within getShipByType - you requested a type which does not exist");
		}
	}
	public Map(boolean host, String name, Match match){
		this.host = host;
		this.name = name;
		this.match = match;
		
		//START creating map and setting the ships on it to "null"
		int counter_y = 0;
		int counter_x = 0;
		while (counter_y < SIZE){
			while (counter_x < SIZE){
				map[counter_y][counter_x] = new MapField(counter_y, counter_x);
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
	public MapField getMapField(int y, int x){
		return map[y][x];
	}
	public boolean isHost(){
		return host;
	}
	public MapField.Status getStatus(int x, int y){
		return map[x][y].getStatus();
	}
	/**
	 * Tries to place a new ship or replaces the old ship if it has been placed already
	 * @param type
	 * @param posY
	 * @param posX
	 * @param orientation
	 * @throws ShipNotPlacableException If the new/replaced ship would collide with map borders or the borders of an already placed ship
	 */
	public void placeShips(type type, int posY, int posX, Ship.orientation orientation) throws ShipNotPlacableException{
		//check if posY & posX within map borders
		if (posY < 0 || posX < 0) {
			throw new ShipNotPlacableException(Reason.INDEXOUTOFBOUNDS);
		}
		int bottomRightY = posY + Ship.getSizeY(type, orientation)-1; //-1 because our index starts with 0
		int bottomRightX = posX + Ship.getSizeX(type, orientation)-1; //-1 because our index starts with 0
		if (bottomRightX > SIZE-1 || bottomRightY > SIZE-1){
			System.out.println("Placing @ (Y|X)" + posY + "|" + posX + " -------- bottomRightY: " + bottomRightY + " bottomRightX " + bottomRightX);
			System.out.println("Orientation: " + orientation);
			throw new ShipNotPlacableException(Reason.INDEXOUTOFBOUNDS);
		}
		
		//TODO: Test for collission
		
		
		//Place the ship
		MapField topLeft = getMapField(posY, posX);
		switch (type) {
		case BATTLESHIP:
			battleship = new Ship(type.BATTLESHIP, topLeft, orientation);
			break;
		case CARRIER:
			carrier = new Ship(type.CARRIER, topLeft, orientation);
			break;
		case DESTROYER1:
			destroyer1 = new Ship(type.DESTROYER1, topLeft, orientation);
			break;
		case DESTROYER2:
			destroyer2 = new Ship(type.DESTROYER2, topLeft, orientation);
			break;
		case SUB1:
			sub1 = new Ship(type.SUB1, topLeft, orientation);
			break;
		case SUB2:
			sub2 = new Ship(type.SUB2, topLeft, orientation);
			break;
		default:
			throw new RuntimeException("Attempting to place a ship with a non-existed enum");
		}
		
		//set the appropriate MapFields
		System.out.println();
		System.out.println();
		int c_y = posY;
		while (c_y <= bottomRightY) {
			int c_x = posX;
			while (c_x <= bottomRightX) {
				getMapField(c_y, c_x).setShip(getShipByType(type));
				System.out.print((c_y+"|"+c_x+"- -"));
				c_x++;
			}
			System.out.println();
			c_y++;
		}
		
	}
}
