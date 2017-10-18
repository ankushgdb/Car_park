package integrationTest.ticket;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bcccp.tickets.season.ISeasonTicket;
import bcccp.tickets.season.ISeasonTicketDAO;
import bcccp.tickets.season.IUsageRecord;
import bcccp.tickets.season.IUsageRecordFactory;
import bcccp.tickets.season.SeasonTicket;
import bcccp.tickets.season.SeasonTicketDAO;
import bcccp.tickets.season.UsageRecord;
import bcccp.tickets.season.UsageRecordFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   ISeasonTicket.class,
   ISeasonTicketDAO.class,
   IUsageRecord.class,
   IUsageRecordFactory.class,
   ISeasonTicket.class,
})

public class IntegrationUsageRecordsSeasonTickedDAO {

    static SeasonTicket seasonTicket;
    static SeasonTicketDAO seasonTicketDAO;
    static UsageRecord usageRecord;
    static UsageRecordFactory usageRecordFactory;
    static ISeasonTicket iSeasonTicket;


    Logger logger = Logger.getLogger("Integration test for SeasonTicketDAO, SeasonTicket, UsageRecord and UsageRecordFactory");


    public IntegrationUsageRecordsSeasonTickedDAO() {
    }
    
    @Before
    public static void setUp() throws Exception {
        iSeasonTicket = new SeasonTicket("1234","A",0L,2L);
        seasonTicket = new SeasonTicket("1212","B",1L,10L);
        usageRecord = new UsageRecord("1212",2L);

    }

    @Test
    public void testConstructors(){

        logger.log(Level.INFO,"Testing constructors");
        assertEquals("1212",seasonTicket.getId());
        assertEquals("B",seasonTicket.getCarparkId());
        assertEquals(1L,seasonTicket.getStartValidPeriod());
        assertEquals(10L,seasonTicket.getEndValidPeriod());
        assertEquals("1212",usageRecord.getSeasonTicketId());
        assertEquals(2L,usageRecord.getStartTime());


    }
    @Test
    public void testSeasonTicketRegistration(){
        IUsageRecord newRecord;
        newRecord = usageRecordFactory.make("2233",3L);
        seasonTicketDAO = new SeasonTicketDAO(usageRecordFactory);
        seasonTicketDAO.registerTicket(seasonTicket);
        assertEquals("1212",newRecord.getSeasonTicketId());

    }

}