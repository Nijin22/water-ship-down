package model.java;

import java.util.Collection;
import java.util.HashMap;

public class MatchController {
	//BEGIN Singelton-Code, which makes sure this object exists only once while the server runs
	static private MatchController instance;
	static public MatchController getInstance() {
		if (instance == null) {
			instance = new MatchController();
		}
		return instance;
	}
	private MatchController(){
		
	}
	//END Singelton-Code
	
	private static HashMap<Integer, Match> currentMatches = new HashMap<Integer, Match>();
	private static int lastAddedMatchID = 0;
	private static Collection<Integer> getAllIDs(){
		return currentMatches.keySet();
	}
	
	public static int addMatch(Match match){
		lastAddedMatchID++;
		currentMatches.put(lastAddedMatchID, match);
		return lastAddedMatchID;
	}
	
	/**
	 * Iterates over all IDs, checks if the corresponding match already has 2 players and if not adds it to the HashMap which is returned 
	 * @return a HashMap consisting of MatchIDs and Matches which don't have 2 players.
	 */
	public HashMap<Integer, Match> getOpenMatches(){
		HashMap<Integer, Match> openMatches = new HashMap<Integer, Match>();
		Collection<Integer> allIDs = getAllIDs();
		for (Integer IteratedID : allIDs) {
			Match IteratedMatch = currentMatches.get(IteratedID);
			if (IteratedMatch.has2Players()==false){
				openMatches.put(IteratedID, IteratedMatch);
			}
		}
		return openMatches;
	}
	
}
