package bcccp.ticket.adhoc;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bcccp.tickets.adhoc.*;

public class AdhocTicketTest {
	
	String carparkId;
    int ticketNo;
    long entryDateTime;
    long paidDateTime;
    long exitDateTime;
    float charge;
    String barcode;
    final String ISSUED = "ISSUED";
    final String CURRENT = "CURRENT";
    final String PAID = "PAID";
    final String EXITED = "EXITED";
    
    private IAdhocTicket sut;

	@Before
	public void setUp() throws Exception {
		mock(IAdhocTicket.class);
		ticketNo = 1760;
		carparkId = "278";
		barcode = "A1760";
		charge = (float) 3.00;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sut = new AdhocTicket(carparkId, ticketNo, barcode);
	}

	@After
	public void tearDown() throws Exception {
		carparkId="";
		barcode = "";
		sut = null;
	}
	
	@Test(expected=RuntimeException.class) 
	public void testConstructorWithInvalidTicketId() {
		sut = new AdhocTicket(carparkId, -1 , barcode);		
		fail("Should have thrown exception");
	}
	
	@Test(expected=RuntimeException.class) 
	public void testConstructorWithNullCarparkId() {
		sut = new AdhocTicket(null, ticketNo, barcode);		
		fail("Should have thrown exception");
	}
	
	@Test(expected=RuntimeException.class)
	public void testConstructorWithNullBarcode( ) {
		sut = new AdhocTicket(carparkId, ticketNo, null);
		fail("Should have thrown exception");
	}
	
	@Test
	public void testGetTicketNo()
	{
		int expected = ticketNo;
		int result = sut.getTicketNo();
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetBarcode() {
		String expected = barcode;
		String result = sut.getBarcode();
		assertEquals(expected, result);
	}

	@Test
	public void testGetCarparkId() {
		String expected = carparkId;
		String result = sut.getCarparkId();
		assertEquals(expected, result);
	}

	@Test
	public void testEnter() {
		long dateTime = new Date().getTime();
		sut.enter(dateTime);
		assertTrue(CURRENT.equals(sut.getState()));
	}
	
	@Test
	public void testGetEntryDateTime() {
		long dateTime = new Date().getTime();
		sut.enter(dateTime);
		assertEquals(dateTime, sut.getEntryDateTime());
	}

	@Test
	public void testIsCurrentWithCurrentTicket() {
		long dateTime = new Date().getTime();
		sut.enter(dateTime);
		boolean expected = true;
		boolean result = sut.isCurrent();
		assertEquals(expected, result);
	}
	
	@Test
	public void testIsCurrentWithExitedTicket() {
		long dateTime = new Date().getTime();
		sut.exit(dateTime);
		boolean expected = false;
		boolean result = sut.isCurrent();
		assertEquals(expected, result);
	}
	

	@Test
	public void testPay() {
		long dateTime = new Date().getTime();
		sut.pay(dateTime, charge);
		assertTrue(PAID.equals(sut.getState()));
	}

	@Test
	public void testGetPaidDateTime() {
		long dateTime = new Date().getTime();
		sut.pay(dateTime, charge);
		assertEquals(dateTime, sut.getPaidDateTime());
	}

	@Test
	public void testIsPaidtWithCurrentTicket() {
		long dateTime = new Date().getTime();
		sut.enter(dateTime);
		boolean expected = false;
		boolean result = sut.isPaid();
		assertEquals(expected, result);
	}
	
	@Test
	public void testIsPaidWithPaidTicket() {
		long dateTime = new Date().getTime();
		sut.enter(dateTime);
		dateTime = new Date().getTime();
		sut.pay(dateTime, charge);
		boolean expected = true;
		boolean result = sut.isPaid();
		assertEquals(expected, result);
	}
	
	@Test
	public void testIsPaidWithExitedTicket() {
		long dateTime = new Date().getTime();
		sut.enter(dateTime);
		dateTime = new Date().getTime();
		sut.pay(dateTime, charge);
		dateTime = new Date().getTime();
		sut.exit(dateTime);
		boolean expected = false;
		boolean result = sut.isPaid();
		assertEquals(expected, result);
	}
	@Test
	public void testExit() {
		long dateTime = new Date().getTime();
		sut.exit(dateTime);
		assertTrue(EXITED.equals(sut.getState()));
	}

	@Test
	public void testGetExitDateTime() {
		long dateTime = new Date().getTime();
		sut.exit(dateTime);
		assertEquals(dateTime, sut.getExitDateTime());
	}

	@Test
	public void testHasExitedtWithCurrentTicket() {
		long dateTime = new Date().getTime();
		sut.enter(dateTime);
		boolean expected = false;
		boolean result = sut.hasExited();
		assertEquals(expected, result);
	}
	
	@Test
	public void testHasExitedWithExitedTicket() {
		long dateTime = new Date().getTime();
		sut.enter(dateTime);
		dateTime = new Date().getTime();
		sut.pay(dateTime, charge);
		dateTime = new Date().getTime();
		sut.exit(dateTime);
		boolean expected = true;
		boolean result = sut.hasExited();
		assertEquals(expected, result);
	}
	



}