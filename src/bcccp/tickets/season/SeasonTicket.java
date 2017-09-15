/*
 * This class creates a season ticket 
 * and record each usage in an array list for each ticket holder
 */

package bcccp.tickets.season;

// import java.util.ArrayList;
// import java.util.Collections;
import java.util.List;

// import bcccp.tickets.adhoc.IAdhocTicket; 

public class SeasonTicket implements ISeasonTicket {
	
	private List<IUsageRecord> usages;
	private IUsageRecord currentUsage = null;
	
	private String ticketId;
	private String carparkId;
	private long startValidPeriod;
	private long endValidPeriod;
	
	public SeasonTicket (String ticketId, 
			             String carparkId, 
			             long startValidPeriod,
			             long endValidPeriod) {
		//TDO Implement constructor
		ticketId = this.ticketId;
		carparkId = this.carparkId;
		startValidPeriod = this.startValidPeriod;
		endValidPeriod = this.endValidPeriod;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return ticketId;
	}

	@Override
	public String getCarparkId() {
		// TODO Auto-generated method stub
		return carparkId;
	}

	@Override
	public long getStartValidPeriod() {
		// TODO Auto-generated method stub
		return startValidPeriod;
	}

	@Override
	public long getEndValidPeriod() {
		// TODO Auto-generated method stub
		return endValidPeriod;
	}

	/* This method checks whether the ticket is used at the moment, i.e. currentUsage is null or not
	 * (non-Javadoc)
	 * @see bcccp.tickets.season.ISeasonTicket#inUse()
	 */
	@Override
	public boolean inUse() {
		// TODO Auto-generated method stub
		if (currentUsage != null) {
			return true;
		}
		else return false;
	}

	@Override
	public void recordUsage(IUsageRecord record) {
		// TODO Auto-generated method stub
		usages.add(record);
	}

	@Override
	public IUsageRecord getCurrentUsageRecord() {
		// TODO Auto-generated method stub
		return currentUsage;
	}
/* This will finalise the ticket and add usage to the usage list
 * (non-Javadoc)
 * @see bcccp.tickets.season.ISeasonTicket#endUsage(long)
 */
	@Override
	public void endUsage(long dateTime) {
		// TODO Auto-generated method stub
		currentUsage.finalise(dateTime); // finalise usage
		recordUsage(currentUsage); // add usage to usage list
		currentUsage = null; // return usage as null for next time usage
	}

	@Override
	public List<IUsageRecord> getUsageRecords() {
		// TODO Auto-generated method stub
		return usages;
	}


}
