package bcccp.carpark.exit;

import static org.junit.Assert.*;

import org.mockito.Mockito;
import org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyList;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import bcccp.carpark.Carpark;
import bcccp.carpark.ICarSensor;
import bcccp.carpark.ICarpark;
import bcccp.carpark.IGate;
import bcccp.carpark.exit.ExitController;
import bcccp.carpark.exit.IExitController;
import bcccp.carpark.exit.IExitUI;
import bcccp.tickets.adhoc.IAdhocTicket;
import bcccp.tickets.season.ISeasonTicket;

public class ExitControllerTest {
	
	IGate exitGate;
	ICarSensor insideSensor;
	ICarSensor outsideSensor;
	IExitUI ui;
	Carpark carpark;
	IAdhocTicket adhocTicket;
	long exitTime;
	ISeasonTicket seasonTicket;
	String barcode;
	static IExitController sut;
	

  private enum STATE {
      IDLE,
      WAITING,
      PROCESSED,
      REJECTED,
      TAKEN,
      EXITING,
      EXITED,
      BLOCKED
  }

  STATE state;
  STATE prevState;
  
  @Before
	public void setUp() throws Exception {
		exitGate = mock(IGate.class);
		outsideSensor = mock(ICarSensor.class);
		insideSensor = mock(ICarSensor.class);
		ui = mock(IExitUI.class);
		carpark = mock(Carpark.class);
		adhocTicket = mock(IAdhocTicket.class);
		seasonTicket = mock(ISeasonTicket.class);
		barcode = "1234";
		sut = new ExitController(carpark, exitGate, outsideSensor, insideSensor, ui);
		
	}

	@Test
	public void testInit() {
		assertTrue(sut instanceof IExitController);
		assertTrue(exitGate instanceof IGate);
		assertTrue(outsideSensor instanceof ICarSensor);
		assertTrue(insideSensor instanceof ICarSensor);
	}
	
	@Test(expected=RuntimeException.class)
	public void testConstructorWithNullcarpark()
	{
		sut = new ExitController(null, exitGate, outsideSensor, insideSensor, ui);
		fail("Should have thrown exception");
	}
	
	@Test(expected=RuntimeException.class)
	public void testConstructorWithNullEntryGate()
	{
		sut = new ExitController(carpark, null, outsideSensor, insideSensor, ui);
		fail("Should have thrown exception");
	}
	
	@Test(expected=RuntimeException.class)
	public void testConstructorWithNullOutsideEntrySensor()
	{
		sut = new ExitController(carpark, exitGate, null, insideSensor, ui);
		fail("Should have thrown exception");
	}
	
	@Test(expected=RuntimeException.class)
	public void testConstructorWithNullInsideEntrySensor()
	{
		sut = new ExitController(carpark, exitGate, outsideSensor, null, ui);
		fail("Should have thrown exception");
	}
	
	@Test(expected=RuntimeException.class)
	public void testConstructorWithNullUi()
	{
		sut = new ExitController(carpark, exitGate, outsideSensor, insideSensor, null);
		fail("Should have thrown exception");
	}
	
	@Test
	  public void exitControllerInitialisedToIdle() {
	    assertEquals(STATE.IDLE,sut.getState());
	  }

	  @Test
	  public void ticketInsertedCheckProcessingValidAdhocTicket() {
	    String validAdhocTicketBarcode = "A" + barcode.substring(1);
	    Mockito.when(carpark.getAdhocTicket(validAdhocTicketBarcode)).thenReturn(adhocTicket);
	    Mockito.when(adhocTicket.getPaidDateTime()).thenReturn(new Date().getTime());
	    Mockito.when(adhocTicket.isPaid()).thenReturn(true);
	    sut.ticketInserted(validAdhocTicketBarcode);
	    assertEquals(STATE.PROCESSED,sut.getState());

	  }


	@Test
	  public void ticketInsertedCheckProcessingNotValidAdhocTicket() {
	    String invalidAdhocTicketBarcode = "B" + barcode.substring(1);
	    Mockito.when(carpark.getAdhocTicket(invalidAdhocTicketBarcode)).thenReturn(adhocTicket);
	    Mockito.when(adhocTicket.getPaidDateTime()).thenReturn(new Date().getTime());
	    Mockito.when(adhocTicket.isPaid()).thenReturn(false);
	    sut.ticketInserted(invalidAdhocTicketBarcode);
	    assertTrue(STATE.REJECTED.equals(sut.getState()));

	  }

	  @Test
	  public void ticketInsertedCheckProcessingValidSeasonTicket() {
	    String validSeasonTicketBarcode = "S" + barcode.substring(1);
	    Mockito.when(carpark.isSeasonTicketValid(validSeasonTicketBarcode)).thenReturn(true);
	    Mockito.when(carpark.isSeasonTicketInUse(validSeasonTicketBarcode)).thenReturn(true);
	    sut.ticketInserted(validSeasonTicketBarcode);
	    assertTrue(STATE.PROCESSED.equals(sut.getState()));

	  }

	  @Test
	  public void ticketInsertedCheckProcessingNotValidSeasonTicket() {
	    String validSeasonTicketBarcode = "S" + barcode.substring(1);
	    Mockito.when(carpark.isSeasonTicketValid(validSeasonTicketBarcode)).thenReturn(false);
	    Mockito.when(carpark.isSeasonTicketInUse(validSeasonTicketBarcode)).thenReturn(false);
	    sut.ticketInserted(barcode);
	    assertTrue(STATE.REJECTED.equals(sut.getState()));
	  }

	  @Test
	  public void ticketInsertedCheckProcessingNotValidTicket() {

	    sut.ticketInserted("Invalid Ticket");
	    assertTrue(STATE.REJECTED.equals(sut.getState()));
	    Mockito.verify(ui).beep();

	  }

	 
	  @Test
	 public void ticketTakenProcessed() {
	    sut.ticketTaken();
	    assertTrue(STATE.TAKEN.equals(sut.getState()));

	  }

	  @Test
	  public void ticketTakenRejected() {
	    sut.ticketTaken();
	    assertTrue(STATE.WAITING.equals(sut.getState()));

	  }

	  @Test
	  public void ticketTakenBeep() {
	    sut.ticketTaken();
	    Mockito.verify(ui).beep();
	    assertFalse((STATE.PROCESSED.equals(sut.getState())) ||
	            (STATE.REJECTED.equals(sut.getState())));

	  }
}

