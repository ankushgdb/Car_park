package bcccp.carpark.paystation;

import bcccp.carpark.ICarpark;
import bcccp.tickets.adhoc.IAdhocTicket;
import bcccp.carpark.CalcAdhocTicketCharge;

public class PaystationController implements IPaystationController {

	private enum STATE { IDLE, WAITING, REJECTED, PAID } 

	private IPaystationUI ui_;
	private ICarpark carpark_;

	private IAdhocTicket adhocTicket_ = null;
	private float charge_;
	private STATE state_;

	public PaystationController(ICarpark carpark, IPaystationUI ui) {
		if (carpark != null && ui != null) {
			ui.registerController(this);
			carpark_ = carpark;
			ui_ = ui;
			setState(STATE.IDLE);	
			ui.display("Idle");
		}
		else {
			throw new RuntimeException("Arguments to constructor cannot be null.");
		}
	}

	@Override
	public void ticketInserted(String barcode) {

		if (state_ == STATE.IDLE) {
			adhocTicket_ = carpark_.getAdhocTicket(barcode);
			if (adhocTicket_ != null) {
				charge_ = CalcAdhocTicketCharge.calculateAddHocTicketCharge(adhocTicket_.getEntryDateTime());
				ui_.display("Pay " + String.format("%.2f", charge_));
				setState(STATE.WAITING);
			}
			else {
				ui_.beep();
				ui_.display("Take Rejected Ticket");
				setState(STATE.REJECTED);
				log("ticketInserted: ticket is not current");				
			}
		}
		else {
			ui_.beep();
			log("ticketInserted: called while in incorrect state");				
		}
	}

	@Override
	public void ticketPaid() {
		if (state_ == STATE.WAITING) {
			adhocTicket_.pay(adhocTicket_.getExitDateTime(), charge_);
		carpark_.recordAdhocTicketExit();
		ui_.printTicket(
				carpark_.getName(),
				adhocTicket_.getTicketNo(),
				adhocTicket_.getEntryDateTime(),
				adhocTicket_.getPaidDateTime(),
				charge_,
				adhocTicket_.getBarcode());
		setState(STATE.PAID);
		}
		else {
			ui_.beep();
			log("ticketPaid: called while in incorrect state");				
		}
		
	}

	@Override
	public void ticketTaken() {
		if (state_ == STATE.IDLE) {
			ui_.beep();
			log("ticketTaken: called while in incorrect state");				
		}
		else {
			setState(STATE.IDLE);
			ui_.display("Idle");
			ui_.deregisterController();
		}

	}

	@Override
	public Object getState() {
		// TODO Auto-generated method stub
		return null;
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
		private void log(String message) {
			System.out.println("EntryController : " + message);
		}
		public String getStateAsString() {
			return state_.name();
		}
		
	}
