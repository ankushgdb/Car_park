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
		//TODO Implement constructor
		try {
		this.carpark = carpark;
		this.ui = ui;
		this.state = State.IDLE;
		}catch(NullPointerException e) {
			display("Can't be null");
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
