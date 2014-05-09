package model.java;

import java.util.HashMap;

public class Matches {
	//BEGIN Singelton-Code
	static private Matches instance;
	static public Matches getInstance() {
		if (instance == null) {
			instance = new Matches();
		}
		return instance;
	}
	private Matches(){
		
	}
	//END Singelton-Code
	
	private int lastAddedUserID = 0;
	private HashMap<Integer, Map> currentGames = new HashMap<Integer, Map>();

	
}
