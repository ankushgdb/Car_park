package bcccp.tickets.adhoc;


/**
 * A ticket dispensation utility class. The barcode is simple just A and the Hex of ticketNo 
 * (for easy retrieval of the ticket)
 */
public class AdhocTicketFactory implements IAdhocTicketFactory {

    @Override
    public IAdhocTicket make(String carparkId, int ticketNo) {
		String barcode = "A" + Integer.toHexString(ticketNo);
		return new AdhocTicket(carparkId, ticketNo, barcode);
	}
}

