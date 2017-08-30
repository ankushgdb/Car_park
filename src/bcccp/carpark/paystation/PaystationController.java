package bcccp.carpark.paystation;

import bcccp.carpark.ICarpark;
import bcccp.tickets.adhoc.IAdhocTicket;

public class PaystationController 
		implements IPaystationController {
	
	private IPaystationUI ui;	
	private ICarpark carpark;

	private IAdhocTicket  adhocTicket = null;
	private float charge;
	
	

	public PaystationController(ICarpark carpark, IPaystationUI ui) {
		//TODO Implement constructor
		this.carpark = carpark;
		this.ui = ui;
	}



	@Override
	public void ticketInserted(String barcode) {
		// TODO Auto-generated method stub
		/*
		if the controller state is IDLE 
			request the carpark to return the adhoc ticket identified by the barcode
			if a ticket is returned and is current and not paid 
				request the carpark to calculate the charge 
				display the charge 
				transition controller to WAITING state 
			otherwise
				make the UI 'beep'
				transition controller to REJECTED state
		otherwise
			make the UI 'beep'
		*/
	}



	@Override
	public void ticketPaid() {
		// TODO Auto-generated method stub
		/*
		if the controller is in the WAITING state 
			record the payment time and charge for the adhoc ticket 
			print payment details on ticket 
			transition controller to PAID state 
		otherwise
			make the UI ‘beep’ 
		*/
	}



	@Override
	public void ticketTaken() {
		// TODO Auto-generated method stub
		/*
		if the controller is in the WAITING, PAID, or REJECTED states 
			transition the controller to the IDLE state 
		otherwise
			make the UI 'beep'
		*/
	}

	
	
}
