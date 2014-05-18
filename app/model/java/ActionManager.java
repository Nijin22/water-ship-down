package model.java;

import model.java.exceptions.ActionAlreadyUsedException;

public class ActionManager {
	enum Action {
		FIRE_TWICE, ENEMY_PASSES, ALL_SHOTS_HIT, GET_HINT
	}
	
	private Map map;
	private boolean fireTwiceAvailable = true;
	private boolean enemyPassesAvailable = true;
	private boolean allShotsHitAvailable = true;
	private boolean getHintAvailable = true;
	
	public ActionManager(Map map) {
		this.map = map;
	}

	public Map getMap() {
		return map;
	}
	public boolean isFireTwiceAvailable() {
		return fireTwiceAvailable;
	}
	public boolean isEnemyPassesAvailable() {
		return enemyPassesAvailable;
	}
	public boolean isAllShotsHitAvailable() {
		return allShotsHitAvailable;
	}
	public boolean isGetHintAvailable() {
		return getHintAvailable;
	}
	public boolean isActionAvailable(Action action){
		switch (action) {
		case ALL_SHOTS_HIT:
			return isAllShotsHitAvailable();
		case ENEMY_PASSES:
			return isEnemyPassesAvailable();
		case FIRE_TWICE:
			return isFireTwiceAvailable();
		case GET_HINT:
			return isGetHintAvailable();
		default:
			//This should never happen (Unless the IDE of a developer is not set up correctly)
			throw new RuntimeException("Attempting to check if Action is available on a action which does not exist.");
		}
	}
	
	/**
	 * Sets the action as used or throws an ActionAlreadyUsedException if action was used already
	 * @param action the kind of action which should be used.
	 * @throws ActionAlreadyUsedException if action was used already
	 */
	public void useAction(Action action) throws ActionAlreadyUsedException{
		switch (action) {
		case FIRE_TWICE:
			if (fireTwiceAvailable) {
				fireTwiceAvailable = false;
			} else {
				throw new ActionAlreadyUsedException();
			}
			break;
		case ENEMY_PASSES:
			if (enemyPassesAvailable) {
				enemyPassesAvailable = false;
			} else {
				throw new ActionAlreadyUsedException();
			}
			break;
		case ALL_SHOTS_HIT:
			if (allShotsHitAvailable) {
				allShotsHitAvailable = false;
			} else {
				throw new ActionAlreadyUsedException();
			}
			break;
		case GET_HINT:
			if (getHintAvailable) {
				getHintAvailable = false;
			} else {
				throw new ActionAlreadyUsedException();
			}
			break;
		}
	}
}
