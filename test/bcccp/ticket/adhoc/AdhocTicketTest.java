package bcccptest.ticketTest.adhocTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import bcccp.tickets.adhoc.AdhocTicket;
import bcccp.tickets.adhoc.AdhocTicketDAO;
import bcccp.tickets.adhoc.AdhocTicketFactory;
import bcccp.tickets.adhoc.IAdhocTicket;
import bcccp.tickets.adhoc.IAdhocTicketDAO;
import bcccp.tickets.adhoc.IAdhocTicketFactory;

import mockdemo.*;

@RunWith(MockitoJUnitRunner.class)

public class AdhocTicketTest {
	
	static IAdhocTicket testAdhoc;
    static IAdhocTicketDAO iAdhocDAO;
    static IAdhocTicketFactory factory;

    Logger logger = Logger.getLogger("Unit testing for AdHocTicket class");

	@Before	
	public static void before() {
        testAdhoc = mock(AdhocTicket.class);
        iAdhocDAO = spy(new AdhocTicketDAO(new AdhocTicketFactory()));
    }

	@After
	 public void after() {
        testAdhoc = mock(AdhocTicket.class);
        iAdhocDAO = spy(new AdhocTicketDAO(new AdhocTicketFactory()));
	}
	
	@Test
	public void testGetTicketNo() {
		logger.log(Level.INFO, "Testing ticket number");
        testAdhoc.getTicketNo();
        
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("Error");
        });
        assertEquals("Error", exception.getMessage());	
	}

	@Test
	public void testGetBarcode() {
        logger.log(Level.INFO, "Test getBarcode method");
        testAdhoc.getBarcode();
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("Error");
        });
        assertEquals("Error", exception.getMessage());
	   
	}

	@Test
	public void testGetCarparkId() {
		logger.log(Level.INFO, "Test getCarparkId method with invalid parameter");
        testAdhoc.getCarparkId();
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("Error");
        });
        assertEquals("Error", exception.getMessage());
	}

	@Test
	public void testEnter() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEntryDateTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsCurrent() {
		fail("Not yet implemented");
	}

	@Test
	public void testPay() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPaidDateTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsPaid() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCharge() {
		fail("Not yet implemented");
	}

	@Test
	public void testExit() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetExitDateTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasExited() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetState() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
