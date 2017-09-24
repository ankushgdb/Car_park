package bcccp.ticket.adhoc;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bcccp.tickets.adhoc.*;

public class AdhocTicketTest {
	
	private String carparkId;
    private int ticketNo;
    private long entryDateTime;
    private long paidDateTime;
    private long exitDateTime;
    private float charge;
    private String barcode;
    private STATE state;

    private enum STATE {
        ISSUED,
        CURRENT,
        PAID,
        EXITED
    }
    
    private IAdhocTicket adhocTicket;

	@Before
	public void setUp() throws Exception {
		mock(IAdhocTicket.class);
		ticketNo = 1760;
		carparkId = "278";
		barcode = "A1760";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entryDateTime = sdf.parse("2017-06-12 12:00:00").getTime(); //1497232800000
		paidDateTime = sdf.parse("2017-06-12 14:00:00").getTime();
		exitDateTime = sdf.parse("2017-06-12 14:05:00").getTime();
		charge = (float) 3.00;
		
		new AdhocTicket(carparkId, ticketNo, barcode);
	}

	@After
	public void tearDown() throws Exception {
		adhocTicket = null;
	}

	@Test
	public void testInit()
	{
		assertTrue(adhocTicket instanceof IAdhocTicket);	
	}
	
	@Test(expected=RuntimeException.class) 
	public void testConstructorWithInvalidTicketId() {
		adhocTicket = new AdhocTicket(carparkId, -1 , barcode);		
		fail("Should bave thrown exception");
	}
	
	@Test(expected=RuntimeException.class) 
	public void testConstructorWithNullCarparkId() {
		adhocTicket = new AdhocTicket(null, ticketNo, barcode);		
		fail("Should bave thrown exception");
	}
	
	@Test(expected=RuntimeException.class)
	public void testConstructorWithNullBarcode( ) {
		adhocTicket = new AdhocTicket(carparkId, ticketNo, null);
		fail("Should bave thrown exception");
	}
	
	@Test
	public void testGetTicketNo()
	{
		int no = adhocTicket.getTicketNo();
		assertEquals(no, ticketNo);
	}
	
	@Test
	public void testGetBarcode() {
		String bc = adhocTicket.getBarcode();
		assertEquals(bc, barcode);
	}

	@Test
	public void testGetCarparkId() {
		String id = adhocTicket.getCarparkId();
		assertEquals(id,carparkId);
	}

	/* @Test
	public void testEnter() {

		
	}*/

	@Test
	public void testGetEntryDateTime() {
		long dt = adhocTicket.getEntryDateTime();
		assertEquals(dt, entryDateTime);
	}

	/* @Test
	public void testIsCurrent() {
		fail("Not yet implemented");
	}*/

	/*@Test
	public void testPay() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testGetPaidDateTime() {
		long dt = adhocTicket.getPaidDateTime();
		assertEquals(dt, paidDateTime);
	}

	/*@Test
	public void testIsPaid() {
		fail("Not yet implemented");
	}*/

	/*@Test
	public void testGetCharge() {
		fail("Not yet implemented");
	}*/

	/*@Test
	public void testToString() {
		fail("Not yet implemented");
	}*/

	/*@Test
	public void testExit() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testGetExitDateTime() {
		long dt = adhocTicket.getExitDateTime();
		assertEquals(dt, exitDateTime);
	}

	/*@Test
	public void testHasExited() {
		fail("Not yet implemented");
	}*/


}