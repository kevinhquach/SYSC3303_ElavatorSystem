package ElevatorSubsystem;

import java.io.IOException;
import java.util.ArrayList;

import SchedulerSubsystem.SchedulerDataGramCommunicator;
import SharedResources.*;
import Messages.*;

/**
 * @author Michael Quach, Harjap Gill, Jason Gao
 * 
 * Receives messages from the Scheduler. Based on the message type, determines the correct
 * interaction with the corresponding elevator and returns any confirmation messages back to
 * the scheduler
 *
 */
public class ElevatorSystem implements Runnable {
	private ByteBufferCommunicator bufferCommunicator;
	private ArrayList<Elevator> elevators;

	/**
	 * Constructs an ElevatorSystem.
	 * @param bufferCommunicator An instance of a buffer communicator object
	 * @param communicator a reference to the Scheduler's communicator
	 */
	public ElevatorSystem(ByteBufferCommunicator bufferCommunicator, ArrayList<Elevator> elevators) {
		this.elevators = elevators;
		this.bufferCommunicator = bufferCommunicator;
	}

	/**
	 * Forwards messages from the scheduler to the corresponding Elevator and enables message forwarding back
	 */
	@Override
	public void run() {
		// Start all elevator threads
		for (Elevator e: elevators) {
			new Thread(e).start();
		}
		while (true) {
			Message message = null;
			
			// Waits for message to be sent by scheduler
			byte[] bytes = bufferCommunicator.getRequestBuffer();
			try {
				message = SerializeUtils.deserialize(bytes);
				
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			
			switch (message.getMessageType()) {
			
				case SERVICE_FLOOR_REQUEST_MESSAGE:
					// If Message is ServiceFloorRequest, send the request to the corresponding elevator and wait for its response
					ServiceFloorRequestMessage serviceFloorRequestMessage = (ServiceFloorRequestMessage) message;
					Integer elevatorId = serviceFloorRequestMessage.getElevatorId();
					Message confirmationMessage = createConfirmationMessage(elevatorId, serviceFloorRequestMessage);
					
					try {
						// Send the elevator's response back to the scheduler
						bufferCommunicator.putResponseBuffer(SerializeUtils.serialize(confirmationMessage));
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
	
				default:
					System.out.println("Elevator System does not handle messages of type: " + message.getMessageType());
					break;
				}
		}

	}
	
	/**
	 * Method for forwarding ServiceFloorRequest to the correct elevator and then waits to receive the confirmation 
	 * message back from that elevator
	 * 
	 * @param elevatorId the id of the elevator who should receive the message
	 * @param serviceFloorRequestMessage message to be sent to the elevator
	 * @return confirmationMessage from elevator to determine if the request is accepted or declined
	 */
	private Message createConfirmationMessage(Integer elevatorId, ServiceFloorRequestMessage serviceFloorRequestMessage) {
		Message msg = null;
		for (Elevator elevator: elevators) {
			if (elevator.getElevatorId() == elevatorId) {
				elevator.putFloorRequest(serviceFloorRequestMessage);
				msg = elevator.getConfirmationMessage();
			}
		}
		return msg;
	}
}
