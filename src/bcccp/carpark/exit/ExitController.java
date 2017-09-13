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
	enum State
    {
		IDLE, WAITING, BLOCKED, PROCESSED, REJECTED, TAKEN, EXITING, EXITED
    }
	
	private IGate exitGate;
	private ICarSensor insideSensor;
	private ICarSensor outsideSensor; 
	private IExitUI ui;
	private State state;
	private ICarpark carpark;
	private IAdhocTicket  adhocTicket = null;
	private long exitTime;
	private String seasonTicketId = null;
	
	

	public ExitController(Carpark carpark, IGate exitGate, 
			ICarSensor is,
			ICarSensor os, 
			IExitUI ui) {
		//TODO Implement constructor
		this.carpark = carpark;
		this.exitGate = exitGate;
		this.insideSensor = is;
		this.outsideSensor = os;
		this.ui = ui;
		this.state = State.IDLE;
		/*
		Register the exit controller with both car sensors as a ICarEventResponder 
		Register the exit controller with the ui as and IExitController 
		Initialise the exit controller state to IDL
		*/
	}



	@Override
	public void ticketInserted(String ticketStr) {
		// TODO Auto-generated method stub
		Date date = new Date();
		if(state == State.WAITING) {
			if(getBarcode().charAt(0) == 'A') {
				if(findTicketByBarcode(String barcode) ==! null) {
					 
					 exitTime = date.getTime();
					 display('take processed ticket');
					 state = State.PROCESSED;
				}else {
					('take rejected ticket');
					state = State.REJECTED;
				}
			}else if(getBarcode().charAt(0) == 'S') {
				if(getId() ==! null) {
					 UsageRecord.finalise(date);
					 System.out.println('take processed ticket');
					 state = State.PROCESSED;
				}else {
					System.out.println('take rejected ticket');
					state = State.REJECTED;
				}
			}else {
				System.out.println('take rejected ticket');
				state = State.REJECTED
				beep();
			}
		}else {
			beep();
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
