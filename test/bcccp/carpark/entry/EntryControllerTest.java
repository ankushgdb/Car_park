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
	
private enum STATE { IDLE, WAITING, FULL, VALIDATED, ISSUED, TAKEN, ENTERING, ENTERED, BLOCKED } 
	
	 STATE state;
	 STATE prevSate;
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
	 
}
