
import java.time.LocalDate;
import java.time.Month;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author agata
 */
public class EmployeeTest {

    private Employee employee;

    @Before
    public void setUp() {
        employee = new Employee(
                "EC0111",
                "123",
                "Bob",
                "Green",
                Department.IT,
                "Analyst",
                Gender.MALE,
                42,
                30
        );
    }

    @Test
    public void request_holiday_throws_when_start_date_is_a_weekend_day() {

        // arrange
        LocalDate startDate = LocalDate.of(2022, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2022, Month.JANUARY, 3);

        try {
            // act
            employee.requestHoliday(startDate, endDate);
            fail("expected exception did not occur");
        } catch (InvalidRequestException ex) {
        }
    }

    @Test
    public void request_holiday_throws_when_end_date_is_a_weekend_day() {

        // arrange
        LocalDate startDate = LocalDate.of(2022, Month.JANUARY, 3);
        LocalDate endDate = LocalDate.of(2022, Month.JANUARY, 8);

        try {
            // act
            employee.requestHoliday(startDate, endDate);
            fail("expected exception did not occur");
        } catch (InvalidRequestException ex) {
        }
    }

    @Test
    public void request_holiday_throws_when_end_days_reuested_less_than_zero() {

        // arrange
        LocalDate startDate = LocalDate.of(2022, Month.JANUARY, 8);
        LocalDate endDate = LocalDate.of(2022, Month.JANUARY, 3);

        try {
            // act
            employee.requestHoliday(startDate, endDate);
            fail("expected exception did not occur");
        } catch (InvalidRequestException ex) {
        }
    }

    @Test
    public void request_holiday_throws_when_days_requested_greated_than_days_remaining() {

        // arrange
        LocalDate startDate = LocalDate.of(2022, Month.JANUARY, 3);
        LocalDate endDate = LocalDate.of(2022, Month.MARCH, 1);

        try {
            // act
            employee.requestHoliday(startDate, endDate);
            fail("expected exception did not occur");
        } catch (InvalidRequestException ex) {
        }
    }

    @Test
    public void request_holiday_throws_when_new_request_overlaps_with_non_rejected_request() {

        // arrange
        LocalDate existingStartDate = LocalDate.of(2022, Month.JANUARY, 3);
        LocalDate existingEndDate = LocalDate.of(2022, Month.MARCH, 7);

        HolidayRequest existingRequest = new HolidayRequest(existingStartDate, existingEndDate,
                RequestStatus.APPROVED, "EC0027");
        employee.getHolidayRequests().add(existingRequest);

        LocalDate startDate = LocalDate.of(2022, Month.JANUARY, 4);
        LocalDate endDate = LocalDate.of(2022, Month.MARCH, 5);

        try {
            // act
            employee.requestHoliday(startDate, endDate);
            fail("expected exception did not occur");
        } catch (InvalidRequestException ex) {
        }
    }

    @Test
    public void request_holiday_can_create_new_holiday_request() {
        // arrange
        LocalDate startDate = LocalDate.of(2022, Month.JANUARY, 3);
        LocalDate endDate = LocalDate.of(2022, Month.JANUARY, 7);

        try {
            // act
            employee.requestHoliday(startDate, endDate);
        } catch (InvalidRequestException ex) {
        }

        // assert
        assertEquals(1, employee.getHolidayRequests().size());
    }
}
