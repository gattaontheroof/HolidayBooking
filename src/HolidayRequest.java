import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author agata
 */
public class HolidayRequest implements Comparable<HolidayRequest> {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int daysRequested;
    private RequestStatus status;
    private String approverId;

    public HolidayRequest(LocalDate startDate, LocalDate returnDate) {
        this.startDate = startDate;
        this.endDate = returnDate;
        daysRequested = Utility.getWeekdayCount(startDate, returnDate);

        status = RequestStatus.PENDING;
        approverId = "-";
    }
    
    public HolidayRequest(LocalDate startDate, LocalDate returnDate, RequestStatus status, String approverId) {
         this.startDate = startDate;
        this.endDate = returnDate;
        daysRequested = Utility.getWeekdayCount(startDate, returnDate);

        this.status = status;
        this.approverId = approverId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getApproverId() {
        return approverId;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getDaysRequested() {
        return daysRequested;
    }

    public void approve(String approverId) {
        this.approverId = approverId;
        status = RequestStatus.APPROVED;
    }

    public void reject(String approverId) {
        this.approverId = approverId;
        status = RequestStatus.REJECTED;
    }
         
    // Desired date pattern (format)
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    @Override
    public int compareTo(HolidayRequest hr) {
        return this.getStartDate().compareTo(hr.getStartDate());
    }

}