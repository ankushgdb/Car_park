package bcccp.carpark.exit;

public interface IExitController {
	public void ticketInserted(String ticketStr);
	public void ticketTaken();
	public String getStateAsString();
	public String getPrevStateAsString();
	public void carEventDetected(String detectorId, boolean detected); 

}
