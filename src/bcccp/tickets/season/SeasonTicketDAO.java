/* This class is implemented for registering/deregistering one season ticket 
 * When a new ticket is registered, ticket factory will create a list of usage for that ticket
 * no of tickets is incremented by 1 automatically
 * new season ticket will be added in the ticket list
 */

package bcccp.tickets.season;

import java.util.HashMap;
import java.util.Map;

import bcccp.tickets.season.ISeasonTicket;
import bcccp.tickets.season.IUsageRecordFactory;


public class SeasonTicketDAO implements ISeasonTicketDAO {

	private IUsageRecordFactory usageRecordFactory;
	private Map<String,ISeasonTicket> currentTickets; //create a map with ticketId and ticket
	
	
	public SeasonTicketDAO(IUsageRecordFactory factory) {
		//TOD Implement constructor
		this.usageRecordFactory = factory;
		currentTickets = new HashMap<>();
	}



	@Override
	public void registerTicket(ISeasonTicket ticket) {
		// TODO Auto-generated method stub
		if (!currentTickets.containsKey(ticket.getId())) {
			currentTickets.put(ticket.getId(),ticket);
		}
	}



	@Override
	public void deregisterTicket(ISeasonTicket ticket) {
		// TODO Auto-generated method stub
		if (currentTickets.containsKey(ticket.getId())) {
			currentTickets.remove(ticket.getId());
		}
	}



	@Override
	public int getNumberOfTickets() {
		// TODO Auto-generated method stub
		return currentTickets.size();
	}



	@Override
	public ISeasonTicket findTicketById(String barcode) {
		// TODO Auto-generated method stub
		if (currentTickets.containsKey(barcode)) {
			return currentTickets.get(barcode);
		}
		return null;
	}



	@Override
	public void recordTicketEntry(String ticketId) {
		// TODO Auto-generated method stub
		ISeasonTicket ticket = findTicketById(ticketId);
		if (ticket == null) throw new RuntimeException("recordTicketUsage : no such ticket: " + ticketId);
		IUsageRecord usageRecord = usageRecordFactory.make(ticketId, System.currentTimeMillis()); 
		// make new usage record from factory with system time
		ticket.recordUsage(usageRecord);
	}



	@Override
	public void recordTicketExit(String ticketId) {
		// TODO Auto-generated m.ethod stub
		ISeasonTicket ticket = findTicketById(ticketId);
		if (ticket == null) throw new RuntimeException("finaliseTicketUsage : no such ticket: " + ticketId);
		ticket.endUsage(System.currentTimeMillis()); // end usage of the usage record with system time
	}
	
	
	
}
