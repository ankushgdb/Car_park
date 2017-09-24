package bcccp.ticket.season;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bcccp.tickets.season.ISeasonTicket;
import bcccp.tickets.season.IUsageRecord;
import bcccp.tickets.season.SeasonTicket;
import bcccp.tickets.season.UsageRecord;

public class SeasonTicketTest {
	private ISeasonTicket seasonTicket;
	
	private List<IUsageRecord> usages;
	private IUsageRecord currentUsage;
	private String ticketId;
	private String carparkId;
	private long startValidPeriod;
	private long endValidPeriod;

	@Before
	public void setUp() throws Exception {
		@SuppressWarnings("unchecked")
		List<IUsageRecord> usages = (List<IUsageRecord>) mock(ArrayList.class);
		this.usages = usages;
		seasonTicket = mock(ISeasonTicket.class);
		currentUsage = mock(IUsageRecord.class);
		ticketId = "S1760";
		carparkId = "278";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		startValidPeriod = sdf.parse("2017-06-12 12:00:00").getTime();
		endValidPeriod = sdf.parse("2017-09-13 12:00:00").getTime();
		
		seasonTicket = new SeasonTicket(ticketId, carparkId, startValidPeriod, endValidPeriod);
	}

	@After
	public void tearDown() throws Exception {
		seasonTicket = null;
	}

	@Test
	public void testInit()
	{
		assertTrue(seasonTicket instanceof ISeasonTicket);
		assertTrue(currentUsage instanceof IUsageRecord);	
	}
	
	@Test(expected=RuntimeException.class) 
	public void testConstructorWithNullTicketId() {
		seasonTicket = new SeasonTicket(null, carparkId, startValidPeriod, endValidPeriod);		
		fail("Should have thrown exception");
	}
	
	@Test(expected=RuntimeException.class) 
	public void testConstructorWithNullCarparkId() {
		seasonTicket = new SeasonTicket(ticketId, null, startValidPeriod, endValidPeriod);		
		fail("Should have thrown exception");
	}
	
	
	@Test
	public void testGetId()
	{
		String id = seasonTicket.getId();
		assertEquals(id,ticketId);
	}
	
	@Test
	public void testGetCarparkId()
	{
		String id = seasonTicket.getCarparkId();
		assertEquals(id,carparkId);
	}
	
	@Test
	public void testStartValidPeriod()
	{
		long start = seasonTicket.getStartValidPeriod();
		assertEquals(start,startValidPeriod);
	}
	
	@Test
	public void testEndValidPeriod()
	{
		long end = seasonTicket.getEndValidPeriod();
		assertEquals(end,endValidPeriod);
	}
	
	@Test
	public void testCurrentUsageRecord()
	{
		assertNotSame(currentUsage,seasonTicket.getCurrentUsageRecord());
	}
	
	@Test
	public void testRecordUsage()
	{
		UsageRecord record = new UsageRecord(ticketId, startValidPeriod);
		seasonTicket.recordUsage(record);
	}

}
