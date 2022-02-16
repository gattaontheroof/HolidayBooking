
/**
 *
 * @author agata
 */
public class Manager extends Employee {

    public Manager(String id, String password, String name, String surname, Department department,
            String position, Gender gender, int age, int holsAllowance) {
        super(id, password, name, surname, department, position, gender, age, holsAllowance);

    }

    @Override
    public boolean canReviewRequests() {
        return true;
    }

}
