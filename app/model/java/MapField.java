package model.java;

public class MapField {
	
	/**
	 * Information the enemy has against the owner of this field.
	 */
	public enum Status{
		UNKNOWN, HIT, MISSED
	}
	
	private Status status;
	private int posX;
	private Ship ship = null; //null if no  ship is present
	private int posY;

	/**
	 * A MapField is single field on the map. (e.g. the one in the top left corner, called "A1" in the classic game)
	 * @param posX X-Coordinates of field. (Topleft is "0")
	 * @param posY Y-Coordinates of field. (Topleft is "0")
	 * @param ship If ship is present on this field, a ship object - otherwise "null"
	 */
	public MapField(int posY, int posX) {
		this.posY = posY;
		this.posX = posX;
		this.status = Status.UNKNOWN;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
