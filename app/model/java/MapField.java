package model.java;

import model.java.exceptions.FieldAlreadyFiredUponException;

public class MapField {
	
	/**
	 * Information the enemy has against the owner of this field.
	 */
	private enum Status{
		UNKNOWN, HIT, MISSED;
	}
	
	private Status status;
	private int posX;
	private int posY;
	private Ship ship; //set to "null" if no ship present on this field. (i.e. "status" is "status.WATER") 
	
	/**
	 * A MapField is single field on the map. (e.g. the one in the top left corner, called "A1" in the classic game)
	 * @param posX X-Coordinates of field. (Topleft is "0")
	 * @param posY Y-Coordinates of field. (Topleft is "0")
	 * @param ship If ship is present on this field, a ship object - otherwise "null"
	 */
	public MapField(int posY, int posX, Ship ship) {
		this.posY = posY;
		this.posX = posX;
		this.ship = ship;
		this.status = Status.UNKNOWN;
	}

	/**
	 * 
	 * @return the new Status of the field.
	 * @throws FieldAlreadyFiredUponException If this field has been fired upon already. (UI shouldn't allow this!)
	 */
	public Status fireOn() throws FieldAlreadyFiredUponException{
		if (status != Status.UNKNOWN) {
			if (ship == null){
				this.status = Status.MISSED;
			} else {
				this.status = Status.HIT;
			}
		} else {
			throw new FieldAlreadyFiredUponException();
		}
		return status;
	}
	
	public Ship getShip() {
		return ship;
	}
	public void setShip(Ship ship) {
		if (this.ship != null) {
			throw new RuntimeException("ERROR/WARNING: Trying to set the ship on a map field where another ship is already present");
		}
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
