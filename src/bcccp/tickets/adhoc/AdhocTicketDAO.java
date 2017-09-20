/* This class is implemented for creating new ticket from the ticket factory.
 * When a new ticket is issued, a barcode is created
 * TicketNo is incremented by 1 automatically
 * New Ticket will be added in the ticket list
 */

package bcccp.tickets.adhoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdhocTicketDAO  implements IAdhocTicketDAO  {

	private IAdhocTicketFactory adhocTicketFactory_;
	private int currentTicketNo_; // when car park start, ticketNo = 0, increment by 1 for each ticket
	private Map<String, IAdhocTicket> currentTickets; 
		// create the map of currentTicket, with String as barcode
	

	public AdhocTicketDAO(IAdhocTicketFactory adhocTicketFactory) {
		//TODO Implement constructor
		adhocTicketFactory_ = adhocTicketFactory;
		currentTickets = new HashMap<String, IAdhocTicket>();
		// currentTicketNo_ = 0; 
		// when a new AdhocTicketDAO object is set up, no ticket is recorded yet 
		// so currentTicketNo_=0
	}



	@Override
	public IAdhocTicket createTicket(String carparkId) {
		// TODO Auto-generated method stub
		if (carparkId == null) {
			throw new IllegalArgumentException("Carpark ID cannot be null");
		}
		IAdhocTicket adhocTicket = adhocTicketFactory_.make(carparkId, ++currentTicketNo_); 
		// make ticket in factory with the new ticketNo
		// when a ticket is issued, ticketNo increments by 1
		currentTickets.put(adhocTicket.getBarcode(), adhocTicket);
		return adhocTicket;
	}


	/* This method iterates every item in adhocTicketList 
	 * and check whether the ticket has the same barcode.
	 * If the ticket is the same, the search will stop and return the ticket 
	 * (non-Javadoc)
	 * @see bcccp.tickets.adhoc.IAdhocTicketDAO#getCurrentTickets()
	 */
	
	@Override
	public IAdhocTicket findTicketByBarcode(String barcode) {
		// TODO Auto-generated method stub
		return currentTickets.get(barcode);
	}


	/* This method generate the total tickets as in currentTickets
	 * @see bcccp.tickets.adhoc.IAdhocTicketDAO#getCurrentTickets()
	 */
	@Override
	public List<IAdhocTicket> getCurrentTickets() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(new ArrayList<IAdhocTicket>(currentTickets.values()));
	}

	
	
}

