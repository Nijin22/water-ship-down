package model.java;

public class Ship {
	public enum type{
		CARRIER, BATTLESHIP, DESTROYER1, DESTROYER2, SUB1, SUB2
	}
	public enum orientation{
		HORIZONTAL, VERTICAL
	}
	
	/**
	 * Static method which requires no instance of a ship and provides the size of a ship (determined by it's type and orientation)
	 * Values from https://docs.google.com/document/d/1TOjhRAXVTmiKjDHiTCq09nFR02474PPGkzWHhnrpe7A/edit @ 2014-05-17 16:53 UTC+2
	 * @param type
	 * @param orientation
	 * @return
	 */
	public static int getSizeX(type type, orientation orientation){
		switch (type) {
		case CARRIER:
			if (orientation == orientation.HORIZONTAL) {return 10;} else {return 2;}
		case BATTLESHIP:
			if (orientation == orientation.HORIZONTAL) {return 5;} else {return 2;}
		case DESTROYER1:
			if (orientation == orientation.HORIZONTAL) {return 5;} else {return 1;}
		case DESTROYER2:
			if (orientation == orientation.HORIZONTAL) {return 5;} else {return 1;}
		case SUB1:
			if (orientation == orientation.HORIZONTAL) {return 3;} else {return 1;}
		case SUB2:
			if (orientation == orientation.HORIZONTAL) {return 3;} else {return 1;}
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
	public static int getSizeY(type type, orientation orientation){
		switch (type) {
		case CARRIER:
			if (orientation == orientation.HORIZONTAL) {return 2;} else {return 10;}
		case BATTLESHIP:
			if (orientation == orientation.HORIZONTAL) {return 2;} else {return 5;}
		case DESTROYER1:
			if (orientation == orientation.HORIZONTAL) {return 1;} else {return 5;}
		case DESTROYER2:
			if (orientation == orientation.HORIZONTAL) {return 1;} else {return 5;}
		case SUB1:
			if (orientation == orientation.HORIZONTAL) {return 1;} else {return 3;}
		case SUB2:
			if (orientation == orientation.HORIZONTAL) {return 1;} else {return 3;}
		default:
			//Assuming the IDE is set up correctly this should never ever happen. ;)
			throw new RuntimeException("Attempting to getSizeX of a ship where the type is not carrier, battleship, destroyer or submarine");
		}
	}
	
	private type type;
	private MapField topLeft;
	private orientation orientation;
	
	public Ship(type type, MapField topLeft, orientation o){
		this.type = type;
		this.topLeft = topLeft;
		this.orientation = o;
	}
	public MapField getTopLeft() {
		return topLeft;
	}
	
}
