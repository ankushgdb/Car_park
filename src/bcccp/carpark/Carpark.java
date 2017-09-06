package bcccp.carpark;

import java.util.ArrayList;
import java.util.List;

import bcccp.tickets.adhoc.IAdhocTicket;
import bcccp.tickets.adhoc.IAdhocTicketDAO;
import bcccp.tickets.season.ISeasonTicket;
import bcccp.tickets.season.ISeasonTicketDAO;

public class Carpark implements ICarpark {
	
	private List<ICarparkObserver> observers;
	private String carparkId_;
	private int capacity_;
	private int nParked_;
	private IAdhocTicketDAO adhocTicketDAO_;
	private ISeasonTicketDAO seasonTicketDAO_;
	
	
	
	public Carpark(String carparkId, int capacity_, 
			IAdhocTicketDAO adhocTicketDAO_, 
			ISeasonTicketDAO seasonTicketDAO_) {
		//TODO Implement constructor
		this.carparkId = this.name;
		this.capacity = this.capacity;
		observers = new ArrayList<>();
		this.adhocTicketDAO = adhocTicketDAO;
		this.seasonTicketDAO = seasonTicketDAO;
		
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
	
	private void notifyObservers() {
		for (ICarparkObserver observer : observers) {
			observer.notifyCarparkEvent();
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
		return nParked + seasonTicketDAO.getNumberOfTickets() == capacity;
	}



	@Override
	public IAdhocTicket issueAdhocTicket() {
		// TODO Auto-generated method stub
		return adhocTicketDAO.createTicket(carparkId);
	}



	@Override
	public IAdhocTicket getAdhocTicket(String barcode) {
		// TODO Auto-generated method stub
		return adhocTicketDAO.findTicketByBarcode(barcode); 
	}



	// Assume the price is $3.00 for the whole time?
	@Override
	public float calculateAddHocTicketCharge(long entryDateTime) {
		// TODO Auto-generated method stub
		return 3.0f;
	}



	// May have to convert to date?
	@Override
	public boolean isSeasonTicketValid(String barcode) {
ISeasonTicket ticket = seasonTicketDAO.findTicketById(barcode);
		
		// TODO implement full validation logic
		return ticket != null;
	}


	@Override
	public void registerSeasonTicket(ISeasonTicket seasonTicket) {
		seasonTicketDAO.registerTicket(seasonTicket);		
	}



	@Override
	public void deregisterSeasonTicket(ISeasonTicket seasonTicket) {
		seasonTicketDAO.deregisterTicket(seasonTicket);		
	}
	
	@Override
	public void recordSeasonTicketEntry(String ticketId) {
		ISeasonTicket ticket = seasonTicketDAO.findTicketById(ticketId);
		if (ticket == null) throw new RuntimeException("recordSeasonTicketEntry: invalid ticketId - " + ticketId);
		
		seasonTicketDAO.recordTicketEntry(ticketId);
		log(ticket.toString());
	}
	
	private void log(String message) {
		System.out.println("Carpark : " + message);
	}



	@Override
	public void recordAdhocTicketEntry() {
		nParked++;
		
	}
	
	@Override
	public void recordAdhocTicketExit() {
		nParked--;
		notifyObservers();		
	}



	@Override
	public void recordSeasonTicketExit(String ticketId) {
		ISeasonTicket ticket = seasonTicketDAO.findTicketById(ticketId);
		if (ticket == null) throw new RuntimeException("recordSeasonTicketExit: invalid ticketId - " + ticketId);
		
		seasonTicketDAO.recordTicketExit(ticketId);
		log(ticket.toString());
	}



	@Override
	public boolean isSeasonTicketInUse(String ticketId) {
		ISeasonTicket ticket = seasonTicketDAO.findTicketById(ticketId);
		if (ticket == null) throw new RuntimeException("recordSeasonTicketExit: invalid ticketId - " + ticketId);
		
		return ticket.inUse();
	}



}