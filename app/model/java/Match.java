package model.java;

public class Match {
	private Map host;
	private Map guest;
	
	
	/**
	 * 
	 * @param username Username of host
	 */
	public Match(String username){
		Map host = new Map(true, username, this); //is host
		this.host = host;
	}
	
	public void addGuest(String username){
		Map guest = new Map(false, username, this); //is guest
		this.guest = guest;
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
