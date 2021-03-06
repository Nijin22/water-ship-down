package model.java;

import java.util.Collection;

import model.java.exceptions.ActionAlreadyUsedException;
import model.java.exceptions.FieldAlreadyFiredUponException;
import model.java.exceptions.MoreCoordinatesThanAllowedException;

public class Match extends java.util.Observable {
	public enum WinnerOptions{
		NONE, HOST, GUEST
	}
	private boolean gameCheated = false;
	private boolean gameForfeited = false;
	private WinnerOptions winner = WinnerOptions.NONE;
	private int numberOfRounds = 0;
	private Map host;
	private Map guest;
	private int id;
	private String decisionHostAction = null;
	private Collection<Coordinate> decisionHostCoordinates = null;
	private String decisionGuestAction = null;
	private Collection<Coordinate> decisionGuestCoordinates = null;	
	

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
	public boolean isGameForfeited() {
		return gameForfeited;
	}
	public void markGameForfeited(boolean forfeitedByHost) {
		this.gameForfeited = true;
		if (forfeitedByHost) {
			winner=WinnerOptions.GUEST;
		} else {
			winner=WinnerOptions.HOST;
		}
		//Tell the Application that the turn is finished. (And tell it the matches id)
		setChanged();
		notifyObservers(id);
	}
	public Map getHost() {
		return host;
	}
	public boolean isGameCheated() {
		return gameCheated;
	}
	public Map getGuest() {
		return guest;
	}
	public int getId() {
		return id;
	}
	/**
	 * Collects the Decisions for a round and calculates the turn if decisions from both parties were submitted.
	 * @param isHost true if these are the decisions for the host
	 * @param action String representing the special action (or "NONE")
	 * @param coordinates Collection of coordinates
	 */
	public void collectDecisions(boolean isHost, String action, Collection<Coordinate> coordinates){
		//Save decisions
		if (isHost) {
			decisionHostAction = action;
			decisionHostCoordinates = coordinates;
		} else {
			decisionGuestAction = action;
			decisionGuestCoordinates = coordinates;
		}
		
		//Check if both decisions were made
		if (decisionGuestAction != null && decisionGuestCoordinates != null && decisionHostAction != null && decisionHostCoordinates != null) {
			//Both players made their decisions.
			
			try {
				calculateRound();
			} catch (MoreCoordinatesThanAllowedException e) {
				//Someone tried to cheat the game by submitting more targets than he is allowed to.
				if (e.causedByHost) {
					winner = WinnerOptions.GUEST;
				} else {
					winner = WinnerOptions.HOST;
				}
				gameCheated = true;
				
			} catch (ActionAlreadyUsedException e) {
				//Someone tried to cheat the game by submitting an action which he already used earlier.
				if (e.causedByHost) {
					winner = WinnerOptions.GUEST;
				} else {
					winner = WinnerOptions.HOST;
				}
				gameCheated = true;
				
			}
			
			if (getHost().getNumberShipsAlive() < 1) {
				winner = WinnerOptions.GUEST;
			}
			if (getGuest().getNumberShipsAlive() < 1) {
				winner = WinnerOptions.HOST;
			}
			
			//Tell the Application that the turn is finished. (And tell it the matches id)
			setChanged();
			notifyObservers(id);
			
			//Set choices to null because round was calculated successful.
			decisionGuestAction = null;
			decisionGuestCoordinates = null;
			decisionHostAction = null;
			decisionHostCoordinates = null;
		}
	}
	/**
	 * Calculates the decisions for this turn, throws exceptions should disallowed decisions been sent
	 * @throws MoreCoordinatesThanAllowedException
	 * @throws ActionAlreadyUsedException
	 */
	private void calculateRound() throws MoreCoordinatesThanAllowedException, ActionAlreadyUsedException{
		numberOfRounds++;
		// Check if players use actions which they already used
		switch (decisionGuestAction) {
		case "FIRE_TWICE":
			if (getGuest().sa_fire_twice == false) {
				throw new ActionAlreadyUsedException(false);
			}
			break;
		case "THREE_BONUS_SHOTS":
			if (getGuest().sa_three_bonus_shots == false) {
				throw new ActionAlreadyUsedException(false);
			}
			break;
		case "ENEMY_PASSES":
			if (getGuest().sa_enemy_passes == false) {
				throw new ActionAlreadyUsedException(false);
			}
			break;
		case "AUTO_ROCKET":
			if (getGuest().sa_auto_rocket == false) {
				throw new ActionAlreadyUsedException(false);
			}
			break;
		}
		switch (decisionHostAction) {
		case "FIRE_TWICE":
			if (getHost().sa_fire_twice == false) {
				throw new ActionAlreadyUsedException(true);
			}
			break;
		case "THREE_BONUS_SHOTS":
			if (getHost().sa_three_bonus_shots == false) {
				throw new ActionAlreadyUsedException(true);
			}
			break;
		case "ENEMY_PASSES":
			if (getHost().sa_enemy_passes == false) {
				throw new ActionAlreadyUsedException(true);
			}
			break;
		case "AUTO_ROCKET":
			if (getHost().sa_auto_rocket == false) {
				throw new ActionAlreadyUsedException(true);
			}
			break;
		}
		
		//calculating max shots
		int maxShotsHost = getHost().getNumberShipsAlive();
		int maxShotsGuest = getGuest().getNumberShipsAlive();
		if (decisionHostAction.equals("FIRE_TWICE")) {
			getHost().sa_fire_twice = false;
			maxShotsHost = maxShotsHost*2;
		}
		if (decisionGuestAction.equals("FIRE_TWICE")) {
			getGuest().sa_fire_twice = false;
			maxShotsGuest = maxShotsHost*2;
		}
		if (decisionHostAction.equals("THREE_BONUS_SHOTS")) {
			getHost().sa_three_bonus_shots = false;
			maxShotsHost += 3;
		}
		if (decisionGuestAction.equals("THREE_BONUS_SHOTS")) {
			getGuest().sa_three_bonus_shots = false;
			maxShotsGuest += 3;
		}
		
		//Check if coordinates are within the limits
		if (decisionHostCoordinates.size() > maxShotsHost) {
			throw new MoreCoordinatesThanAllowedException(true);
		}
		if (decisionGuestCoordinates.size() > maxShotsGuest){
			throw new MoreCoordinatesThanAllowedException(false);
		}
		
		//Fire on guest map
		if (decisionGuestAction.equals("ENEMY_PASSES")) {
			getGuest().sa_enemy_passes = false;
			//nothing to do
		} else {
			for (Coordinate coord : decisionHostCoordinates) {
				MapField mapField = getGuest().getMapField(coord.y, coord.x);
				try {
					mapField.fireUpon();
				} catch (FieldAlreadyFiredUponException e) {
					// Due to the architecture we do no longer need to handle this specific case.
				}
			}
		}
		
		//Fire on host map
		if (decisionHostAction.equals("ENEMY_PASSES")) {
			getHost().sa_enemy_passes = false;
			//nothing to do
		} else {
			for (Coordinate coord : decisionGuestCoordinates) {
				MapField mapField = getHost().getMapField(coord.y, coord.x);
				try {
					mapField.fireUpon();
				} catch (FieldAlreadyFiredUponException e) {
					// Due to the architecture we do no longer need to handle this specific case.
				}
			}
		}
		
		//Auto-Rocket Handling
		if (decisionHostAction.equals("AUTO_ROCKET")) {
			getHost().sa_auto_rocket = false;
			try {
				if(getGuest().findFirstShipPosition() != null){
					getGuest().findFirstShipPosition().fireUpon();
				}
			} catch (FieldAlreadyFiredUponException e) {
				throw new RuntimeException("Due to how findFirstShipPosition() works, this case should never happen.");
			}
			
		}
		if (decisionGuestAction.equals("AUTO_ROCKET")) {
			getGuest().sa_auto_rocket = false;
			try {
				if(getHost().findFirstShipPosition() != null){
					getHost().findFirstShipPosition().fireUpon();
				}
			} catch (FieldAlreadyFiredUponException e) {
				throw new RuntimeException("Due to how findFirstShipPosition() works, this case should never happen.");
			}
			
		}

	}
	public WinnerOptions getWinner() {
		return winner;
	}
	public int getNumberOfRounds() {
		return numberOfRounds;
	}

}
