package model.java;

import model.java.exceptions.ActionAlreadyUsedException;
import model.java.exceptions.NoUnshotShipOnMapException;

public class Match {
	private Map host;
	private Map guest;
	private int id;

	/**
	 * 
	 * @param username
	 *            Username of host
	 */
	public Match(String username, int id) {
		this.id = id;
		Map host = new Map(true, username, this); // is host
		this.host = host;
		
	}
	public void addGuest(String username) {
		Map guest = new Map(false, username, this); // is guest
		this.guest = guest;
	}

	public boolean has2Players() {
		if (guest == null) {
			return false;
		} else {
			return true;
		}
	}

	public Map getHost() {
		return host;
	}

	public Map getGuest() {
		return guest;
	}

	public int getId() {
		return id;
	}

}
