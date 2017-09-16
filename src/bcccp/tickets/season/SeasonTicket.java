/*
 * This class creates a season ticket 
 * and record each usage in an array list for each ticket holder
 */

package bcccp.tickets.season;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// import bcccp.tickets.adhoc.IAdhocTicket; 

public class SeasonTicket implements ISeasonTicket {
	
	private List<IUsageRecord> usages;
	private IUsageRecord currentUsage = null;
	
	private String ticketId_;
	private String carparkId_;
	private long startValidPeriod_;
	private long endValidPeriod_;
	
	public SeasonTicket (String ticketId, 
			             String carparkId, 
			             long startValidPeriod,
			             long endValidPeriod) {
		//TDO Implement constructor
		ticketId_ = ticketId;
		carparkId_ = carparkId;
		startValidPeriod_ = startValidPeriod;
		endValidPeriod_ = endValidPeriod;
		
		usages = new ArrayList<IUsageRecord>();
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return ticketId_;
	}

	@Override
	public String getCarparkId() {
		// TODO Auto-generated method stub
		return carparkId_;
	}

	@Override
	public long getStartValidPeriod() {
		// TODO Auto-generated method stub
		return startValidPeriod_;
	}

	@Override
	public long getEndValidPeriod() {
		// TODO Auto-generated method stub
		return endValidPeriod_;
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
	public void recordUsage(IUsageRecord usageRecord) {
		// TODO Auto-generated method stub
		currentUsage = usageRecord;
		if (!usages.contains(usageRecord)) {
			usages.add(usageRecord);
		}
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
		if (currentUsage == null) {
			throw new RuntimeException("SeasonTicket.endUsage : ticket is not in use");
		}	
		currentUsage.finalise(dateTime); // finalise usage
		currentUsage = null; // return usage as null for next time usage
	}

	@Override
	public List<IUsageRecord> getUsageRecords() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(usages);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Carpark    : " + carparkId_ + "\n" +
		       "Ticket No  : " + ticketId_ + "\n" );
		for (IUsageRecord usage : usages) {
			builder.append(usage.toString() + "\n");
		}
		return builder.toString();
	}


}
