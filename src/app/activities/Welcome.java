package app.activities;

import java.util.List;

import app.donation.R;
import app.http.Response;
import app.main.DonationApp;
import app.models.Admin;
import app.models.Candidate;
import app.models.DonationServiceAPI;
import app.models.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Welcome extends Activity implements Response<User>
{
  DonationApp app;
  class MockResponse implements Response<Candidate>{

      @Override
      public void setResponse(List<Candidate> aList) {
          app.candidates = aList;
      }

      @Override
      public void setResponse(Candidate anObject) {
      }

      @Override
      public void errorOccurred(Exception e) {
      }
    }
  class MockAdminResponse implements Response<Admin>{

      @Override
      public void setResponse(List<Admin> aList) {
          app.admins = aList;
      }

      @Override
      public void setResponse(Admin anObject) {
      }

      @Override
      public void errorOccurred(Exception e) {
      }
    }
  
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);
    app = (DonationApp) getApplication();
    
    DonationServiceAPI.getCandidates(this, new MockResponse(), "");
    DonationServiceAPI.getUsers(this, this, "Retrieving list of users and Getting list of candidates");
    DonationServiceAPI.getAdmins(this, new MockAdminResponse(), "");
  
  }
  
  @Override
  public void onResume()
  {
    super.onResume();
    app.currentUser = null;
    DonationServiceAPI.getCandidates(this, new MockResponse(), "");
    DonationServiceAPI.getUsers(this, this, "Retrieving list of users and Getting list of candidates");
  }
  
  void serviceUnavailableMessage()
  {
    Toast toast = Toast.makeText(this, "Donation Service Unavailable. Try again later", Toast.LENGTH_LONG);
    toast.show();
  }
  
  public void loginPressed (View view) 
  {
    if (app.donationServiceAvailable)
    {
      startActivity (new Intent(this, Login.class));
    }
    else
    {
      serviceUnavailableMessage();
    }
  }

  public void signupPressed (View view) 
  {
    if (app.donationServiceAvailable)
    {
      startActivity (new Intent(this, Signup.class));
    }
    else
    {
      serviceUnavailableMessage();
    }
  }
  
  @Override
  public void setResponse(List<User> aList)
  {
    app.users = aList;
    app.donationServiceAvailable = true;
  }
  
  @Override
  public void errorOccurred(Exception e)
  {
    app.donationServiceAvailable = false;
    serviceUnavailableMessage();
  }
  
  @Override
  public void setResponse(User anObject)
  {}
}
