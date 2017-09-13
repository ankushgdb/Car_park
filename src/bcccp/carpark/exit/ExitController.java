package bcccp.carpark.exit;

import bcccp.carpark.Carpark;
import bcccp.carpark.ICarSensor;
import bcccp.carpark.ICarSensorResponder;
import bcccp.carpark.ICarpark;
import bcccp.carpark.IGate;
import bcccp.tickets.adhoc.IAdhocTicket;
import bcccp.carpark.ICarSensor;
import bcccp.carpark.ICarSensorResponder;
import bcccp.carpark.ICarpark;
import bcccp.carpark.IGate;
import bcccp.tickets.adhoc.IAdhocTicket;
import java.util.*;

public class ExitController 
implements ICarSensorResponder,
           IExitController {

private enum STATE { IDLE, WAITING, PROCESSED, REJECTED, TAKEN, EXITING, EXITED, BLOCKED } 

private STATE state;
private STATE prevState;
private String message;
//private String prevMessage;

private IGate exitGate;
private ICarSensor is;
private ICarSensor os; 
private IExitUI ui;

private ICarpark carpark;
private IAdhocTicket  adhocTicket = null;
private long exitTime;
private String seasonTicketId = null;
	
	

public ExitController(Carpark carpark, IGate exitGate, 
		ICarSensor is,
		ICarSensor os, 
		IExitUI ui) {
	
	this.carpark = carpark;
	this.exitGate = exitGate;
	this.is = is;
	this.os = os;
	this.ui = ui;
	
	os.registerResponder(this);
	is.registerResponder(this);
	ui.registerController(this);

	prevState = STATE.IDLE;		
	setState(STATE.IDLE);		
}



@Override
public void carEventDetected(String detectorId, boolean carDetected) {

	log("carEventDetected: " + detectorId + ", car Detected: " + carDetected );
	
	switch (state) {
	
	case BLOCKED: 
		if (detectorId.equals(is.getId()) && !carDetected) {
			setState(prevState);
		}
		break;
		
	case IDLE: 
		log("eventDetected: IDLE");
		if (detectorId.equals(is.getId()) && carDetected) {
			log("eventDetected: setting state to WAITING");
			setState(STATE.WAITING);
		}
		else if (detectorId.equals(os.getId()) && carDetected) {
			setState(STATE.BLOCKED);
		}
		break;
		
	case WAITING: 
	case PROCESSED: 
		if (detectorId.equals(is.getId()) && !carDetected) {
			setState(STATE.IDLE);
		}
		else if (detectorId.equals(os.getId()) && carDetected) {
			setState(STATE.BLOCKED);
		}
		break;
		
	case TAKEN: 
		if (detectorId.equals(is.getId()) && !carDetected) {
			setState(STATE.IDLE);
		}
		else if (detectorId.equals(os.getId()) && carDetected) {
			setState(STATE.EXITING);
		}
		break;
		
	case EXITING: 
		if (detectorId.equals(is.getId()) && !carDetected) {
			setState(STATE.EXITED);
		}
		else if (detectorId.equals(os.getId()) && !carDetected) {
			setState(STATE.TAKEN);
		}
		break;
		
	case EXITED: 
		if (detectorId.equals(is.getId()) && carDetected) {
			setState(STATE.EXITING);
		}
		else if (detectorId.equals(os.getId()) && !carDetected) {
			setState(STATE.IDLE);
		}
		break;
		
	default: 
		break;
		
	}
	
}



	@Override
	public void ticketTaken() {
		// TODO Auto-generated method stub
		if(state == State.PROCESSED) {
			raise();
			state = State.TAKEN;
		}else if(state == State.REJECTED) {
			state = State.WAITING;
		}else {
			beep();
		}
		
	}



	@Override
	public void carEventDetected(String detectorId, boolean detected) {
		// TODO Auto-generated method stub
		switch(state) {
			case IDLE:
				if(is) {
					display("Insert Ticket")
					state = State.WAITING;
				}
				if(os) {
					state = State.BLOCKED;
				}
				break;
			case WAITING:
				if(!is) {
					state = State.IDLE;
				}else if(os) {
					state = State.BLOCKED;
				}
				break;
			case BLOCKED:
				if(!is) {
					state = State.IDLE;
				}else if(!os) {
					display("no car detected");
				}
				break;
			case TAKEN:
				if(!is) {
					state = State.IDLE;
				}else if(os) {
					state = State.EXITING;
				}
				break;
			case EXITING:
				if(!is) {
					state = State.EXITED;
				}else if(!os) {
					state = State.TAKEN;
				}
				break;
			case EXITED:
				if(!os) {
					state = State.IDLE;
				} else if(is) {
					state = State.EXITING;
				}
		}
	}

	
	
}
