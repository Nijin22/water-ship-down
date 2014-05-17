package model.java;

import java.util.Set;

import model.java.ActionManager.Action;

/**
 * This class "collects" choices the player wants to do this round. It can be used by a Match to store the choices.
 * @author Dennis Weber
 *
 */
public class RoundChoices {
	private ActionManager.Action actionUsedThisRound = null; //can be null if no action is used this round
	private Set<Coordinate> targetsThisRound;

	public RoundChoices(Action actionUsedThisRound, Set<Coordinate> targetsThisRound) {
		this.actionUsedThisRound = actionUsedThisRound;
		this.targetsThisRound = targetsThisRound;
	}

	public ActionManager.Action getActionUsedThisRound() {
		return actionUsedThisRound;
	}

	public Set<Coordinate> getTargetsThisRound() {
		return targetsThisRound;
	}


}
