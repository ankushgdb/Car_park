package bcccp.ticket.season;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bcccp.tickets.season.IUsageRecord;
import bcccp.tickets.season.UsageRecord;

public class UsageRecordTest {
	
	 IUsageRecord sut;
	 String ticketId;
	 String carparkId;
	 long startDateTime;
	 long endDateTime;
	
	@Before
    public void setUp() {
        startDateTime = new Date().getTime();
        endDateTime = new Date().getTime() + 3000;
        sut = new UsageRecord(ticketId, startDateTime);
    }
    
    @After
    public void tearDown() {
        sut = null;
    }

    @Test
    public void testFinalise() {
        sut.finalise(endDateTime);
        long result = sut.getEndTime();
        assertEquals(endDateTime, result);
    }

    @Test
    public void testGetStartTime() {
        assertEquals(startDateTime, sut.getStartTime());
    }

    @Test
    public void testGetEndTimeValid() {
        sut.finalise(endDateTime);        
        assertEquals(endDateTime, sut.getEndTime());
    }
    
    @Test
    public void testGetEndTimeInvalid() { 
        long expected = 0L; //usage has not ended
        long result = sut.getEndTime();
        assertEquals(expected, result);
    }

    @Test
    public void testGetSeasonTicketId() {     
        assertEquals(ticketId, sut.getSeasonTicketId());
    } 
}
