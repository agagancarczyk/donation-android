package app.activities;



import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import app.donation.R;
import app.http.Response;
import app.main.DonationApp;
import app.models.Candidate;
import app.models.DonationServiceAPI;


public class CreateCandidate extends Activity implements Response<Candidate> {
    
    private DonationApp app;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
      getMenuInflater().inflate(R.menu.create, menu);
      return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
      switch (item.getItemId())
      {
        case R.id.menuAdmin : startActivity (new Intent(this, AdminPanel.class));
                               break;  
        case R.id.menuLogout : startActivity (new Intent(this, Welcome.class));
                               break;                               
      }
      return true;
    }  
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create);
      
      app = (DonationApp) getApplication();
    }
    
    public void createCandidatePressed(View view) {
        TextView candFirstName = (TextView) findViewById(R.id.candFirstName);
        TextView candLastName = (TextView) findViewById(R.id.candLastName);
        TextView candOffice = (TextView) findViewById(R.id.candOffice);

        Candidate candidate = new Candidate(
                candFirstName.getText().toString(), candLastName.getText()
                .toString(), candOffice.getText().toString());
        
        DonationServiceAPI.createCandidate(this, this, "Creating new candidate", candidate);
     
    }

    public static void createCandidate(String firstName, String lastName, String office)
    {
        Candidate createdCand = new Candidate(firstName, lastName, office);
        Log.i(createdCand.toString(), "New candidate created");
    }
    
    @Override
    public void setResponse(List<Candidate> aList) 
    {
    }

    @Override
    public void setResponse(Candidate candidate) {
        app.candidates.add(candidate);
        startActivity(new Intent(this, CreateCandidate.class));
    }

    @Override
    public void errorOccurred(Exception e) {
        app.donationServiceAvailable = false;
        Toast toast = Toast.makeText(this,
                "Donation Service Unavailable. Try again later",
                Toast.LENGTH_LONG);
        toast.show();
        startActivity(new Intent(this, Welcome.class));
    }
}
