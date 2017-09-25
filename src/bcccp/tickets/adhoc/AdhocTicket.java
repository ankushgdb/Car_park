package bcccp.tickets.adhoc;


import java.util.Date;


/**
 * A ticket for casual carpark users.
 */
public class AdhocTicket implements IAdhocTicket {


    private String carparkId;
    private int ticketNo;
    private long entryDateTime;
    private long paidDateTime;
    private long exitDateTime;
    private float charge;
    private String barcode;
    private STATE state;

    private enum STATE {
        ISSUED,
        CURRENT,
        PAID,
        EXITED
    }

    /**
     * A ticket for casual carpark clients.
     *
     * @param carparkId the carpark. Cannot be null or empty.
     * @param ticketNo  the ticket number. Cannot be zero or negative.
     * @param barcode   the string of values encoded by the barcode. Cannot be null or empty.
     */
    public AdhocTicket(String carparkId, int ticketNo, String barcode) {
    	if (isValue(carparkId) && isValidID(ticketNo) && isValue(barcode)) { {
            this.carparkId = carparkId;
            this.ticketNo = ticketNo;
            this.barcode = barcode;
            entryDateTime = new Date().getTime();
            state = STATE.ISSUED;

        } else {
            throw new IllegalArgumentException(
                    "Invalid Input: check that the arguments passed to the constructor " + "are valid.");
        }
    }

    @Override
    public int getTicketNo() {
        return ticketNo;
    }

    @Override
    public String getBarcode() {
        return barcode;
    }

    @Override
    public String getCarparkId() {
        return carparkId;
    }

    @Override
    public void enter(long dateTime) {
        entryDateTime = dateTime;
        state = STATE.CURRENT;


    }

    @Override
    public long getEntryDateTime() {
        return entryDateTime;
    }

    @Override
    public boolean isCurrent() {
        return state == STATE.CURRENT;
    }

    @Override
    public void pay(long dateTime, float charge) {
        paidDateTime = dateTime;
        this.charge = charge;
        state = STATE.PAID;
    }

    @Override
    public long getPaidDateTime() {
        return paidDateTime;
    }

    @Override
    public boolean isPaid() {
        return state == STATE.PAID;
    }

    @Override
    public float getCharge() {
        return charge;
    }

    public String toString() {
        Date entryDate = new Date(entryDateTime);
        Date paidDate = new Date(paidDateTime);
        Date exitDate = new Date(exitDateTime);

        return "Carpark: " + carparkId + "\n"
                + "Ticket No: " + ticketNo + "\n"
                + "Entry Time: " + entryDate + "\n"
                + "State: " + state + "\n"
                + "Barcode: " + barcode;
    }

    @Override
    public void exit(long dateTime) {
        exitDateTime = dateTime;
        state = STATE.EXITED;
    }

    @Override
    public long getExitDateTime() {
        return exitDateTime;
    }

    @Override
    public boolean hasExited() {
        return state == STATE.EXITED;
    }
    
    private Boolean isValue(String str) {
        return (str != null && !str.isEmpty());
    }
    private Boolean isValidID(int id) {
        return id > 0;
    }

  

}