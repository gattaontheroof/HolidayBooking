import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author agata
 */
public class HolidayBookingTest {

    HolidayBooking holidayBooking;

    String id;
    String password;
    String name;
    String surname;
    Department department;
    String position;
    Gender gender;
    int age;
    int holsAllowance;

    @Before
    public void setUp() {
        holidayBooking = new HolidayBooking("TestEmployeeData.txt", "TestHolidayRequestData.txt");

        id = "EC0005";
        password = "123";
        name = "Bob";
        surname = "Green";
        department = Department.IT;
        position = "Analyst";
        gender = Gender.MALE;
        age = 42;
        holsAllowance = 30;
    }

    @Test
    public void employees_are_loaded_from_file() {

        // assert employees loaded
        assertEquals(3, holidayBooking.getEmployees().size());
    }

    @Test
    public void get_employee_can_return_employee_by_id() {

        // arrrange
        id = "EC0012";

        // act
        Employee e = holidayBooking.getEmployee(id);

        // assert
        assertNotNull(e);
    }

    @Test
    public void add_employee_throws_when_id_is_empty() {

        // arrange
        id = "";

        try {
            // act
            holidayBooking.addEmployee(id, password, name, surname, department, 
                    position, gender, age, holsAllowance);
            fail();
        } catch (InvalidUserException ex) {
        }
    }

    @Test
    public void add_employee_throws_when_age_less_than_16() {

        // arrange
        age = 10;

        try {
            // act
            holidayBooking.addEmployee(id, password, name, surname, department,
                    position, gender, age, holsAllowance);
            fail();
        } catch (InvalidUserException ex) {
        }
    }

    @Test
    public void add_employee_throws_when_id_is_not_unique() {

        // arrange
        id = "EC0001";

        try {
            // act
            holidayBooking.addEmployee(id, password, name, surname, department, 
                    position, gender, age, holsAllowance);
            fail();
        } catch (InvalidUserException ex) {
        }
    }

    @Test
    public void add_employee_can_add_new_employee() {

        try {
            // act
            holidayBooking.addEmployee(id, password, name, surname, department, 
                    position, gender, age, holsAllowance);

            // assert
            assertEquals(4, holidayBooking.getEmployees().size());
        } catch (InvalidUserException ex) {
            fail();
        }
    }

}
