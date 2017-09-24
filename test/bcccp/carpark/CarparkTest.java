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
	public void recordAdhocTicketExit() {
		//cars + 1
		cpTest.recordAdhocTicketEntry();
		//cars + 1
		cpTest.recordAdhocTicketEntry();
		//cars + 1 = capacity = 3 = full
		cpTest.recordAdhocTicketEntry();

		if (cpTest.isFull()) { 
		//3 - 1 = 2  (capacity - 1)
		cpTest.recordAdhocTicketExit();
		}
		assertEquals(false, cpTest.isFull());


	}

	@Test
	/**
	 * increments the number of adhoc carpark spaces in use. May cause the carpark to become full (ie
	 * all adhoc spaces filled)
	 */
	public void recordAdhocTicketEntry() {
		//cars + 1
		cpTest.recordAdhocTicketEntry();
		//cars + 1
		cpTest.recordAdhocTicketEntry();
		//car + 1
		cpTest.recordAdhocTicketEntry();
		assertEquals(true, cpTest.isFull());

	}

	@Test
	/**
	 * returns the adhoc ticket identified by the barcode, returns null if the ticket does not exist,
	 * or is not current (ie not in use).
	 */
	public void getAdhocTicket() {
		IAdhocTicket expected = cpTest.issueAdhocTicket();
		IAdhocTicket ticket = cpTest.getAdhocTicket(expected.getBarcode());

		// This test is failing when only one ticket has been issued.
		assertEquals(expected.getEntryDateTime(), ticket.getEntryDateTime());


	}

	@Test
	/**
	 * registers a season ticket with the carpark so that the season ticket may be used to access the
	 * carpark.
	 *
	 * Note this test will fail because there is no usage record - requires ExitController.ticketInserted()
	 * to call SeasonTicketDAO.recordTicketEntry().
	 */
	public void registerSeasonTicket() {
		ISeasonTicket tktA = mock(SeasonTicket.class);
		doReturn("S1234").when(tktA).getId();
		doReturn("Bathurst Plus").when(tktA).getCarparkId();
		cpTest.registerSeasonTicket(tktA);
		verify(seasonTicketDAO).registerTicket(tktA);

	}

	/**
	 * Throws a RuntimeException if the carpark the season ticket is associated with is not the
	 * same as the carpark name.
	 */
	@Test
	public void registerSeasonTicketRuntimeExceptionTest() {
		try {
			cpTest.registerSeasonTicket(new SeasonTicket("S9999", "Wrong Name", 0L, 0L));
			fail("Expected a RuntimeException to be thrown");

		} catch (Exception e) {
			assertEquals(
					"SeasonTicket in registerSeasonTicket has invalid CarparkId: Wrong Name, should be CarparkId: Alphabet Street",
					e.getMessage());
		}

	}


	@Test
	/**
	 * deregisters the season ticket so that the season ticket may no longer be used to access the
	 * carpark
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
	 * causes a new usage record to be created and associated with a season ticket.
	 */
	@Test
	public void recordSeasonTicketEntry() {
		ISeasonTicket tkt = mock(SeasonTicket.class);
		when(tkt.getId()).thenReturn("S1234");
		when(tkt.getCarparkId()).thenReturn("Barthurst Plus");
		cpTest.registerSeasonTicket(tkt);

		cpTest.recordSeasonTicketEntry(tkt.getId());

		verify(seasonTicketDAO).recordTicketEntry("S1234");

	}


	/**
	 * Throws a
	 * RuntimeException if the season ticket associated with ticketId does not exist, or is currently
	 * in use
	 */
	@Test
	public void recordSeasonTicketEntryRuntimeExceptionTest() {

		try {
			//no ticket has the following id:
			cpTest.recordSeasonTicketEntry("badId");

			fail("Expected a RuntimeException to be thrown");

		} catch (Exception e) {

			assertEquals("Runtime Exception: No corresponding ticket.", e.getMessage());
		}

		/** todo: test for exception thrown if id of car exists and is 'in use' */
	}

	@Test
	/**
	 * causes the current usage record of the season ticket associated with ticketID to be finalized.
	 * throws throws a RuntimeException if the season ticket associated with ticketId does not exist,
	 * or is not currently in use
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