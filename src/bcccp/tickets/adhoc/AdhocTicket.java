/*
 * This method creates an adhocTicket object
 * with constructor of carparkId, ticketNo, barcode
 * ticketNo is incremented according to carpark
 * barcode consists of A + hexstring of ticketNo + hexstring of dateTime
 */
package bcccp.tickets.adhoc;

import bcccp.tickets.adhoc.AdhocTicket.STATE;

import java.util.Date;

public class AdhocTicket implements IAdhocTicket {
	
	private String carparkId_;
	private int ticketNo_;
	private long entryDateTime;
	private long paidDateTime;
	private long exitDateTime;
	private float charge;
	private String barcode;
	private STATE state_;
	
	private enum STATE { ISSUED, CURRENT, PAID, EXITED };
	
	
	public AdhocTicket(String carparkId, int ticketNo, String barcode) {
		//TDO Implement constructor
		carparkId_ = carparkId;
		ticketNo_ = ticketNo;
		barcode_ = barcode;
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
	public void enter(long dateTime) {
		// TODO Auto-generated method stub
		entryDateTime_ = dateTime;
	}


	@Override
	public long getEntryDateTime() {
		// TODO Auto-generated method stub
		return entryDateTime_;
	}


	/* 	Checks whether the ticket is valid at current time; 
	 *  ie the entryDateTime has been initiated (entryDateTime_ > 0)
	 *  and the exitDateTime has not happened (exitDateTime_ = 0)
	 * @see bcccp.tickets.adhoc.IAdhocTicket#isCurrent()
	 */
	@Override
	public boolean isCurrent() {
		// TODO Auto-generated method stub
		if ((entryDateTime_ > 0) && (exitDateTime_ <= 0)) { 
			return true;
		}
		else return false;
	}


	@Override
	public void pay(long dateTime, float charge) {
		// TODO Auto-generated method stub
		paidDateTime_ = dateTime;
		charge_ = charge;
	}


	@Override
	public long getPaidDateTime() {
		// TODO Auto-generated method stub
		return paidDateTime_;
	}


	@Override
	public boolean isPaid() {
		// TODO Auto-generated method stub
		if (paidDateTime_ >0 & charge_ > 0) return true;
			// the ticket is paid if paidDateTime is initiated & charge is activated
		else return false;
	}


	@Override
	public float getCharge() {
		// TODO Auto-generated method stub
		return charge_;
	}


	@Override
	public void exit(long dateTime) {
		// TODO Auto-generated method stub
		exitDateTime_ = dateTime;
	}


	@Override
	public long getExitDateTime() {
		// TODO Auto-generated method stub
		return exitDateTime_;
	}


	@Override
	public boolean hasExited() {
		// TODO Auto-generated method stub
		if (exitDateTime_ > 0) return true;
		else return false;
	}

	
	
}
