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
	private ISeasonTicket seasonTicket = null;
	private long entryTime;
	private String seasonTicketId = null;
	private String EVENT_OUTSIDE_SENSOR = "Entry Outside Sensor";
	private String EVENT_INSIDE_SENSOR = "Entry Inside Sensor";

	public EntryController(Carpark _carpark, IGate _entryGate, 
			ICarSensor _os, 
			ICarSensor _is,
			IEntryUI _ui) {
		this.carpark = _carpark;
		this.entryGate = _entryGate;
		this.outsideSensor = _os;
		this.insideSensor = _is;
		this.ui = _ui;
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
		System.out.println("car event for : " + detectorId + " , " + detected);
		/*
		if(EVENT_OUTSIDE_SENSOR.equals(detectorId) && detected)
		{
			outsideSensor.setSensorValue(true);
			insideSensor.setSensorValue(false);
		}
		if(EVENT_INSIDE_SENSOR.equals(detectorId) && detected)
		{
			outsideSensor.setSensorValue(false);
			insideSensor.setSensorValue(true);
			entryGate.lower();
		}
		*/
	}

	
	
}
