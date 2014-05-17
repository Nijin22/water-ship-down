package model.java;


public class Ship {
	enum Type {
		CARRIER, BATTLESHIP, DESTROYER, SUBMARINE
	}
	enum Orientation {
		HORIZONTAL, VERTICAL
	}
	/**
	 * Static method which requires no instance of a ship and provides the size of a ship (determined by it's type and orientation)
	 * Values from https://docs.google.com/document/d/1TOjhRAXVTmiKjDHiTCq09nFR02474PPGkzWHhnrpe7A/edit @ 2014-05-17 16:53 UTC+2
	 * @param type
	 * @param orientation
	 * @return
	 */
	public static int getSizeX(Type type, Orientation orientation){
		switch (type) {
		case CARRIER:
			if (orientation == Orientation.HORIZONTAL) {return 10;} else {return 2;}
		case BATTLESHIP:
			if (orientation == Orientation.HORIZONTAL) {return 5;} else {return 2;}
		case DESTROYER:
			if (orientation == Orientation.HORIZONTAL) {return 5;} else {return 1;}
		case SUBMARINE:
			if (orientation == Orientation.HORIZONTAL) {return 3;} else {return 1;}
		default:
			//Assuming the IDE is set up correctly this should never ever happen. ;)
			throw new RuntimeException("Attempting to getSizeX of a ship where the type is not carrier, battleship, destroyer or submarine");
		}
	}
	/**
	 * Static method which requires no instance of a ship and provides the size of a ship (determined by it's type and orientation)
	 * Values from https://docs.google.com/document/d/1TOjhRAXVTmiKjDHiTCq09nFR02474PPGkzWHhnrpe7A/edit @ 2014-05-17 16:53 UTC+2
	 * @param type
	 * @param orientation
	 * @return
	 */
	public static int getSizeY(Type type, Orientation orientation){
		switch (type) {
		case CARRIER:
			if (orientation == Orientation.HORIZONTAL) {return 2;} else {return 10;}
		case BATTLESHIP:
			if (orientation == Orientation.HORIZONTAL) {return 2;} else {return 5;}
		case DESTROYER:
			if (orientation == Orientation.HORIZONTAL) {return 1;} else {return 5;}
		case SUBMARINE:
			if (orientation == Orientation.HORIZONTAL) {return 1;} else {return 3;}
		default:
			//Assuming the IDE is set up correctly this should never ever happen. ;)
			throw new RuntimeException("Attempting to getSizeX of a ship where the type is not carrier, battleship, destroyer or submarine");
		}
	}
	
	
	private int PosX;
	private int posY;
	private Type type;
	private Map map;
	private Orientation orientation;

	
	public Ship(int posX, int posY, Type type, Map map, Orientation orientation) {
		PosX = posX;
		this.posY = posY;
		this.type = type;
		this.map = map;
		this.orientation = orientation;
	}
	public int getPosX() {
		return PosX;
	}
	public int getPosY() {
		return posY;
	}
	public Type getType() {
		return type;
	}
	public Map getMap(){
		return map;
	}
	public Orientation getOrientation() {
		return orientation;
	}
	
	//Healthpoints can be calculated by checking by checking which MapFields (with this ship) are still "Status.UNKNOWN"
}
