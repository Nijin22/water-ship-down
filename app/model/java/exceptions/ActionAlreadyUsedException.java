package model.java.exceptions;

public class ActionAlreadyUsedException extends WSDException {
	public boolean causedByHost; //false if caused by guest
	public ActionAlreadyUsedException(boolean causedByHost) {
		this.causedByHost = causedByHost;
	}

}
