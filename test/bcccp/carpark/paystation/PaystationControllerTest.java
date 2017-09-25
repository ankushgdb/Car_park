package bcccp.carpark.paystation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bcccp.carpark.ICarpark;
import bcccp.carpark.exit.AdhocTicket;
import bcccp.carpark.exit.IPaystationUI;
import bcccp.carpark.exit.List;
import bcccp.carpark.exit.UnfinishedVerificationException;
import bcccp.carpark.paystation.*;
import bcccp.tickets.adhoc.IAdhocTicket;

public class PaystationControllerTest {
	
	enum STATE { IDLE, WAITING, REJECTED, PAID } 
	
	STATE state;
	IPaystationUI ui;
	ICarpark carpark;
	IAdhocTicket  adhocTicket;
	float charge;
	IPaystationController payStation;

	
	@Before
	public void setUp() throws Exception {
		ui = mock(IPaystationUI.class);
		carpark = mock(ICarpark.class);
		adhocTicket = mock(IAdhocTicket.class);
		payStation = new PaystationController(carpark, ui);
	}
	
	@After
	public void tearDown() throws Exception {
		payStation = null;
		adhocTicket = null;
		carpark = null; 
		ui = null;
	}
	
	@Test
	public void testInit()
	{
		assertTrue(payStation instanceof IPaystationController);
		assertTrue(ui instanceof IPaystationUI);
		assertTrue(carpark instanceof ICarpark);
		assertTrue(adhocTicket instanceof IAdhocTicket);
	}
	
	@Test(expected=RuntimeException.class) 
	public void testConstructorWithNullCarpark() {
		payStation = new PaystationController(null, ui);		
		fail("Should have thrown exception");
	}
	
	@Test(expected=RuntimeException.class) 
	public void testConstructorWithNullPayStationUI() {
		payStation = new PaystationController(carpark, null);		
		fail("Should have thrown exception");
	}
	
	public void controllerIsStateIdle() throws NullPointerException {
	    paystationController.ticketInserted(barcode);
	    verify(userInterface).display(captor.capture());
	    assertEquals("Idle", captor.getValue());
	  }
	
	@Test
	  public void ticketInsertedIsBarcodeValidAndTicketReturned() throws NullPointerException {
	    ticket = cp.getAdhocTicket(barcode);
	    assertEquals(barcode, ticket.getBarcode());
	  }

	  @Test
	  public void ticketInsertedAndReturnedCheckIsCurrent() {
	    ticket = cp.getAdhocTicket(barcode);
	    paystationController.ticketInserted(ticket.getBarcode());
	    when(ticket.isCurrent()).thenReturn(true);

	  }

	  @Test
	  public void ticketInsertedAndReturnedCheckIsNotPaid() throws NullPointerException {
	    ticket = cp.getAdhocTicket(barcode);
	    paystationController.ticketInserted(ticket.getBarcode());
	    when(ticket.isPaid()).thenReturn(false);
	  }

	  @Test
	  public void ticketInsertedIsChargeCalculated() throws NullPointerException {
	    ticket = cp.getAdhocTicket(barcode);
	    paystationController.ticketInserted(ticket.getBarcode());
	    verify(userInterface).display(captor.capture());
	    assertEquals("AU", captor.getValue().substring(0,1));

	  }

	  @Test
	  public void ticketInsertedNotIdle() {

	    // Following variable declarations are to assist with debugging, in particular to see what's
	    // inside the variables that are used as arguments in the dependent modules.

	    String checkBarcode = barcode;
	    ticket = new AdhocTicket("Flinders Lane", 34, barcode);
	    when(cp.getAdhocTicket(barcode)).thenReturn(ticket);
	    IAdhocTicket checkadhocTicket = cp.getAdhocTicket(barcode);
	    long checkdateTime = ticket.getEntryDateTime();
	    checkdateTime = cp.getAdhocTicket(barcode).getEntryDateTime();
	    String checkDateInfo = ticket.toString();
	    float checkCharge = CalcAdhocTicketCharge.calculateAddHocTicketCharge(checkdateTime);
	    checkCharge = CalcAdhocTicketCharge.calculateAddHocTicketCharge(ticket.getEntryDateTime());
	    paystationController.ticketInserted(barcode);
	    verify(userInterface).display(captor.capture());

	    assertEquals("Idle", captor.getValue());

	  }

	  @Test
	  public void ticketPaidIsStateWaiting() throws NullPointerException {
	    paystationController.ticketPaid();
	    verify(userInterface).display(captor.capture());
	    assertEquals("Waiting", captor.getValue());

	  }

	  @Test
	  public void ticketPaidIsTimeAndChargeRecorded() throws NullPointerException {
	    ticket = cp.getAdhocTicket(barcode);
	    paystationController.ticketPaid();
	    verify(cp).recordAdhocTicketExit();
	  }

	  @Test
	  public void ticketPaidIsPaymentPrinted() throws NullPointerException {
	    paystationController.ticketPaid();
	    argsList = ArgumentCaptor.forClass(IPaystationUI.class);
	    List<IPaystationUI> numOfArgs = argsList.getAllValues();
	    assertEquals(barcode, numOfArgs.get(5));

	  }

	  @Test
	  public void ticketTakenIsStateWaiting() throws NullPointerException {
	    paystationController.ticketTaken();
	    verify(userInterface).display(captor.capture());
	    assertEquals("Waiting", captor.getValue());
	  }

	  @Test
	  public void ticketTakenIsStatePaid() throws NullPointerException {
	    paystationController.ticketTaken();
	    verify(userInterface).display(captor.capture());
	    when(captor.capture().equals("Paid")).thenReturn(true);
	  }

	  @Test
	  public void ticketTakenIsStateRejected() throws UnfinishedVerificationException {

	    paystationController.ticketTaken();
	    verify(userInterface).display(captor.capture());
	    when(captor.capture().equals("Rejected")).thenReturn(true);

	  }
	 
	
}
}