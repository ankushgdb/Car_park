/*
 * This class produce one usage each time for each ticket
 */

package bcccp.tickets.season;

public class UsageRecordFactory implements IUsageRecordFactory {

	@Override
	public IUsageRecord make(String ticketId, long startDateTime) {
		// TODO Auto-generated method stub
		IUsageRecord usageRecord = new UsageRecord(ticketId, startDateTime);
		return usageRecord;
	}


}
