package controllers;

import java.util.Observable;

import model.java.Match;
import play.mvc.WebSocket;

public class MatchObserver implements java.util.Observer {
	public WebSocket.Out<String> wsHost;
	public WebSocket.Out<String> wsGuest;
	public Match match;

	
	
	public MatchObserver(Match match) {
		this.match = match;
		match.addObserver(this);
	}



	@Override
	/**
	 * Looks trough the active players until it has host & guest of the argument. Informs them about the status of their match.
	 */
	public void update(Observable o, Object arg) {
		if (match.getWinner() == Match.WinnerOptions.NONE) {
			// match still goes on
			wsHost.write("refresh");
			wsGuest.write("refresh");
		} else {
			wsHost.write("result");
			wsGuest.write("result");
			wsHost.close();
			wsGuest.close();
		}
	}
}
