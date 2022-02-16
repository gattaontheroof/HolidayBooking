
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author agata
 */
public class IOFile {

    public IOFile() {
    }

    public List<Employee> getEmployees(String filename) {
        List<Employee> arrList = new ArrayList<>();

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String data;

            while ((data = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(data, ",");

                while (st.hasMoreTokens()) {

                    String id = st.nextToken().trim();
                    String password = st.nextToken().trim();
                    String name = st.nextToken().trim();
                    String surname = st.nextToken().trim();
                    String departmentString = st.nextToken().trim().toUpperCase();
                    Department department = Department.valueOf(departmentString);
                    String position = st.nextToken().trim();
                    String genderString = st.nextToken().trim().toUpperCase();
                    Gender gender = Gender.valueOf(genderString);
                    int age = (Integer.parseInt(st.nextToken().trim()));
                    int holsAllowance = (Integer.parseInt(st.nextToken().trim()));
                    char isManager = st.nextToken().trim().charAt(0);

                    Employee emp;

                    if (isManager == 'N') {
                        emp = new Employee(id, password, name, surname, department,
                                position, gender, age, holsAllowance);
                    } else {
                        emp = new Manager(id, password, name, surname, department,
                                position, gender, age, holsAllowance);
                    }

                    arrList.add(emp);
                }
            }
            br.close();

        } catch (IOException e) {
            System.out.println("Error loading file");
        }

        Employee result[] = new Employee[arrList.size()];

        return arrList;
    }

    public void populateHolidayRequests(String filename, List<Employee> employees) {

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String data;

            while ((data = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(data, ",");

                while (st.hasMoreTokens()) {

                    String id = st.nextToken().trim();

                    // get the employee
                    Employee employee = null;
                    for (Employee e : employees) {
                        if (e.getId().equalsIgnoreCase(id)) {
                            employee = e;
                        }
                    }

                    LocalDate startDate = LocalDate.parse(st.nextToken().trim());
                    LocalDate endDate = LocalDate.parse(st.nextToken().trim());
                    String statusString = st.nextToken().trim().toUpperCase();
                    RequestStatus status = RequestStatus.valueOf(statusString);
                    String approverId = st.nextToken().trim();

                    HolidayRequest request = new HolidayRequest(startDate, endDate, status, approverId);

                    employee.getHolidayRequests().add(request);
                }
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error loading file");
        }

    }

    public void writeEmployees(String employeesFile, List<Employee> employees) {

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(employeesFile));
            for (Employee e : employees) {

                String id = e.getId();
                String password = e.getPassword();
                String name = e.getName();
                String surname = e.getSurname();
                String department = e.getDepartment().toString().toUpperCase();
                String position = e.getPosition();
                String gender = e.getGender().toString().toUpperCase();
                int age = e.getAge();
                int holidayAllowance = e.getHolsAllowance();
                char isManager = 'N';
                if (e.canReviewRequests()) {
                    isManager = 'Y';
                }

                String output = id + ", " + password + ", " + name + ", " + surname + ", " + department + ", " + position
                        + ", " + gender + ", " + age + ", " + holidayAllowance + ", " + isManager + "\n";

                writer.write(output);
            }
            writer.close();
        } catch (IOException io) {
            System.out.println("Error writing employees file");
        }
    }

    public void writeHolidayRequests(String holidayRequestFile, List<Employee> employees) {

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(holidayRequestFile));
            for (Employee e : employees) {
                for (HolidayRequest hr : e.getHolidayRequests()) {

                    String id = e.getId();
                    String startDate = hr.getStartDate().toString();
                    String endDate = hr.getEndDate().toString();
                    String status = hr.getStatus().toString().toUpperCase();
                    String approverId = hr.getApproverId();

                    String output = id + ", " + startDate + ", " + endDate + ", "
                            + status + ", " + approverId + "\n";

                    writer.write(output);
                }
            }
            writer.close();
        } catch (IOException io) {
            System.out.println("Error writing holiday requests file");
        }
    }

    public void exportToExcel(String holidayRequestFile, Employee employee) {

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(holidayRequestFile));
            for (HolidayRequest hr : employee.getHolidayRequests()) {
                if (hr.getStatus().equals(RequestStatus.APPROVED)) {
                    String id = employee.getId();
                    String name = employee.getName();
                    String surname = employee.getSurname();
                    Department department = employee.getDepartment();
                    String position = employee.getPosition();
                    String startDate = hr.getStartDate().toString();
                    String endDate = hr.getEndDate().toString();
                    String approverId = hr.getApproverId();

                    String output = id + ", " + name + ", " + surname + ", " + department + ", "
                            + position + ", " + startDate + ", " + endDate + ", " + approverId + "\n";

                    writer.write(output);
                }
            }
            writer.close();
        } catch (IOException io) {
            System.out.println("Error writing holiday requests file");
        }
    }

    public void displayArray(Employee[] array, String message) {
        System.out.println(message);
        System.out.println("**********************************");

        for (int k = 0; k < array.length; k++) {
            System.out.print(array[k]);
        }
        System.out.println("\n\n");
        System.out.println("****************************\n\n");
    }
}
