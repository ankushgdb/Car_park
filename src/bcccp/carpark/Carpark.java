package bcccp.carpark;

import java.util.List;

import bcccp.tickets.adhoc.IAdhocTicket;
import bcccp.tickets.adhoc.IadhocTicketDAO_;

import bcccp.tickets.season.ISeasonTicket;
import bcccp.tickets.season.ISeasonTicketDAO_;

public class Carpark implements ICarpark {
	
	private List<ICarparkObserver> observers;
	private String carparkId_;
	private int capacity_;
	private int numberOfCarsParked_;
	private IadhocTicketDAO_ adhocTicketDAO_;
	private IseasonTicketDAO_ seasonTicketDAO_;
	
	
	
	public Carpark(String name, int capacity_, 
			IadhocTicketDAO_ adhocTicketDAO_, 
			IseasonTicketDAO_ seasonTicketDAO_) {
		//TODO Implement constructor
	}


	// Add 1 observer to the list of observers
	@Override
	public void register(ICarparkObserver observer) {
		// TODO Auto-generated method stub
	
	}


	// Remove 1 observer from the list of observers
	@Override
	public void deregister(ICarparkObserver observer) {
		// TODO Auto-generated method stub
		
	}
	

	// Getting name of carpark?
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


	// Check the carpark is full capacity or not
	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public IAdhocTicket issueAdhocTicket() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void recordAdhocTicketEntry() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public IAdhocTicket getAdhocTicket(String barcode) {
		// TODO Auto-generated method stub
		return null; 
	}



	// Assume the price is $3.00 for the whole time?
	@Override
	public float calculateAddHocTicketCharge(long entryDateTime) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void recordAdhocTicketExit() {
		// TODO Auto-generated method stub
		
	}




	// May have to convert to date?
	@Override
	public boolean isSeasonTicketValid(String ticketId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean isSeasonTicketInUse(String ticketId) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void registerSeasonTicket(ISeasonTicket seasonTicket) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void deregisterSeasonTicket(ISeasonTicket seasonTicket) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void recordSeasonTicketEntry(String ticketId) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void recordSeasonTicketExit(String ticketId) {
		// TODO Auto-generated method stub
		
	}
	
	private void log(String message) {
		System.out.println("Carpark : " + message);
	}


}