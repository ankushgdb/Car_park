package bcccp.carpark.paystation;

import bcccp.carpark.ICarpark;
import bcccp.tickets.adhoc.IAdhocTicket;

public class PaystationController 
		implements IPaystationController {
	
private enum STATE { IDLE, WAITING, REJECTED, PAID } 
	
	private STATE state_;
	
	private IPaystationUI ui_;
	
	private ICarpark carpark_;

	private IAdhocTicket  adhocTicket_ = null;
	private float charge_;
	
	

	public PaystationController(ICarpark carpark, IPaystationUI ui) {
		
		this.carpark_ = carpark;
		this.ui_ = ui;
		
		ui.registerController(this);		
		setState(STATE.IDLE);		
	}

	private void log(String message) {
		System.out.println("EntryController : " + message);
	}

	
	
	private void setState(STATE newState) {
		switch (newState) {
		
		case IDLE: 
			state_ = STATE.IDLE;
			ui_.display("Idle");
			
			log("setState: IDLE");
			break;
			
		case WAITING: 
			state_ = STATE.WAITING;
			log("setState: WAITING");
			break;
			
		case REJECTED: 
			state_ = STATE.WAITING;
			log("setState: WAITING");
			break;
			
		case PAID: 
			state_ = STATE.PAID;
			ui_.display("Paid");
			log("setState: PAID");
			break;			
			
		default: 
			break;
			
		}			
	}


	@Override
	public void ticketInserted(String barcode) {
		// TODO Auto-generated method stub
		if(state == State.IDLE) {
			getAdhocTicket(barcode);
				if(isPaid() !== true) {
					calculateAddHocTicketCharge(getEntryDateTime());
					display(charge);
					state = State.WAITING;
				}else {
					beep();
					state = State.REJECTED;
				}
		}beep();
	}



	@Override
	public void ticketPaid() {
		// TODO Auto-generated method stub
			
		if(state == State.WAITING) {
			calculateAddHocTicketCharge(getEntryDateTime());
			printTicket(adhocTicket);
			state = State.PAID;
		}else {
			beep();
		}
		
	}



	@Override
	public void ticketTaken() {
		// TODO Auto-generated method stub
		
		if(state == State.WAITING || state == State.PAID || state == State.REJECTED ) {
			state = State.IDLE;
		}else {
			beep();
		}
	}

	
	
}
