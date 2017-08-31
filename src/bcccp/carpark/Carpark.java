package bcccp.carpark;

import java.util.List;

import bcccp.tickets.adhoc.IAdhocTicket;
import bcccp.tickets.adhoc.IAdhocTicketDAO;
import bcccp.tickets.season.ISeasonTicket;
import bcccp.tickets.season.ISeasonTicketDAO;

public class Carpark implements ICarpark {
	
	private List<ICarparkObserver> observers;
	private String carparkId;
	private int capacity;
	private int numberOfCarsParked;
	private IAdhocTicketDAO adhocTicketDAO;
	private ISeasonTicketDAO seasonTicketDAO;
	
	
	
	public Carpark(String carparkId, int capacity, 
			IAdhocTicketDAO adhocTicketDAO, 
			ISeasonTicketDAO seasonTicketDAO) {
		//TODO Implement constructor
		carparkId = this.carparkId;
		capacity = this.capacity;
		adhocTicketDAO = this.adhocTicketDAO;
		seasonTicketDAO = this.seasonTicketDAO;
		
	}


	// Add 1 observer to the list of observers
	@Override
	public void register(ICarparkObserver observer) {
		// TODO Auto-generated method stub
		if(!observers.contains(observer))
		{
			observers.add(observer);
		}
	}


	// Remove 1 observer from the list of observers
	@Override
	public void deregister(ICarparkObserver observer) {
		// TODO Auto-generated method stub
		if(observers.contains(observer))
		{
			observers.remove(observer);
		}
	}


	// Getting name of carpark?
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return carparkId;
	}


	// Check the carpark is full capacity or not
	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		boolean isFull = false;
		if (numberOfCarsParked < capacity) {
			isFull = false;
		}
		else isFull = true;
		return isFull;
	}



	@Override
	public IAdhocTicket issueAdhocTicket() {
		// TODO Auto-generated method stub
		return adhocTicket.createTicket(carparkId);
	}



	@Override
	public void recordAdhocTicketEntry() {
		// TODO Auto-generated method stub
		numberOfCarsParked ++;
	}



	@Override
	public IAdhocTicket getAdhocTicket(String barcode) {
		// TODO Auto-generated method stub
		return adhocTicket.findTicketByBarcode(barcode);
	}



	// Assume the price is $3.00 for the whole time?
	@Override
	public float calculateAddHocTicketCharge(long entryDateTime) {
		// TODO Auto-generated method stub
		float charge = 3.00;
		return charge;
	}


	// Maybe add some activities?
	@Override
	public void recordAdhocTicketExit() {
		// TODO Auto-generated method stub
		numberOfCarsParked --;
	}



	@Override
	public void registerSeasonTicket(ISeasonTicket seasonTicket) {
		// TODO Auto-generated method stub
		seasonTicket.registerTicket(seasonTicket);
	}



	@Override
	public void deregisterSeasonTicket(ISeasonTicket seasonTicket) {
		// TODO Auto-generated method stub
		seasonTicket.deregisterSeasonTicket(seasonTicket);
	}


	// May have to convert to date?
	@Override
	public boolean isSeasonTicketValid(String ticketId) {
		// TODO Auto-generated method stub
		boolean isSeasonTicketValid = false;
		if (seasonTicket.findTicketById(ticketId).getStartValidPeriod() <= System.currentTimeMillis())
		&& (seasonTicket.findTicketById(ticketId).getEndValidPeriod() >= System.currentTimeMillis()) {
			isSeasonTicketValid = true;
		} 
		else isSeasonTicketValid = false;
		return isSeasonTicketValid;
	}



	@Override
	public boolean isSeasonTicketInUse(String ticketId) {
		// TODO Auto-generated method stub
			boolean isSeasonTicketInUse = false;
		if (seasonTicket.findTicketById(ticketId).inUse() ==true) {
			isSeasonTicketInUse = true;
		} 
		else isSeasonTicketInUse = false;
		return isSeasonTicketInUse;
	}



	@Override
	public void recordSeasonTicketEntry(String ticketId) {
		// TODO Auto-generated method stub
		seasonTicket.recordTicketEntry(ticketId);
		numberOfCarsParked ++;
	}



	@Override
	public void recordSeasonTicketExit(String ticketId) {
		// TODO Auto-generated method stub
		seasonTicket.recordTicketExit(ticketId);
		numberOfCarsParked --:
	}

	
	

}
