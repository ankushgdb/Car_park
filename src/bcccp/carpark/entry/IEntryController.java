package bcccp.carpark.entry;

public interface IEntryController {
	
	public void buttonPushed();
	public void ticketInserted(String barcode);
	public void ticketTaken();
	public String getState();
	public void notifyCarparkEvent(); 

}
