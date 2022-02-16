import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author agata
 */
public class HolidayBooking {

    private List<Employee> employees;
    private final IOFile iof;
    private static final String EMPLOYEE_FILE = "EmployeeData.txt";
    private static final String HOLIDAYREQUEST_FILE = "HolidayRequestData.txt";
    private static final String HOLIDAYREQUESTLOG_FILE = "HolidayRequestLog.csv";
    
    public HolidayBooking() {
        employees = new ArrayList();
        iof = new IOFile();
        loadEmployees(EMPLOYEE_FILE);
        loadHolidayRequests(HOLIDAYREQUEST_FILE);
    }
    
    public HolidayBooking(String employeeFile, String holidayRequestFile) {
        employees = new ArrayList();
        iof = new IOFile();
        loadEmployees(employeeFile);
        loadHolidayRequests(holidayRequestFile);
    }

    private void loadEmployees(String filename) {
        employees = iof.getEmployees(filename);
    }

    private void loadHolidayRequests(String filename) {
        iof.populateHolidayRequests(filename, employees);
    }
    
    public void writeEmployees() {
        iof.writeEmployees(EMPLOYEE_FILE, employees);
        iof.writeHolidayRequests(HOLIDAYREQUEST_FILE, employees);
    }
    
    public void exportToExcel(String id){
        
        Employee employee = getEmployee(id);
        
        if(id != null)
            iof.exportToExcel(HOLIDAYREQUESTLOG_FILE, employee);
    }

    /*
        Returns sorted list of all employees
     */
    public List<Employee> getEmployees() {
        Collections.sort(employees);
        return employees;
    }

    /*
        get employee by Id
     */
    public Employee getEmployee(String id) {
        for (Employee e : employees) {
            if (e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }

    public void addEmployee(String id, String password, String name, String surname, Department department,
            String position, Gender gender, int age, int holsAllowance) throws InvalidUserException {

        // validate the fields to prevent adding empty content (Strings)
        if (("".equals(position)) || ("".equals(id)) || ("".equals(name)) || ("".equals(surname)) || ("".equals(password))) {
            //return message as String
            throw new InvalidUserException("Please anter a valid ID, Password, Name, Surname and Position!");
        }

        //validate the allowed age span        
        if (age < 16 || age > 75) {
            throw new InvalidUserException("The employee must be between 16 and 75 yearsof age.");
        }

        // validate that ID is unique 
        for (Employee e : employees) {
            if (e.getId().equals(id)) {
                throw new InvalidUserException("This employee id already exists");
            }
        }

        // construct new employee object   
        Employee emp = new Employee(id, password, name, surname, department, position, gender, age, holsAllowance);
        //add it to the arrayList
        employees.add(emp);
    }

    public void removeEmployee(String employeeId) {

        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee emp = iterator.next();
            if (emp.getId().equals(employeeId)) {
                iterator.remove();
                break;
            }
        }

    }

    // placeholders - may not live here
    public Employee login(String id, String password) throws LoginException {

        // get the employee
        Employee employee = getEmployee(id);

        if (employee == null) {
            throw new LoginException("Employee not found");
        }

        if (!employee.login(password)) {
            throw new LoginException("Invalid details");
        }

        return employee;
    }

    public void approveRequest(Employee approver, String id, LocalDate startDate) throws InvalidApproverException {

        // check if employee can review requests
        if (!approver.canReviewRequests()) {
            throw new InvalidApproverException("No permission to review the request");
        }

        // get the employee
        Employee requester = getEmployee(id);
        if (requester == null) {
            throw new InvalidApproverException("No such employee on the record");
        }

        // get the holiday request
        HolidayRequest request = requester.getRequest(startDate);
        if (request == null) {
            throw new InvalidApproverException("No holiday request exists");
        }

        request.approve(approver.getId());
    }

    public void rejectRequest(Employee approver, String id, LocalDate startDate) throws InvalidApproverException {
        // check if employee can review requests
        if (!approver.canReviewRequests()) {
            throw new InvalidApproverException("No permission to review the request");
        }

        // get the employee
        Employee requester = getEmployee(id);
        if (requester == null) {
            throw new InvalidApproverException("No such employee on the record");
        }

        // get the holiday request
        HolidayRequest request = requester.getRequest(startDate);
        if (request == null) {
            throw new InvalidApproverException("No holiday request exists");
        }

        request.reject(approver.getId());
    }
}
