
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author agata
 */
public class Employee extends User implements Comparable<Employee> {

    private String name;
    private String surname;
    private Department department;
    private String position;
    private Gender gender;
    private int age;
    private int holsAllowance;
    private final List<HolidayRequest> holidayRequests;

    public List<HolidayRequest> getHolidayRequests() {
        Collections.sort(holidayRequests);
        return holidayRequests;
    }

    public Employee(String id, String password, String name, String surname, Department department,
            String position, Gender gender, int age, int holsAllowance) {
        super(id, password);
        this.name = name;
        this.surname = surname;
        this.department = department;
        this.position = position;
        this.gender = gender;
        this.age = age;
        this.holsAllowance = holsAllowance;
        this.holidayRequests = new ArrayList<>();
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;

    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHolsAllowance() {
        return holsAllowance;
    }

    public void setHolsAllowance(int holsAllowance) {
        this.holsAllowance = holsAllowance;
    }

    public boolean canReviewRequests() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (!this.getId().equals(other.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + getId() + ", name=" + name + ", surname=" + 
                surname + ", department=" + department + ", position=" + 
                position + ", gender" + gender + ", age" + age + ", holiday allowance" + 
                holsAllowance + '}';
    }

    @Override
    public int compareTo(Employee e) {
        return this.getId().compareTo(e.getId());
    }

    public HolidayRequest getRequest(LocalDate startDate) {
        for (HolidayRequest request : holidayRequests) {
            if(request.getStartDate().equals(startDate)) {
                return request;
            }
        }

        return null;
    }

    public int getHolidaysRemaining() {

        int holidaysRemaining = holsAllowance;
        for (HolidayRequest hr : holidayRequests) {
            if (!hr.getStatus().equals(RequestStatus.REJECTED)) {
                holidaysRemaining -= hr.getDaysRequested();
            }
        }

        return holidaysRemaining;
    }

    public void requestHoliday(LocalDate startDate, LocalDate endDate) throws InvalidRequestException {
        
        // check start date not on the weekend
        DayOfWeek startDay = startDate.getDayOfWeek();
        if (startDay.equals(DayOfWeek.SATURDAY) || startDay.equals(DayOfWeek.SUNDAY)) {
            throw new InvalidRequestException("Holiday can't start on the weekend");
        }

        // check end date not on the weekend
        DayOfWeek endDay = endDate.getDayOfWeek();
        if (endDay.equals(DayOfWeek.SATURDAY) || endDay.equals(DayOfWeek.SUNDAY)) {
            throw new InvalidRequestException("Holiday can't end on the weekend");
        }

        // check number of days requested is greater than 0
        int daysRequested = Utility.getWeekdayCount(startDate, endDate);
        if (daysRequested <= 0) {
            throw new InvalidRequestException("Days requested should be greater than 0");
        }

        // check that we have enough holidays remaining
        if (getHolidaysRemaining() - daysRequested < 0) {
            throw new InvalidRequestException("Not enough holidays remaining");
        }

        // check for overlapping with all existing non rejected requests
        for (HolidayRequest hr : holidayRequests) {
            if (!hr.getStatus().equals(RequestStatus.REJECTED)) {
                if (Utility.datesOverlap(startDate, endDate, hr.getStartDate(), hr.getEndDate())) {
                    throw new InvalidRequestException("Request overlaps with existing holiday request");
                }
            }
        }

        // add the new holiday request
        holidayRequests.add(new HolidayRequest(startDate, endDate));
    }

}
