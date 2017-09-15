/*
 * This class save the usage record of each season ticket
 */

package bcccp.tickets.season;

public class UsageRecord implements IUsageRecord {
	
	String ticketId;
	long startDateTime;
	long endDateTime;
	
	
	
	public UsageRecord(String ticketId, long startDateTime) {
		//TODO Implement constructor
		ticketId = this.ticketId; 
		startDateTime = this.startDateTime;
	}



	@Override
	public void finalise(long endDateTime) {
		// TODO Auto-generated method stub
		endDateTime = this.endDateTime;
	}



	@Override
	public long getStartTime() {
		// TODO Auto-generated method stub
		return startDateTime;
	}



	@Override
	public long getEndTime() {
		// TODO Auto-generated method stub
		return endDateTime;
	}



	@Override
	public String getSeasonTicketId() {
		// TODO Auto-generated method stub
		return ticketId;
	}
	
	
	
}
