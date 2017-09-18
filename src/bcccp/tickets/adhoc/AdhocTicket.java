/*
 * This method creates an adhocTicket object
 * with constructor of carparkId, ticketNo, barcode
 * ticketNo is incremented according to carpark
 * barcode consists of A + hexstring of ticketNo + hexstring of dateTime
 */
package bcccp.tickets.adhoc;

import java.util.Date;

public class AdhocTicket implements IAdhocTicket {

	private String carparkId_;
	private int ticketNo_;
	private long entryDateTime_;
	private long paidDateTime_;
	private long exitDateTime_;
	private float charge_;
	private String barcode_;
	private STATE state_;

	/*
	 * Create the state of ticket:
	 * ISSUED: the ticket has been issued
	 * CURRENT: when the ticket has been issued and car has entered, not yet paid
	 * PAID: when the ticket has been paid
	 * EXITED: when the car has exited
	 */
	private enum STATE { ISSUED, CURRENT, PAID, EXITED };


	public AdhocTicket(String carparkId, int ticketNo, String barcode) {
		//TDO Implement constructor
		if(carparkId == null || barcode == null) {
			throw new IllegalArgumentException("Parameters cannot be null");
		}
		if (ticketNo <=0) {
			throw new IllegalArgumentException("Ticket number must be larger than 0");
		}

		carparkId_ = carparkId;
		ticketNo_ = ticketNo;
		barcode_ = barcode;
		state_ = STATE.ISSUED;
	}


	@Override
	public int getTicketNo() {
		// TODO Auto-generated method stub
		return ticketNo_;
	}


	@Override
	public String getBarcode() {
		// TODO Auto-generated method stub
		return barcode_;
	}


	@Override
	public String getCarparkId() {
		// TODO Auto-generated method stub
		return carparkId_;
	}


	@Override
	public void enter(long entryDateTime) {
		// TODO Auto-generated method stub
		if (entryDateTime <=0) {
			throw new RuntimeException("Date and time can't be negative or 0");
		}
		switch (state_) {
		case ISSUED:
			entryDateTime_ = entryDateTime;
			state_ = STATE.CURRENT;	
			break;
		default:
			throw new RuntimeException("Can only enter if the car in ISSUED state");
		}			
	}


	@Override
	public long getEntryDateTime() {
		// TODO Auto-generated method stub
		return entryDateTime_;
	}


	/* 	Checks if the state of the ticket is current
	 */
	@Override
	public boolean isCurrent() {
		// TODO Auto-generated method stub
		return state_ == STATE.CURRENT;
	}


	@Override
	public void pay(long paidDateTime, float charge) {
		// TODO Auto-generated method stub
		paidDateTime_ = paidDateTime;
		charge_ = charge;
		state_ = STATE.PAID;
		}



	@Override
	public long getPaidDateTime() {
		// TODO Auto-generated method stub
		return paidDateTime_;
	}


	@Override
	public boolean isPaid() {
		// TODO Auto-generated method stub
		return state_ == STATE.PAID;
	}


	@Override
	public float getCharge() {
		// TODO Auto-generated method stub
		return charge_;
	}


	@Override
	public void exit(long exitDateTime) {
		// TODO Auto-generated method stub
		if (exitDateTime <= paidDateTime_) {
			throw new RuntimeException("Time of exit must be after time of payment");
		}
		switch(state_) {
		case PAID:
			exitDateTime_ = exitDateTime;
			state_ = STATE.EXITED;
			break;
		default:
			throw new RuntimeException("Can only exit if current ticket has been paid");
		}


	}


	@Override
	public long getExitDateTime() {
		// TODO Auto-generated method stub
		return exitDateTime_;
	}


	@Override
	public boolean hasExited() {
		// TODO Auto-generated method stub
		return state_ == STATE.EXITED;
	}

	public STATE getState() {
		return state_;
	}
	
	public String toString() {
		Date entryDateTime = new Date(entryDateTime_);
		Date paidDateTime = new Date(paidDateTime_);
		Date exitDateTime = new Date(exitDateTime_);

		return "Carpark    : " + carparkId_ + "\n" +
		"Ticket No  : " + ticketNo_ + "\n" +
		"Entry Time : " + entryDateTime + "\n" + 
		"Paid Time  : " + paidDateTime + "\n" + 
		"Exit Time  : " + exitDateTime + "\n" +
		"State      : " + state_ + "\n" +
		"Barcode    : " + barcode_;		
	}

}
