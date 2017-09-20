/*
 * This class save the usage record of each season ticket
 */

package bcccp.tickets.season;

public class UsageRecord implements IUsageRecord {
	
	private String ticketId_;
	private long startDateTime_;
	private long endDateTime_;
	
	
	
	public UsageRecord(String ticketId, long startDateTime) {
		//TODO Implement constructor
		ticketId_ = ticketId; 
		startDateTime_ = startDateTime;
	}



	@Override
	public void finalise(long endDateTime) {
		// TODO Auto-generated method stub
		endDateTime_ = endDateTime;
	}



	@Override
	public long getStartTime() {
		// TODO Auto-generated method stub
		return startDateTime_;
	}



	@Override
	public long getEndTime() {
		// TODO Auto-generated method stub
		return endDateTime_;
	}



	@Override
	public String getSeasonTicketId() {
		// TODO Auto-generated method stub
		return ticketId_;
	}
	
	public String toString() {
		return ("Ticket ID: " + ticketId_
				+ "Usage : startDateTime : " + startDateTime_ 
				+ ", endDateTime: " + endDateTime_);
	}
	
}
