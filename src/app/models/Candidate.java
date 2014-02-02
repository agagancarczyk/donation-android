package app.models;

public class Candidate 
{
    public Long   id;
    public String firstName;
    public String lastName;
    public String office;

    public Candidate() 
    {}

    public Candidate(String firstName, String lastName, String office) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.office = office;
    }

    public String toString() {
        return firstName + ", " + lastName + ", " + office;
    }
}
