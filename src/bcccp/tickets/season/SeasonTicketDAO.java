package bcccp.tickets.season;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class SeasonTicketDAO implements ISeasonTicketDAO {

	private List<ISeasonTicket> seasonTickets;
	private IUsageRecordFactory factory;

	/**
	 * This class records the usage of Season Tickets
	 *
	 * @param factory factory for making Season Ticket usage records
	 */
	public SeasonTicketDAO(IUsageRecordFactory factory) {

		this.factory = factory;

		seasonTickets = new ArrayList<>();
	}

	@Override
	public void registerTicket(ISeasonTicket ticket) {
		if(!seasonTickets.contains(ticket)){
            seasonTickets.add(ticket);
        }
	}

	@Override
	public void deregisterTicket(ISeasonTicket ticket) {
		if(seasonTickets.contains(ticket)){
            seasonTickets.remove(ticket);
        }
	}

	@Override
	public int getNumberOfTickets() {
		return seasonTickets.size();
	}

	@Override
	public ISeasonTicket findTicketById(String ticketId) {
		Iterator<ISeasonTicket> sTicketRecs = seasonTickets.iterator();
		while (sTicketRecs.hasNext()) {
			ISeasonTicket sTicket = sTicketRecs.next();
			if (ticketId.contentEquals(sTicket.getId())) { // fix line
				return sTicket;
			}
		}
		return null;
	}


	@Override
	public void recordTicketEntry(String ticketId) throws RuntimeException {

		// This method creates a new usage record with current day and time as the startTime
		// and uses recordUsage method from SeasonTicket class to record it to the ArrayList
		ISeasonTicket ticket = findTicketById(ticketId);
		if ( ticket != null) {
			Date entryDateTime = new Date();
			IUsageRecord usage = factory.make(ticketId, entryDateTime.getTime());
            ticket.recordUsage(usage);
		} else {
			throw new RuntimeException("Runtime Exception: No corresponding ticket.");
		}

	}

	@Override
	public void recordTicketExit(String ticketId) {
		// Finds an existing usage record and records the current day and time (on exiting of vehicle)
		// on the record
		ISeasonTicket ticket = findTicketById(ticketId);
		if ( ticket != null) {
			Date exitDateTime = new Date();
            ticket.endUsage(exitDateTime.getTime());
		} else {
			throw new RuntimeException("Runtime Exception: No corresponding ticket.");
		}
	}

}

