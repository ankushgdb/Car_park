package bcccp.carpark.paystation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bcccp.carpark.ICarpark;
import bcccp.carpark.paystation.*;
import bcccp.tickets.adhoc.IAdhocTicket;

public class PaystationControllerTest {

	final String IDLE = "IDLE";
	final String WAITING = "WAITING";
	final String REJECTED = "REJECTED";
	final String PAID = "PAID";
	
	IPaystationUI ui;
	ICarpark carpark;
	IAdhocTicket  adhocTicket;
	float charge;
	IPaystationController sut;
	String barcode; // adhocTicket barcode


	@Before
	public void setUp() throws Exception {
		ui = mock(IPaystationUI.class);
		carpark = mock(ICarpark.class);
		adhocTicket = mock(IAdhocTicket.class);
		sut = new PaystationController(carpark, ui);
		barcode = adhocTicket.getBarcode();
	}

	@After
	public void tearDown() throws Exception {
		sut = null;
		adhocTicket = null;
		carpark = null; 
		ui = null;
	}

	@Test
	public void testInit()
	{
		assertTrue(sut instanceof IPaystationController);
		assertTrue(ui instanceof IPaystationUI);
		assertTrue(carpark instanceof ICarpark);
		assertTrue(adhocTicket instanceof IAdhocTicket);
	}

	@Test(expected=RuntimeException.class) 
	public void testConstructorWithNullCarpark() {
		sut = new PaystationController(null, ui);		
		fail("Should have thrown exception");
	}

	@Test(expected=RuntimeException.class) 
	public void testConstructorWithNullUI() {
		sut = new PaystationController(carpark, null);		
		fail("Should have thrown exception");
	}
	
	public void controllerIsStateIdle() throws NullPointerException {
		sut.ticketInserted(barcode);
		assertEquals(IDLE, sut.getStateAsString());
	}

	@Test
	public void ticketInsertedIsBarcodeValidAndTicketReturned() throws NullPointerException {
		adhocTicket = carpark.getAdhocTicket(barcode);
		assertEquals(barcode, adhocTicket.getBarcode());
	}

	@Test
	public void ticketInsertedAndReturnedCheckIsCurrent() {
		adhocTicket = carpark.getAdhocTicket(barcode);
		sut.ticketInserted(adhocTicket.getBarcode());
		when(adhocTicket.isCurrent()).thenReturn(true);

	}

	@Test
	public void ticketInsertedAndReturnedCheckIsNotPaid() throws NullPointerException {
		adhocTicket = carpark.getAdhocTicket(barcode);
		sut.ticketInserted(adhocTicket.getBarcode());
		when(adhocTicket.isPaid()).thenReturn(false);
	}

	//@Test
	//public void ticketInsertedIsChargeCalculated() throws NullPointerException {
	//	adhocTicket = carpark.getAdhocTicket(barcode);
	//	sut.ticketInserted(adhocTicket.getBarcode());
	//	assert((float)3.00, adhocTicket.getCharge());

	//}

	@Test
	public void ticketPaidIsStateWaiting() throws NullPointerException {
		sut.ticketPaid();
		assertEquals(WAITING, sut.getStateAsString());

	}

	@Test
	public void ticketPaidIsTimeAndChargeRecorded() throws NullPointerException {
		adhocTicket = carpark.getAdhocTicket(barcode);
		sut.ticketPaid();
		verify(carpark).recordAdhocTicketExit();
	}


	@Test
	public void ticketTakenIsStateWaiting() throws NullPointerException {
		sut.ticketTaken();
		assertEquals(WAITING, sut.getStateAsString());
	}

	@Test
	public void ticketTakenIsStatePaid() throws NullPointerException {
		sut.ticketTaken();
		assertEquals(PAID, sut.getStateAsString());
	}


	}




	

	


