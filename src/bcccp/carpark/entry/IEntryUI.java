package bcccp.carpark.entry;

public interface IEntryUI {
	public void registerController(IEntryController controller);
	public void deregisterController();
	
	public void display(String message);
	public void printTicket(String detail);
	public boolean ticketPrinted();
	public void discardTicket();
	public void beep();

}