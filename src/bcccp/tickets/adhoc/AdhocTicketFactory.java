/* This class create a barcode of ticket with carparkId and ticketNo are parsed as parameters
 * The barcode is the form: “A” + hexstring representation of ticket number 
 * + hextring representation of entry date and time.
 * 
 */

package bcccp.tickets.adhoc;

import java.util.Date;

public class AdhocTicketFactory implements IAdhocTicketFactory {

	@Override
	public IAdhocTicket make(String carparkId, int ticketNo) {
		// TODO Auto-generated method stub
		if (carparkId == null) {
			throw new IllegalArgumentException("Carpark ID cannot be null");
		}
		if (ticketNo <=0) {
			throw new RuntimeException("Ticket number must be a positive number");
		}
		Long entryDateTime = new Date().getTime();
		String barcode = 'A' + Integer.toHexString(ticketNo) + Long.toHexString(entryDateTime);
		IAdhocTicket adhocTicket = new AdhocTicket (carparkId, ticketNo, barcode);
		adhocTicket.enter(entryDateTime); // record entry date & time
		return adhocTicket;
	}


}
