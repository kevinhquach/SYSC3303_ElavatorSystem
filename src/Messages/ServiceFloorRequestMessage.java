/**
 * 
 */
package Messages;

/**
 * @author Harjap Gill
 *
 */
public class ServiceFloorRequestMessage extends Message {
	private static final long serialVersionUID = 1L;
	private static int idCounter = 0;
	private int requestID;
	private int floorNumber;
	private int destinationNumber;
	private String direction;
	private int elevatorId;
	
	/**
	 * @param floorNumber
	 * @param destinationNumber
	 * @param direction
	 * @param elevatorId
	 */
	public ServiceFloorRequestMessage(int floorNumber, int destinationNumber, String direction, int elevatorId) {
		super();
		this.requestID = idCounter++;
		this.floorNumber = floorNumber;
		this.destinationNumber = destinationNumber;
		this.direction = direction;
		this.elevatorId = elevatorId;
	}
	
	@Override
	public MessageTypes getMessageType() {
		return MessageTypes.SERVICE_FLOOR_REQUEST_MESSAGE;
	}
	
	/**
	 * @return this request's ID
	 */
	public int getRequestID() {
		return requestID;
	}

	/**
	 * @return the floorNumber
	 */
	public int getFloorNumber() {
		return floorNumber;
	}

	/**
	 * @param floorNumber the floorNumber to set
	 */
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	/**
	 * @return the destinationNumber
	 */
	public int getDestinationNumber() {
		return destinationNumber;
	}

	/**
	 * @param destinationNumber the destinationNumber to set
	 */
	public void setDestinationNumber(int destinationNumber) {
		this.destinationNumber = destinationNumber;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return the elevatorId
	 */
	public int getElevatorId() {
		return elevatorId;
	}

	/**
	 * @param elevatorId the elevatorId to set
	 */
	public void setElevatorId(int elevatorId) {
		this.elevatorId = elevatorId;
	}
	
	public String toString() {
		return "Request id: " + this.requestID + " to elevator " + this.elevatorId + " src floor: " + this.floorNumber +
				" destination floor: " + this.destinationNumber + " direction: " + this.direction;
	}
}
