import java.time.LocalDate;
import java.time.Month;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author agata
 */
public class UtilityTest {

    public UtilityTest() {
    }

    @Test
    public void weekday_count_returns_zero_when_end_date_is_before_start_date() {

        // arrange
        LocalDate startDate = LocalDate.of(2022, Month.JANUARY, 5);
        LocalDate endDate = LocalDate.of(2022, Month.JANUARY, 3);

        // act
        int count = Utility.getWeekdayCount(startDate, endDate);

        // assert
        assertEquals(0, count);
    }

    @Test
    public void weekday_count_returns_one_when_end_date_is_equal_to_start_date() {

        // arrange
        LocalDate startDate = LocalDate.of(2022, Month.JANUARY, 5);
        LocalDate endDate = LocalDate.of(2022, Month.JANUARY, 5);

        // act
        int count = Utility.getWeekdayCount(startDate, endDate);

        // assert
        assertEquals(1, count);
    }

    @Test
    public void weekday_count_should_exclude_weekend_days() {

        // arrange
        LocalDate startDate = LocalDate.of(2022, Month.JANUARY, 3);
        LocalDate endDate = LocalDate.of(2022, Month.JANUARY, 10);

        // act
        int count = Utility.getWeekdayCount(startDate, endDate);

        // assert
        assertEquals(6, count);
    }

    @Test
    public void dates_overlap_returns_false_when_new_occurs_before_existing() {

        // arrange
        LocalDate existingStartDate = LocalDate.of(2022, Month.MARCH, 1);
        LocalDate existingEndDate = LocalDate.of(2022, Month.MARCH, 5);
        LocalDate newStartDate = LocalDate.of(2022, Month.JANUARY, 3);
        LocalDate newEndDate = LocalDate.of(2022, Month.JANUARY, 5);

        // act
        boolean overlap = Utility.datesOverlap(newStartDate, newEndDate, existingStartDate, existingEndDate);

        // assert
        assertFalse(overlap);
    }

    @Test
    public void dates_overlap_returns_false_when_new_occurs_after_existing() {

        // arrange
        LocalDate existingStartDate = LocalDate.of(2022, Month.JANUARY, 3);
        LocalDate existingEndDate = LocalDate.of(2022, Month.JANUARY, 5);
        LocalDate newStartDate = LocalDate.of(2022, Month.MARCH, 1);
        LocalDate newEndDate = LocalDate.of(2022, Month.MARCH, 5);

        // act
        boolean overlap = Utility.datesOverlap(newStartDate, newEndDate, existingStartDate, existingEndDate);

        // assert
        assertFalse(overlap);
    }

    @Test
    public void dates_overlap_returns_true_when_new_start_overlaps_with_existing() {

         // arrange
        LocalDate existingStartDate = LocalDate.of(2022, Month.JUNE, 20);
        LocalDate existingEndDate = LocalDate.of(2022, Month.JUNE, 27);
        LocalDate newStartDate = LocalDate.of(2022, Month.JUNE, 24);
        LocalDate newEndDate = LocalDate.of(2022, Month.JULY, 1);

        // act
        boolean overlap = Utility.datesOverlap(newStartDate, newEndDate, existingStartDate, existingEndDate);

        // assert
        assertTrue(overlap);
    }
    @Test
    public void dates_overlap_returns_true_when_new_end_overlaps_with_existing() {

         // arrange
        LocalDate existingStartDate = LocalDate.of(2022, Month.JUNE, 20);
        LocalDate existingEndDate = LocalDate.of(2022, Month.JUNE, 27);
        LocalDate newStartDate = LocalDate.of(2022, Month.JUNE, 14);
        LocalDate newEndDate = LocalDate.of(2022, Month.JUNE, 25);

        // act
        boolean overlap = Utility.datesOverlap(newStartDate, newEndDate, existingStartDate, existingEndDate);

        // assert
        assertTrue(overlap);
    }
    
    @Test
    public void dates_overlap_returns_true_when_new_start_and_new_end_overlap_with_existing() {

         // arrange
        LocalDate existingStartDate = LocalDate.of(2022, Month.JUNE, 20);
        LocalDate existingEndDate = LocalDate.of(2022, Month.JUNE, 27);
        LocalDate newStartDate = LocalDate.of(2022, Month.JUNE, 20);
        LocalDate newEndDate = LocalDate.of(2022, Month.JUNE, 27);

        // act
        boolean overlap = Utility.datesOverlap(newStartDate, newEndDate, existingStartDate, existingEndDate);

        // assert
        assertTrue(overlap);
    }
}
