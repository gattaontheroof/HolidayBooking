
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author agata
 */
public class Utility {

    public static int getWeekdayCount(LocalDate startDate, LocalDate endDate) {
        
        if(endDate.isBefore(startDate))
            return 0;
        
        // Code taken from Answer by Basil Bourque.
        // https://stackoverflow.com/a/40369140/642706
        int count = 1;
        Set< DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        LocalDate ld = startDate;
        while (ld.isBefore(endDate)) {
            if (!weekend.contains(ld.getDayOfWeek())) { // If not weekend, collect this LocalDate.
                count++;
            }
            // Prepare for next loop.
            ld = ld.plusDays(1); // Increment to next day.
        }
        return count;
    }
    
    /*
        Check if dates ranges overlap
    */
    public static boolean datesOverlap(LocalDate newStartDate, LocalDate newEndDate, 
            LocalDate existingStartDate, LocalDate existingEndDate) {
        
        // if new.end >= existing.start &&  new.start <= existing.end
        boolean overlapA = newEndDate.isAfter(existingStartDate) || newEndDate.equals(existingStartDate);
        boolean overlapB = newStartDate.isBefore(existingEndDate) || newStartDate.equals(existingEndDate);
        
        return overlapA && overlapB;
    }

}
