package app.activities;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import app.donation.R;
import app.http.Response;
import app.main.DonationApp;
import app.models.DonationServiceAPI;
import app.models.User;

public class EditDetails extends Activity implements Response<User> {

    public static ArrayList<User> userList = new ArrayList<User>();
    private Context context;
    private User aUser;
    private DonationApp app;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menuDonate:
            startActivity(new Intent(this, Donate.class));
            break;
        case R.id.menuReport:
            startActivity(new Intent(this, Report.class));
            break;
        case R.id.menuLogout:
            startActivity(new Intent(this, Welcome.class));
            break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.activity_editprofile);
        app = (DonationApp) getApplication();

        TextView firstNameView = (TextView) findViewById(R.id.firstName);
        firstNameView.setText(app.currentUser.firstName);

        TextView lastNameView = (TextView) findViewById(R.id.lastName);
        lastNameView.setText(app.currentUser.lastName);

        TextView emailView = (TextView) findViewById(R.id.Email);
        emailView.setText(app.currentUser.email);

        TextView passwordView = (TextView) findViewById(R.id.Password);
        passwordView.setText(app.currentUser.password);

        TextView ageView = (TextView) findViewById(R.id.Age);
        ageView.setText(app.currentUser.age + "");

        TextView addressLineOneView = (TextView) findViewById(R.id.AddressLineOne);
        addressLineOneView.setText(app.currentUser.addressLineOne);

        TextView addressLineTwoView = (TextView) findViewById(R.id.AddressLineTwo);
        addressLineTwoView.setText(app.currentUser.addressLineTwo);

        Spinner countryView = (Spinner) findViewById(R.id.Country);
        for (int i = 1; i < countryView.getCount(); i++) {
            if (countryView.getItemAtPosition(i).toString()
                    .equals(app.currentUser.country)) {
                countryView.setSelection(i);
            }
        }

        Spinner regionView = (Spinner) findViewById(R.id.Region);
        for (int i = 1; i < regionView.getCount(); i++) {
            if (regionView.getItemAtPosition(i).toString()
                    .equals(app.currentUser.region)) {
                regionView.setSelection(i);
            }
        }
    }

    protected EditText getEditText(int id) {
        return ((EditText) findViewById(id));
    }

    protected String getEditString(int id) {
        return (getEditText(id)).getText().toString();
    }
    
    protected int getEditInteger(int id) {
        return Integer.parseInt(getEditString(id));
    }
    
    protected String getSpinner(int id) {
        Spinner spin = (Spinner) findViewById(id);
        return spin.getSelectedItem().toString();
    }

    public void editButtonPressed(View view) {

        String userfirstName = getEditString(R.id.firstName);
        app.currentUser.firstName=userfirstName;
        String userlastName = getEditString(R.id.lastName);
        app.currentUser.lastName=userlastName;
        String userPassword = getEditString(R.id.Password);
        app.currentUser.password=userPassword;
        int userAge = getEditInteger(R.id.Age);
        app.currentUser.age=userAge;
        String userAddressLineOne = getEditString(R.id.AddressLineOne);
        app.currentUser.addressLineOne=userAddressLineOne;
        String userAddressLineTwo = getEditString(R.id.AddressLineTwo);
        app.currentUser.addressLineTwo=userAddressLineTwo;
        String userCountry = getSpinner(R.id.Country);
        app.currentUser.country=userCountry;
        String userRegion = getSpinner(R.id.Region);
        app.currentUser.region=userRegion;

        DonationServiceAPI.editUser(this, this, "Editing user", app.currentUser);
       
    }

    @Override
    public void setResponse(List<User> aList) {
    }

    @Override
    public void setResponse(User user) {
        app.users.add(user);
        startActivity(new Intent(this, EditDetails.class));
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
