package model.java;

public class Match {
	private Map host;
	private Map guest;
	private RoundChoices choiceHost = null;
	private RoundChoices choiceGuest = null;
	
	/**
	 * 
	 * @param username Username of host
	 */
	public Match(String username){
		Map host = new Map(true, username, this); //is host
		this.host = host;
	}
	
	/**
	 * Collects new choices and executes them if choices by other player were already present. 
	 * @param choices the choices of the current player
	 * @param ChoiceByHost true, if the current player is the host
	 */
	public void collectChoices(RoundChoices choices, boolean ChoiceByHost){
		if (ChoiceByHost) {
			choiceHost = choices;
		} else {
			choiceGuest = choices;
		}
		if (choiceHost != null && choiceGuest != null) {
			host.executeChoices(choiceHost);
			guest.executeChoices(choiceGuest);
			//TODO: Check if game is over (and tell controller to display a result screen) or tell controller to refresh the map.
		}
	}
	
	public void addGuest(String username){
		Map guest = new Map(false, username, this); //is guest
		this.guest = guest;
		//TODO: Inform the controller that a guest joined the game
	}
	
	public boolean has2Players(){
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
	
	
}
