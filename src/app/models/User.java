package app.models;

import java.util.ArrayList;
import java.util.List;

public class User 
{
  public Long   id;
  public String firstName;
  public String lastName;
  public String email;
  public String password;
  public int age; 
  public String addressLineOne; 
  public String addressLineTwo;
  public String country;
  public String region; 
  public List<Donation>donations = new ArrayList<Donation>();
  private String checkedtype;
  private boolean checked;
 
  public User()
  {}
  
  public User(String firstName, String lastName, String email, String password, int age, String addressLineOne, String addressLineTwo, String country, String region)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.age = age;
    this.addressLineOne = addressLineOne;
    this.addressLineTwo = addressLineTwo;
    this.country = country;
    this.region = region;
  } 
  
  public void setUserFirstName(String firstName) {
      this.firstName = firstName;
  }
  
  public void setUserLastName(String lastName) {
      this.lastName = lastName;
  }
 //For table//
 
  public String getCheckedType()
  {
      return checkedtype;
  }
   
  public void setCheckedType(String checkedtype)
  {
      this.checkedtype = checkedtype;
  }
   
   
  public boolean isChecked()
  {
      return checked;
  }
  public void setChecked(boolean checked)
  {
      this.checked = checked;
  }
   
  // ArrayList to store child objects
  public List<Donation> getChildren()
  {
      return donations;
  }
   
  public void setChildren(List<Donation> donations)
  {
      this.donations = donations;
  }
  
  public boolean checkPassword(String candidate)
  {
      return BCrypt.checkpw(candidate, this.password);
  }  

@Override
public String toString() {
    return "User [firstName=" + firstName + ", lastName=" + lastName
            + ", age=" + age + ", addressLineOne=" + addressLineOne
            + ", addressLineTwo=" + addressLineTwo + ", country=" + country
            + ", region=" + region + ", donations=" + donations + "]";
}
}