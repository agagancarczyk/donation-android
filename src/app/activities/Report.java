package app.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.donation.R;
import app.http.Response;
import app.main.DonationApp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import app.models.Candidate;
import app.models.Donation;
import app.models.DonationServiceAPI;

public class Report extends Activity implements Response <Donation>
{
  private ListView        listView;
  private DonationApp     app;
  private DonationAdapter adapter; 
  private Spinner         candidateDonated;

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.report, menu);
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case R.id.menuEditDetails : startActivity (new Intent(this, EditDetails.class));
                             break;  
      case R.id.menuDonate : startActivity (new Intent(this, Donate.class));
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
    setContentView(R.layout.activity_report);

    app = (DonationApp) getApplication();

    listView = (ListView) findViewById(R.id.reportList);
    
    List<Donation>donations = new ArrayList<Donation>();
    donations = app.donations;
    Collections.sort(donations);
    adapter = new DonationAdapter (this, donations);

    listView.setAdapter(adapter);
    
    candidateDonated  = (Spinner)  findViewById(R.id.selectCandidate); 
    ArrayList<String> spinnerArray = new ArrayList<String>();
    spinnerArray.add("all");
    for(Candidate c: app.candidates){
        spinnerArray.add(c.toString());
    }
    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
    candidateDonated.setAdapter(spinnerArrayAdapter);  
    
    DonationServiceAPI.getDonations(this, app.currentUser, this, "Downloading Donations List..");
  }

  @Override
  public void setResponse(List<Donation> aList)
  {
    app.donations     = aList;
    adapter.donations = aList;
    adapter.notifyDataSetChanged();
  }

  @Override
  public void setResponse(Donation anObject)
  {
  }
  
  @Override
  public void errorOccurred(Exception e)
  {
    Toast toast = Toast.makeText(this, "Donation Service Unavailable!", Toast.LENGTH_LONG);
    toast.show();
    startActivity (new Intent(this, Welcome.class));
  }
  
  public void applyFilterButtonPressed(View view) 
  {
      List<Donation>donationsForCand = new ArrayList<Donation>();
      
      if(candidateDonated.getSelectedItem().toString().equals("all")){
          donationsForCand = app.donations;
      }
      else{
          String selectedCand = candidateDonated.getSelectedItem().toString();
          for (Donation d: app.donations){
              if(d.candidate.toString().equals(selectedCand)){
                  donationsForCand.add(d);
              }
          }
      }
      Collections.sort(donationsForCand);
      adapter = new DonationAdapter (this, donationsForCand);
      listView.setAdapter(adapter);
  }
}

class DonationAdapter extends ArrayAdapter<Donation>
{
  private Context        context;
  public  List<Donation> donations;

  public DonationAdapter(Context context, List<Donation> donations)
  {
    super(context, R.layout.row_donate, donations);
    this.context   = context;
    this.donations = donations;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
    View     view       = inflater.inflate(R.layout.row_donate, parent, false);
    Donation donation   = donations.get(position);
    TextView firstNameView = (TextView) view.findViewById(R.id.row_firstName);
    TextView lastNameView  = (TextView) view.findViewById(R.id.row_lastName);
    TextView officeView = (TextView) view.findViewById(R.id.row_office);
    TextView amountView = (TextView) view.findViewById(R.id.row_amount);
    TextView methodView = (TextView) view.findViewById(R.id.row_method);
    
    firstNameView.setText(donation.candidate.firstName);
    lastNameView.setText(donation.candidate.lastName);
    officeView.setText(donation.candidate.office);
    amountView.setText("" + donation.amount);
    methodView.setText(donation.method);
    
    return view;
  }
 
  @Override
  public int getCount()
  {
    return donations.size();
  }
}