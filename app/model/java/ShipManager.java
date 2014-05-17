package model.java;

import model.java.Ship.Orientation;
import model.java.exceptions.ShipAlreadyAddedException;

/**
 * Used to manage the different ships for a Map
 * 
 * @author Dennis Weber
 * 
 */
public class ShipManager {



	private Ship carrier = null;
	private Ship battleShip = null;
	private Ship destroyer1 = null;
	private Ship destroyer2 = null;
	private Ship sub1 = null;
	private Ship sub2 = null;

	public ShipManager() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Will attempt to add a new Ship to the correct place in the ShipManager (correct place determined by provided Type)
	 * @param type Type of ship, e.g. CARRIER
	 * @param posX value is passed to the ship instance
	 * @param posY value is passed to the ship instance
	 * @param map value is passed to the ship instance
	 * @param orientation value is passed to the ship instance
	 * @return the newly generated ship
	 * @throws ShipAlreadyAddedException if this space is already blocked.
	 */
	public Ship addShip(Ship.Type type, int posX, int posY, Map map, Orientation orientation)
			throws ShipAlreadyAddedException {
		Ship newShip = new Ship(posX, posY, type, map, orientation);
		switch (type) {
		case CARRIER:
			if (carrier == null) {
				carrier = newShip;
			} else {
				throw new ShipAlreadyAddedException();
			}
			break;
		case BATTLESHIP:
			if (battleShip == null) {
				battleShip = newShip;
			} else {
				throw new ShipAlreadyAddedException();
			}
			break;
		case DESTROYER:
			if (destroyer1 == null) {
				destroyer1 = newShip;
			} else {
				if (destroyer2 == null) {
					destroyer2 = newShip;
				} else {
					throw new ShipAlreadyAddedException();
				}
			}
			break;
		case SUBMARINE:
			if (sub1 == null) {
				sub1 = newShip;
			} else {
				if (sub2 == null) {
					sub2 = newShip;
				} else {
					throw new ShipAlreadyAddedException();
				}
			}
			break;
		}
		return newShip;
	}

}
