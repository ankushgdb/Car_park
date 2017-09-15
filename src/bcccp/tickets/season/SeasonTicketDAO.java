/* This class is implemented for registering/deregistering one season ticket 
 * When a new ticket is registered, ticket factory will create a list of usage for that ticket
 * no of tickets is incremented by 1 automatically
 * new season ticket will be added in the ticket list
 */

package bcccp.tickets.season;

import java.util.ArrayList;
import java.util.List;

import bcccp.tickets.season.ISeasonTicket;
import bcccp.tickets.season.IUsageRecordFactory;


public class SeasonTicketDAO implements ISeasonTicketDAO {

	private IUsageRecordFactory factory;
	private List<ISeasonTicket> seasonTicketList = new ArrayList<ISeasonTicket>();
	
	
	public SeasonTicketDAO(IUsageRecordFactory factory) {
		//TOD Implement constructor
		this.factory = factory;
	}



	@Override
	public void registerTicket(ISeasonTicket ticket) {
		// TODO Auto-generated method stub
		seasonTicketList.add(ticket);
	}



	@Override
	public void deregisterTicket(ISeasonTicket ticket) {
		// TODO Auto-generated method stub
		seasonTicketList.remove(ticket);
	}



	@Override
	public int getNumberOfTickets() {
		// TODO Auto-generated method stub
		return seasonTicketList.size();
	}



	@Override
	public ISeasonTicket findTicketById(String ticketId) {
		// TODO Auto-generated method stub
		ISeasonTicket ticketFound = null;
		for (int i = 0; i<seasonTicketList.size(); i++) {
			if (seasonTicketList.get(i).getId()== ticketId) {
				ticketFound = seasonTicketList.get(i);
			}
		}
		return ticketFound;
	}



	@Override
	public void recordTicketEntry(String ticketId) {
		// TODO Auto-generated method stub
		factory.make(ticketId, System.currentTimeMillis()); // make new usage record from factory with system time
	}



	@Override
	public void recordTicketExit(String ticketId) {
		// TODO Auto-generated m.ethod stub
		findTicketById(ticketId).endUsage(System.currentTimeMillis()); // end usage of the usage record with system time
	}
	
	
	
}
