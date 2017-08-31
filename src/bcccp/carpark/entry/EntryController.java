package bcccp.carpark.entry;

import bcccp.carpark.Carpark;
import bcccp.carpark.ICarSensor;
import bcccp.carpark.ICarSensorResponder;
import bcccp.carpark.ICarpark;
import bcccp.carpark.ICarparkObserver;
import bcccp.carpark.IGate;
public class EntryController 
		implements ICarSensorResponder,
				   ICarparkObserver,
		           IEntryController {
	
	private IGate entryGate;
	private ICarSensor outsideSensor; 
	private ICarSensor insideSensor;
	private IEntryUI ui;
	
	private ICarpark carpark;
	private IAdhocTicket  adhocTicket = null;
	private long entryTime;
	private String seasonTicketId = null;
	
	

	public EntryController(Carpark carpark, IGate entryGate, 
			ICarSensor os, 
			ICarSensor is,
			IEntryUI ui) {
		//TODO Implement constructor
		this.carpark = _carpark;
		this.entryGate = _entryGate;
		this.outsideSensor = _os;
		this.insideSensor = _is;
		this.ui = _ui;
	}



	@Override
	public void buttonPushed() {
		// TODO Auto-generated method stub
		adhocTicket = this.carpark.issueAdhocTicket();
		if(null != this.adhocTicket)
		{
			this.ui.display("Carpark    : " + adhocTicket.getCarparkId() + " Ticket No  : " + adhocTicket.getTicketNo());
			this.ui.printTicket(adhocTicket.getCarparkId(), adhocTicket.getTicketNo(), adhocTicket.getEntryDateTime(), adhocTicket.getBarcode());
		}
		entryGate.raise();
		
	}
	
	public void ticketInserted(String ticketId) {
		this.seasonTicket = this.carpark.findTicketById(ticketId);
		if(null != seasonTicket)
		{
			this.carpark.recordSeasonTicketEntry(ticketId);
			this.seasonTicketId = ticketId;
			this.ui.display("Carpark    : " + seasonTicket.getCarparkId() + " Ticket No  : " + seasonTicket.getId());
			entryGate.raise();
		}
		else
		{
			System.out.println("yahn");
			this.ui.display("Please enter a valid ticket.");
		}
	}


	@Override
	public void ticketTaken() {
		// TODO Auto-generated method stub
		entryGate.lower();
		insideSensor.setSensorValue(true);
		outsideSensor.setSensorValue(false);
	}



	@Override
	public void notifyCarparkEvent() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void carEventDetected(String detectorId, boolean detected) {
		// TODO Auto-generated method stub
		System.out.println("car event for : " + detectorId + " , " + detected);
	}

	
	
}
