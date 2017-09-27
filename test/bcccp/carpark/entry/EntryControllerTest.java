package bcccp.carpark.entry;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

import bcccp.carpark.Carpark;
import bcccp.carpark.ICarSensor;
import bcccp.carpark.ICarpark;
import bcccp.carpark.IGate;
import bcccp.carpark.entry.EntryController;
import bcccp.carpark.entry.IEntryController;
import bcccp.carpark.entry.IEntryUI;
import bcccp.tickets.adhoc.IAdhocTicket;


public class EntryControllerTest {

	String message;

	IGate entryGate;
	ICarSensor outsideEntrySensor; 
	ICarSensor insideEntrySensor;
	IEntryUI ui;

	Carpark carpark;
	IAdhocTicket  adhocTicket;
	long entryTime;
	String seasonTicketId;
	IEntryController entryController;

	final String IDLE = "IDLE";
	final String WAITING = "WAITING";
	final String FULL = "FULL";
	final String VALIDATED = "VALIDATED";
	final String ISSUED = "ISSUED";
	final String TAKEN = "TAKEN";
	final String ENTERING = "ENTERING";
	final String ENTERED = "ENTERED";
	final String BLOCKED = "BLOCKED";

	@Before
	public void setUp() throws Exception {
		entryGate = mock(IGate.class);
		outsideEntrySensor = mock(ICarSensor.class);
		insideEntrySensor = mock(ICarSensor.class);
		ui = mock(IEntryUI.class);
		carpark = mock(Carpark.class);
		adhocTicket = mock(IAdhocTicket.class);
		entryController = new EntryController(carpark, entryGate, outsideEntrySensor, insideEntrySensor, ui);

	}

	@Test
	public void testInit() {
		assertTrue(entryController instanceof IEntryController);
		assertTrue(entryGate instanceof IGate);
		assertTrue(outsideEntrySensor instanceof ICarSensor);
		assertTrue(insideEntrySensor instanceof ICarSensor);
	}

	@Test(expected=RuntimeException.class)
	public void testConstructorWithNullcarpark()
	{
		entryController = new EntryController(null, entryGate, outsideEntrySensor, insideEntrySensor, ui);
		fail("Should have thrown exception");
	}

	@Test(expected=RuntimeException.class)
	public void testConstructorWithNullEntryGate()
	{
		entryController = new EntryController(carpark, null, outsideEntrySensor, insideEntrySensor, ui);
		fail("Should have thrown exception");
	}

	@Test(expected=RuntimeException.class)
	public void testConstructorWithNullOutsideEntrySensor()
	{
		entryController = new EntryController(carpark, entryGate, null, insideEntrySensor, ui);
		fail("Should have thrown exception");
	}

	@Test(expected=RuntimeException.class)
	public void testConstructorWithNullInsideEntrySensor()
	{
		entryController = new EntryController(carpark, entryGate, outsideEntrySensor, null, ui);
		fail("Should have thrown exception");
	}

	@Test(expected=RuntimeException.class)
	public void testConstructorWithNullUi()
	{
		entryController = new EntryController(carpark, entryGate, outsideEntrySensor, insideEntrySensor, null);
		fail("Should have thrown exception");
	}

	@Test
	public void entryControllerInitialisedToIdle() {
		assertEquals(IDLE, entryController.getState());

	}

	@Test
	public void notifyCarparkEventCheckCarparkFull() {
		when(carpark.isFull()).thenReturn(false);
		entryController.notifyCarparkEvent();
		verify(ui).display("Push Button");
		assertTrue(WAITING.equals(entryController.getState()));

	}

	@Test
	public void buttonPushedCarparkFull() {
		when(carpark.isFull()).thenReturn(true);
		entryController.buttonPushed();
		verify(ui).display("Carpark Full");
		assertTrue(FULL.equals(entryController.getState()));

	}

	@Test
	public void buttonPushedCarparkNotFull() {
		when(carpark.isFull()).thenReturn(false);
		entryController.buttonPushed();
		verify(ui).display("Take Ticket");
		assertTrue(ISSUED.equals(entryController.getState()));

	}

	@Test
	public void buttonPushedNotWaiting() {
		entryController.buttonPushed();
		assertNotEquals(WAITING, entryController.getState());
	}

	@Test
	public void ticketInsertedSeasonTicketValidAndNotInUse() {
		String barcode = "Test Barcode";
		when(carpark.isSeasonTicketValid(barcode)).thenReturn(true);
		when(carpark.isSeasonTicketInUse(barcode)).thenReturn(false);
		entryController.ticketInserted(barcode);
		verify(ui).display("Take Ticket");
		assertTrue(VALIDATED.equals(entryController.getState()));

	}

}
