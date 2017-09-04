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
		return numberOfCarsParked >= capacity;
	}



	@Override
	public IAdhocTicket issueAdhocTicket() {
		// TODO Auto-generated method stub
		return adhocTicketDAO.createTicket(this.carparkId);
	}



	@Override
	public void recordAdhocTicketEntry() {
		// TODO Auto-generated method stub
		this.adhocTicketDAO.recordAdhocTicketEntry(Integer.valueOf(ticketId));
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
		return 0;
	}


	// Maybe add some activities?
	@Override
	public void recordAdhocTicketExit(String ticketId) {
		// TODO Auto-generated method stub
		this.adhocTicketDAO.recordAdhocTicketExit(Integer.valueOf(ticketId));
	}



	@Override
	public void registerSeasonTicket(ISeasonTicket seasonTicket) {
		// TODO Auto-generated method stub
		this.seasonTicketDAO.registerTicket(seasonTicket);
	}



	@Override
	public void deregisterSeasonTicket(ISeasonTicket seasonTicket) {
		// TODO Auto-generated method stub
		this.seasonTicketDAO.deregisterTicket(seasonTicket);
	}


	// May have to convert to date?
	@Override
	public boolean isSeasonTicketValid(String ticketId) {
		// TODO Auto-generated method stub
		return this.seasonTicketDAO.isTicketValid(ticketId);
	}



	@Override
	public boolean isSeasonTicketInUse(String ticketId) {
		// TODO Auto-generated method stub
		return this.seasonTicketDAO.isTicketValid(ticketId);
	}



	@Override
	public void recordSeasonTicketEntry(String ticketId) {
		// TODO Auto-generated method stub
		this.seasonTicketDAO.recordTicketEntry(ticketId);
	}



	@Override
	public void recordSeasonTicketExit(String ticketId) {
		// TODO Auto-generated method stub
		this.seasonTicketDAO.recordTicketExit(ticketId);
	}
	
	public ISeasonTicket findTicketById(String ticketId) {
		return this.seasonTicketDAO.findTicketById(ticketId);
	}	

}
