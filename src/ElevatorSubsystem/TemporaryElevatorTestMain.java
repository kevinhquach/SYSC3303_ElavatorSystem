/**
 * 
 */
package ElevatorSubsystem;
import java.io.IOException;
import java.util.ArrayList;

import Messages.Message;
import Messages.ServiceFloorRequestMessage;
import SharedResources.*;

/**
 * @author Harjap Gill
 *
 */
public class TemporaryElevatorTestMain {

	/**
	 * 
	 */
	public TemporaryElevatorTestMain() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		Elevator elevator1 = new Elevator(1, false);
		Elevator elevator2 = new Elevator(2, false);
		Elevator elevator3 = new Elevator(3, false);

		ArrayList<Elevator> elevators = new ArrayList<Elevator>();
	
		elevators.add(elevator1);
		elevators.add(elevator2);
		elevators.add(elevator3);
		
		Buffer getBuffer = new Buffer();
		Buffer putBuffer = new Buffer();
		
		Thread elevatorSystem = new Thread(new ElevatorSystem(getBuffer, putBuffer, elevators));
		elevatorSystem.start();
		
		
		
		// Test
		ServiceFloorRequestMessage msg = new ServiceFloorRequestMessage(5, 10, "Up", 2);
		try {
			byte[] msgBytes = SerializeUtils.serialize(msg);
			getBuffer.put(msgBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean empty = putBuffer.checkEmpty();
		byte[] confirmationMsg = putBuffer.get();
		Message confirmation;
		try {
			confirmation = SerializeUtils.deserialize(confirmationMsg);
			System.out.println(confirmation.getMessageType());
			System.out.println(empty);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
