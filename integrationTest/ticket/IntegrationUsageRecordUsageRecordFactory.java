package integrationTest.ticket;

import org.junit.Before;
import org.junit.Test;

import bcccp.tickets.season.IUsageRecord;
import bcccp.tickets.season.UsageRecord;
import bcccp.tickets.season.UsageRecordFactory;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;

import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   UsageRecord.class,
   UsageRecordFactory.class
})

public class IntegrationUsageRecordUsageRecordFactory {

    static UsageRecord usageRecord;
    static UsageRecordFactory usageRecordFactory;
    IUsageRecord iUsageRecord;


    Logger logger = Logger.getLogger("Integration testing for Usage Record and Usage Record Factory");

    @Before
    public static void setUp() throws Exception{
        usageRecord = new UsageRecord("123",0L);
        usageRecordFactory = new UsageRecordFactory();
    }

    @Test
    public void testContructorsUsageRecord(){
        logger.log(Level.INFO,"Integration test for Usage Record constructor");
        assertEquals("123",usageRecord.getSeasonTicketId());
        assertEquals(0L,usageRecord.getStartTime());
        usageRecord.finalise(1L);
        assertEquals(1L,usageRecord.getEndTime());
    }

    @Test
    public void testMakeRecordwithRecordFactory(){
        iUsageRecord = usageRecordFactory.make("123",0L);
        assertEquals("123",iUsageRecord.getSeasonTicketId());
        assertEquals(0L,iUsageRecord.getStartTime());


    }

}