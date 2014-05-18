package model.java;

import model.java.ActionManager.Action;
import model.java.exceptions.ActionAlreadyUsedException;
import model.java.exceptions.NoUnshotShipOnMapException;

public class Match {
	private Map host;
	private Map guest;
	private RoundChoices choiceHost = null;
	private RoundChoices choiceGuest = null;

	/**
	 * 
	 * @param username
	 *            Username of host
	 */
	public Match(String username) {
		Map host = new Map(true, username, this); // is host
		this.host = host;
	}

	/**
	 * Collects new choices and executes them if choices by other player were
	 * already present.
	 * 
	 * @param choices
	 *            the choices of the current player
	 * @param ChoiceByHost
	 *            true, if the current player is the host
	 * @throws ActionAlreadyUsedException
	 *             if one choice contains an already used action
	 * @throws NoUnshotShipOnMapException 
	 */
	public void collectChoices(RoundChoices choices, boolean ChoiceByHost)
			throws ActionAlreadyUsedException, NoUnshotShipOnMapException {
		if (ChoiceByHost) {
			choiceHost = choices;
		} else {
			choiceGuest = choices;
		}
		if (choiceHost != null && choiceGuest != null) {
			executeChoices();
			// TODO: Check if game is over (and tell controller to display a
			// result screen and tell MatchController to delete this match!) or
			// tell controller to refresh the map. (Could possibly be done via
			// the return value of this?)
			choiceGuest = null;
			choiceHost = null;
		}
	}

	public void addGuest(String username) {
		Map guest = new Map(false, username, this); // is guest
		this.guest = guest;
		// TODO: Inform the controller that a guest joined the game
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

	/**
	 * Executes the choices the players made. Called when both player DID make their choices.
	 * @throws ActionAlreadyUsedException
	 * @throws NoUnshotShipOnMapException
	 */
	public void executeChoices() throws ActionAlreadyUsedException,	NoUnshotShipOnMapException {
		// Special Action ENEMY_PASSES
		boolean guestPasses = false;
		if (choiceHost.getActionUsedThisRound() == Action.ENEMY_PASSES) {
			host.setActionAsUsed(Action.ENEMY_PASSES);
			guestPasses = true;
		}
		boolean hostPasses = false;
		if (choiceGuest.getActionUsedThisRound() == Action.ENEMY_PASSES) {
			guest.setActionAsUsed(Action.ENEMY_PASSES);
			hostPasses = true;
		}

		// Special Action FIRE_TWICE
		int availableShotsHost = host.getAvailableShips();
		if (choiceHost.getActionUsedThisRound() == Action.FIRE_TWICE) {
			host.setActionAsUsed(Action.FIRE_TWICE);
		}
		int availableShotsGuest = guest.getAvailableShips();
		if (choiceGuest.getActionUsedThisRound() == Action.FIRE_TWICE) {
			guest.setActionAsUsed(Action.FIRE_TWICE);
		}
		

		// TODO: Implement targets being fired upon ( Make sure Passes booleans and availableShots are take into consideration!)
		// TODO: Implement special Actions ALL_SHOTS_HIT

		if (choiceHost.getActionUsedThisRound() == Action.GET_HINT) {
			host.setActionAsUsed(Action.GET_HINT);
			guest.doActionGetHint();
		}
		if (choiceGuest.getActionUsedThisRound() == Action.GET_HINT) {
			guest.setActionAsUsed(Action.GET_HINT);
			host.doActionGetHint();
		}
	}

}
