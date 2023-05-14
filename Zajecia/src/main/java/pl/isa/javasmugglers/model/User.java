package pl.isa.javasmugglers.model;

public class User {


    private Integer id;
    private String email;
    private enum userType {STUDENT, PROFESSOR, ADMIN}
    private userType type;
    private String password;
    private String firstName;
    private String lastName;
    private enum accountStatus {ACTIVE, PENDING, REJECTED}
    private accountStatus status;

    public User(Integer id, String email, userType type, String password, String firstName, String lastName, accountStatus status) {
        this.id = id;
        this.email = email;
        this.type = type;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public userType getType() {
        return type;
    }

    public void setType(userType type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public accountStatus getStatus() {
        return status;
    }

    public void setStatus(accountStatus status) {
        this.status = status;
    }
}
