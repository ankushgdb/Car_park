package bcccp.ticket.season;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import bcccp.carpark.ICarSensor;
import bcccp.carpark.IGate;
import bcccp.carpark.entry.IEntryController;
import bcccp.tickets.season.ISeasonTicket;
import bcccp.tickets.season.ISeasonTicketDAO;
import bcccp.tickets.season.IUsageRecordFactory;
import bcccp.tickets.season.SeasonTicketDAO;

public class SeasonTicketDAOTest {
    ISeasonTicketDAO sut;
    String ticketId = "S1234";
    ISeasonTicket ticket;
    IUsageRecordFactory usageRecordFactory;
    
    @Before
    public void setUp() {
        usageRecordFactory = mock(IUsageRecordFactory.class);
        sut = new SeasonTicketDAO(usageRecordFactory);
        ticket = mock(ISeasonTicket.class); 
    }
    
    @After
    public void tearDown() {
        sut = null;
    }

    @Test
	public void testInit() {
		assertTrue(ticket instanceof ISeasonTicket);
		assertTrue(usageRecordFactory instanceof IUsageRecordFactory);
	}
    
    @Test
    public void testRegisterTicket() {
       sut.registerTicket(ticket);
    }

    @Test
    public void testDeregisterTicket() {
        sut.deregisterTicket(ticket);
    }

    @Test
    public void testGetNumberOfTickets() {

        int expected = 0;
        int result = sut.getNumberOfTickets();
        assertEquals(expected, result);
        
        ISeasonTicket ticket = mock(ISeasonTicket.class);        
        sut.registerTicket(ticket);
        
        expected = 1;
        result = sut.getNumberOfTickets();
        assertEquals(expected, result);
    }

    @Test
    public void testFindTicketById() {
        ISeasonTicket ticket = mock(ISeasonTicket.class);  
        when(ticket.getId()).thenReturn(ticketId);
        
        sut.registerTicket(ticket);
        
        ISeasonTicket expected = null;
        ISeasonTicket result = sut.findTicketById("InvalidId");
        assertEquals(expected, result);
        
        expected = ticket;
        result = sut.findTicketById(ticketId);
        assertEquals(expected, result);
    }

    @Test
    public void testRecordTicketEntry() {
    	ISeasonTicket ticket = mock(ISeasonTicket.class);  
        when(ticket.getId()).thenReturn(ticketId);
        
        sut.registerTicket(ticket);
    	sut.recordTicketEntry(ticket.getId());
    }

    @Test
    public void testRecordTicketExit() { 
    	ISeasonTicket ticket = mock(ISeasonTicket.class);  
        when(ticket.getId()).thenReturn(ticketId);
        
        sut.registerTicket(ticket);
    	sut.recordTicketEntry(ticket.getId());
        sut.recordTicketExit(ticket.getId());
    }
    
}

