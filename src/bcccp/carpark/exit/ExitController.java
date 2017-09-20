package bcccp.carpark.exit;

import bcccp.carpark.Carpark;
import bcccp.carpark.ICarSensor;
import bcccp.carpark.ICarSensorResponder;
import bcccp.carpark.ICarpark;
import bcccp.carpark.IGate;
import bcccp.carpark.exit.ExitController.STATE;
import bcccp.tickets.adhoc.IAdhocTicket;
import java.util.Date;

public class ExitController implements ICarSensorResponder, IExitController {

  static final long FIFTEEN_MINUTES = 900000; //fifteen minutes = 900000 milliseconds
  private IGate exitGate;
  private ICarSensor insideSensor;
  private ICarSensor outsideSensor;
  private IExitUI ui;
  private ICarpark carpark;
  private IAdhocTicket adhocTicket = null;
  private long exitTime;
  private String seasonTicketId = null;

    private enum STATE {
        IDLE,
        WAITING,
        PROCESSED,
        REJECTED,
        TAKEN,
        EXITING,
        EXITED,
        BLOCKED
    }

    private STATE state;
    private STATE prevState;
    private String message;
/*
 * State is iddle
 * */
	

    public ExitController(Carpark carpark, IGate exitGate, ICarSensor is, ICarSensor os, IExitUI ui) {

        if (carpark != null && exitGate != null && os != null && is != null && ui != null) {

            this.carpark = carpark;

            this.exitGate = exitGate;

            insideSensor = is;

            outsideSensor = os;

            this.ui = ui;

            os.registerResponder(this);

            is.registerResponder(this);

            ui.registerController(this);

            prevState = STATE.IDLE;

            setState(STATE.IDLE);

        } else {

            throw new RuntimeException("Arguments cannot be null.");
        }
    }

/*
 * Add log method
 */
private void log(String message) {
	System.out.println("ExitController : " + message);
}

/*
 * check car
 * 
 * all state checked with switch
 * */
@Override
public void carEventDetected(String detectorId, boolean carDetected) {

	log("carEventDetected: " + detectorId + ", car Detected: " + carDetected );
	
	switch (state) {
	
	case BLOCKED: 
		if (detectorId.equals(is.getId()) && !carDetected) {
			setState(prevState);
		}
		break;
		
	case IDLE: 
		log("eventDetected: IDLE");
		if (detectorId.equals(is.getId()) && carDetected) {
			log("eventDetected: setting state to WAITING");
			setState(STATE.WAITING);
		}
		else if (detectorId.equals(os.getId()) && carDetected) {
			setState(STATE.BLOCKED);
		}
		break;
		
	case WAITING: 
	case PROCESSED: 
		if (detectorId.equals(is.getId()) && !carDetected) {
			setState(STATE.IDLE);
		}
		else if (detectorId.equals(os.getId()) && carDetected) {
			setState(STATE.BLOCKED);
		}
		break;
		
	case TAKEN: 
		if (detectorId.equals(is.getId()) && !carDetected) {
			setState(STATE.IDLE);
		}
		else if (detectorId.equals(os.getId()) && carDetected) {
			setState(STATE.EXITING);
		}
		break;
		
	case EXITING: 
		if (detectorId.equals(is.getId()) && !carDetected) {
			setState(STATE.EXITED);
		}
		else if (detectorId.equals(os.getId()) && !carDetected) {
			setState(STATE.TAKEN);
		}
		break;
		
	case EXITED: 
		if (detectorId.equals(is.getId()) && carDetected) {
			setState(STATE.EXITING);
		}
		else if (detectorId.equals(os.getId()) && !carDetected) {
			setState(STATE.IDLE);
		}
		break;
		
	default: 
		break;
		
	}
	
}

/*
 * State setting. All state should be stated
 * */

private void setState(STATE newState) {
	switch (newState) {
	
	case BLOCKED: 
		log("setState: BLOCKED");
		prevState = state;
		//prevMessage = message;
		state = STATE.BLOCKED;
		message = "Blocked";
		ui.display(message);
		break;
		
	case IDLE: 
		log("setState: IDLE");
		if (prevState == STATE.EXITED) {
			if (adhocTicket != null) {
				adhocTicket.exit(exitTime);
				carpark.recordAdhocTicketExit();
				log(adhocTicket.toString() );
			}
			else if (seasonTicketId != null) {
				carpark.recordSeasonTicketExit(seasonTicketId);
			}
		}
		adhocTicket = null;
		seasonTicketId = null;
		
		message = "Idle";
		state = STATE.IDLE;
		//prevMessage = message;
		prevState = state;
		ui.display(message);
		if (is.carIsDetected()) {
			setState(STATE.WAITING);
		}
		if (exitGate.isRaised()) {
			exitGate.lower();
		}
		exitTime = 0;
		break;
		
	case WAITING: 
		log("setState: WAITING");
		message = "Insert Ticket";
		state = STATE.WAITING;
		//prevMessage = message;
		prevState = state;
		ui.display(message);
		if (!is.carIsDetected()) {
			setState(STATE.IDLE);
		}
		break;
		
	case PROCESSED: 
		log("setState: PROCESSED");
		message = "Take Processed Ticket";
		state = STATE.PROCESSED;
		//prevMessage = message;
		prevState = state;
		ui.display(message);
		if (!is.carIsDetected()) {
			setState(STATE.IDLE);
		}
		break;
		
	case REJECTED: 
		log("setState: REJECTED");
		message = "Take Rejected Ticket";
		state = STATE.REJECTED;
		//prevMessage = message;
		prevState = state;
		ui.display(message);
		if (!is.carIsDetected()) {
			setState(STATE.IDLE);
		}
		break;
		
	case TAKEN: 
		log("setState: TAKEN");
		message = "Ticket Taken";
		state = STATE.TAKEN;
		//prevMessage = message;
		prevState = state;
		ui.display(message);
		break;
		
	case EXITING: 
		log("setState: EXITING");
		message = "Exiting";
		state = STATE.EXITING;
		//prevMessage = message;
		prevState = state;
		ui.display(message);
		break;
		
	case EXITED: 
		log("setState: EXITED");
		message = "Exited";
		state = STATE.EXITED;
		//prevMessage = message;
		prevState = state;
		ui.display(message);
		break;
		
	default: 
		break;
		
	}
			
}
private boolean isAdhocTicket(String barcode) {
	return barcode.substring(0,1).equals("A");
}

/*
 * Waiting ticket
 * state waiting as well
 * exiting time saved
 * 
 * if it is not valid beep signal and rejected state
 * 
 * otherwise beep and discard ticket
 * */

@Override
public void ticketInserted(String ticketStr) {
	if (state == STATE.WAITING) {
		if (isAdhocTicket(ticketStr)) {
			adhocTicket = carpark.getAdhocTicket(ticketStr);
			exitTime = System.currentTimeMillis();
			if (adhocTicket != null && adhocTicket.isPaid()) {
				setState(STATE.PROCESSED);
			}
			else {
				ui.beep();
				setState(STATE.REJECTED);						
			}
		}
		else if (carpark.isSeasonTicketValid(ticketStr) &&
				 carpark.isSeasonTicketInUse(ticketStr)){					
			seasonTicketId = ticketStr;
			setState(STATE.PROCESSED);
		}
		else {
			ui.beep();
			setState(STATE.REJECTED);						
		}
	}
	else {
		ui.beep();
		ui.discardTicket();
		log("ticketInserted: called while in incorrect state");
		setState(STATE.REJECTED);						
	}
	
}


/*
 * ticketTacken 
 * Gate open
 * set state taken
 * 
 * otherwise beep signal
 */
@Override
public void ticketTaken() {
	if (state == STATE.PROCESSED)  {
		exitGate.raise();
		setState(STATE.TAKEN);
	}
	else if (state == STATE.REJECTED) {
		setState(STATE.WAITING);
	}
	else {
		ui.beep();
		log("ticketTaken: called while in incorrect state");
	}
	
}






}
