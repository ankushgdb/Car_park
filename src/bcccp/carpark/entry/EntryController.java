package bcccp.carpark.entry;

import bcccp.carpark.Carpark;
import bcccp.carpark.ICarSensor;
import bcccp.carpark.ICarSensorResponder;
import bcccp.carpark.ICarpark;
import bcccp.carpark.ICarparkObserver;
import bcccp.carpark.IGate;
import bcccp.tickets.adhoc.IAdhocTicket;

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
		carpark = this.carpark;
		entryGate = this.entryGate;
		os = this.outsideSensor;
		is = this.insideSenSor;
		ui = this.ui;
	}



	@Override
	public void buttonPushed() {
		adhocTicket = this.carpark.issueAdhocTicket();
		if(null != this.adhocTicket)
		{
			this.ui.display("Carpark    : " + adhocTicket.getCarparkId() + " Ticket No  : " + adhocTicket.getTicketNo());
			this.ui.printTicket(adhocTicket.getCarparkId(), adhocTicket.getTicketNo(), adhocTicket.getEntryDateTime(), adhocTicket.getBarcode());
		}
		entryGate.raise();
	}



	@Override
	public void ticketInserted(String barcode) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void ticketTaken() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void notifyCarparkEvent() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void carEventDetected(String detectorId, boolean detected) {
		// TODO Auto-generated method stub
		
	}

	
	
}
