package model.java.exceptions;

public class MoreCoordinatesThanAllowedException extends WSDException {
	public boolean causedByHost; //false if caused by guest
	public MoreCoordinatesThanAllowedException(boolean causedByHost){
		this.causedByHost = causedByHost;
	}
}
