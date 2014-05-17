package model.java.exceptions;

public class ShipNotPlacableException extends WSDException {
	public enum Reason {
		INDEXOUTOFBOUNDS, COLIDINGWITHOTHERSHIP
	}
	private Reason reason;
	public ShipNotPlacableException(Reason reason) {
		this.reason = reason;
	}
	public Reason getReason() {
		return reason;
	}
	

}
