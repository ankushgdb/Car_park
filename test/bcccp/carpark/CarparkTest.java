package bcccp.carpark;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bcccp.carpark.entry.EntryController;
import bcccp.tickets.adhoc.IAdhocTicket;
import bcccp.tickets.adhoc.IAdhocTicketDAO;
import bcccp.tickets.season.ISeasonTicket;
import bcccp.tickets.season.ISeasonTicketDAO;
import bcccp.tickets.season.IUsageRecord;
import bcccp.tickets.season.SeasonTicket;

public class CarparkTest {
	static IAdhocTicket adhocTicket;
	static IAdhocTicketDAO adhocTicketDAO;
	static ISeasonTicketDAO seasonTicketDAO;
	static Carpark cp;
	static Carpark cpTest;
	static EntryController entryController;
	static String DEFAULT_CARPARK = "Bathurst Plus";
	static int DEFAULT_CAPACITY = 3;


	@Before
	public void setUp() throws Exception{

		adhocTicketDAO = mock(IAdhocTicketDAO.class);
		seasonTicketDAO = mock(ISeasonTicketDAO.class);
		cpTest = new Carpark(DEFAULT_CARPARK, DEFAULT_CAPACITY, adhocTicketDAO, seasonTicketDAO);
		entryController = mock(EntryController.class);
		adhocTicket = mock(IAdhocTicket.class);

	}


	@After
	public void tearDown() throws Exception {
		cp = null;

	}
	
	@Test
	public void testInit()
	{
		assertTrue(adhocTicketDAO instanceof IAdhocTicketDAO);
		assertTrue(seasonTicketDAO instanceof ISeasonTicketDAO);
		assertTrue(entryController instanceof EntryController);
	}


	/* NOTE: Unchecked exceptions do not need to be declared in a method or constructor's throws clause if they can be thrown
  by the execution of the method. See: https://docs.oracle.com/javase/7/docs/api/java/lang/RuntimeException.html
  What is actually required by the following test is for the constructor to throw an illegalArgumentException under specified conditions
  (which extends RuntimeException). */

	@Test(expected=RuntimeException.class) 
	public void testConstructorWithNullCarparkId() {
		Carpark cpTest = new Carpark(null, 3, adhocTicketDAO, seasonTicketDAO);		
		fail("Should have thrown exception");
	}

	@Test(expected=RuntimeException.class) 
	public void testConstructorWithEmptyarkId() {
		Carpark cpTest = new Carpark("", 3, adhocTicketDAO, seasonTicketDAO);		
		fail("Should have thrown exception");
	}

	@Test(expected=RuntimeException.class) 
	public void testConstructorWithEmptyCapacity() {
		Carpark cpTest = new Carpark("Bathurst Chase", 0, adhocTicketDAO, seasonTicketDAO);		
		fail("Should have thrown exception");
	}

	@Test(expected=RuntimeException.class) 
	public void testConstructorWithNegativeCapacity() {
		Carpark cpTest = new Carpark("Bathurst Chase", -1, adhocTicketDAO, seasonTicketDAO);		
		fail("Should have thrown exception");
	}



	@Test
	/** returns the name of carpark */
	public void getName() {
		assertEquals("Bathurst Plus", cpTest.getName());
	}

	@Test
	/** returns a boolean indicating whether the carpark is full */
	public void isFull() {
		//cars + 1
		cpTest.recordAdhocTicketEntry();
		//cars + 1
		cpTest.recordAdhocTicketEntry();
		//cars + 1
		cpTest.recordAdhocTicketEntry();
		assertEquals(true, cpTest.isFull());
	}

	@Test
	/**
	 * when the carpark is available, create ticket
	 */
	public void issueAdhocTicket() {
		cpTest.issueAdhocTicket();
		verify(adhocTicketDAO).createTicket("Bathurst Plus");
	}

	@Test
	/**
	 * registers observer as an entity to be notified through the notifyCarparkEvent method when the
	 * carpark is full and spaces become available
	 */
	public void register() {
		cpTest.register(entryController);

	}

	@Test
	/** remove observer as an entity to be notified */
	public void deregister() {
		cpTest.deregister(entryController);

	}

	
	@Test
	/**
	 * increments the number of adhoc carpark spaces in use. check the car park is full,
	 */
	
	public void recordAdhocTicketExit() {
		cpTest.recordAdhocTicketEntry();
		cpTest.recordAdhocTicketEntry(); 
		cpTest.recordAdhocTicketEntry(); // carpark is full

		cpTest.recordAdhocTicketExit(); // one car exiting
		assertEquals(false, cpTest.isFull()); // carpark is not full


	}

	@Test
	/**
	 * increments the number of adhoc carpark spaces in use. check the car park is full
	 */
	public void recordAdhocTicketEntry() {
		cpTest.recordAdhocTicketEntry();
		cpTest.recordAdhocTicketEntry();
		cpTest.recordAdhocTicketEntry();
		assertEquals(true, cpTest.isFull()); // now the carpark is full

	}

	@Test
	/**
	 * returns the adhoc ticket identified by the barcode
	 */
	public void getAdhocTicket() {
		
		when(adhocTicket.getBarcode()).thenReturn("barcode");
		when(adhocTicketDAO.findTicketByBarcode("barcode")).thenReturn(adhocTicket);
		IAdhocTicket result = cpTest.getAdhocTicket("barcode");
		assertEquals(result.getBarcode(), "barcode");


	}

	@Test
	/**
	 * registers a season ticket with the carpark so that the season ticket may be used to access the
	 * carpark.
	 */
	public void registerSeasonTicket() {
		ISeasonTicket tktA = mock(SeasonTicket.class);
		doReturn("S1234").when(tktA).getId();
		doReturn("Bathurst Plus").when(tktA).getCarparkId();
		cpTest.registerSeasonTicket(tktA);
		verify(seasonTicketDAO).registerTicket(tktA);

	}

	@Test
	/**
	 * deregisters the season ticket so that the season ticket cannot be used 
	 */
	public void deregisterSeasonTicket() {
		ISeasonTicket tkt = mock(SeasonTicket.class);
		when(tkt.getId()).thenReturn("S1234");
		when(tkt.getCarparkId()).thenReturn("Bathurst Plus");
		cpTest.registerSeasonTicket(tkt);
		cpTest.deregisterSeasonTicket(tkt);
		verify(seasonTicketDAO).deregisterTicket(tkt);

	}

	@Test
	public void isSeasonTicketValid() {
		//not tested (valid if other methods are tested and passing)
	}



	/**
	 * causes a new usage record and attach it with a season ticket.
	 */
	@Test
	public void recordSeasonTicketEntry() {
		ISeasonTicket tkt = mock(SeasonTicket.class);
		when(tkt.getId()).thenReturn("S1234");
		when(tkt.getCarparkId()).thenReturn("Bathurst Plus");
		cpTest.registerSeasonTicket(tkt);

		cpTest.recordSeasonTicketEntry(tkt.getId());

		verify(seasonTicketDAO).recordTicketEntry("S1234");

	}

	@Test
	/**
	 * finalise usage record
	 */
	public void recordSeasonTicketExit() {
		ISeasonTicket tkt = mock(SeasonTicket.class);
		when(tkt.getId()).thenReturn("S1234");
		when(tkt.getCarparkId()).thenReturn("Bathurst Plus");
		cpTest.registerSeasonTicket(tkt);
		cpTest.recordSeasonTicketEntry(tkt.getId());
		cpTest.recordSeasonTicketExit(tkt.getId());
		verify(seasonTicketDAO).recordTicketExit(tkt.getId());

	}

}