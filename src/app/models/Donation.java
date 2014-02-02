package app.models;



public class Donation implements Comparable<Donation>
{
  public Long   id;
  public Candidate candidate;
  public int    amount;
  public String method;
  
  public User  from;
  
  Donation()
  {}
  
  public Donation (Candidate candidate, int amount, String method) 
  {
    this.candidate = candidate;
    this.amount = amount;
    this.method = method;
  }
  
  @Override
public int compareTo(Donation another) {
    return this.candidate.firstName.compareTo(another.candidate.firstName);
}

public String toString()
  {
    return candidate + ", " + amount + ", " + method;
  }



}
