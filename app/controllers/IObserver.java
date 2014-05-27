package controllers;

public interface IObserver {
	public enum WHAT_TO_UPDATE{
		OTHER_PLAYER_JOINED, ROUND_IS_FINISHED, GAME_IS_FINISHED
	}
	public void update(WHAT_TO_UPDATE wtu);
}
