/* This class is implemented for creating new ticket from the ticket factory.
 * When a new ticket is issued, a barcode is created
 * TicketNo is incremented by 1 automatically
 * New Ticket will be added in the ticket list
 */

package bcccp.tickets.adhoc;

import java.util.ArrayList;
import java.util.List;

public class AdhocTicketDAO  implements IAdhocTicketDAO  {

	private IAdhocTicketFactory factory;
	private int currentTicketNo;
	private List<IAdhocTicket> adhocTicketList = new ArrayList<IAdhocTicket>();
	

	public AdhocTicketDAO(IAdhocTicketFactory factory) {
		//TODO Implement constructor
		this.factory = factory;
	}



	@Override
	public IAdhocTicket createTicket(String carparkId) {
		// TODO Auto-generated method stub
		currentTicketNo++; // when a ticket is issued, ticketNo increments by 1
		IAdhocTicket adhocTicket = factory.make(carparkId, currentTicketNo); 
		// make ticket in factory with the new ticketNo
		adhocTicketList.add(adhocTicket);
		return adhocTicket;
	}



	@Override
	public IAdhocTicket findTicketByBarcode(String barcode) {
		// TODO Auto-generated method stub
		IAdhocTicket ticketFound = null;
		for (int i = 0; i < adhocTicketList.size(); i++) {
			if (barcode == adhocTicketList.get(i).getBarcode()) {
				ticketFound = adhocTicketList.get(i);
				break;
			}
		}
		return ticketFound;
	}



	@Override
	public List<IAdhocTicket> getCurrentTickets() {
		// TODO Auto-generated method stub
		List<IAdhocTicket> currentTickets = new ArrayList<IAdhocTicket>();
		for (int i = 0; i < currentTicketNo; i++) {
			if (currentTickets.get(i).isCurrent() == true) {
				currentTickets.add(currentTickets.get(i));
			}
		}
		return currentTickets;
	}

	
	
}

