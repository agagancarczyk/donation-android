package app.activities;

import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import app.donation.R;
import app.http.Response;
import app.main.DonationApp;
import app.models.DonationServiceAPI;
import app.models.User;

public class Signup extends Activity implements Response<User> {
    private Spinner country;
    private Spinner region;
    private DonationApp app;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menuLogin:
            startActivity(new Intent(this, Login.class));
            break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        app = (DonationApp) getApplication();
    }

    public void registerPressed(View view) {
        TextView firstName = (TextView) findViewById(R.id.firstName);
        TextView lastName = (TextView) findViewById(R.id.lastName);
        TextView email = (TextView) findViewById(R.id.Email);
        TextView password = (TextView) findViewById(R.id.Password);
        TextView age = (TextView) findViewById(R.id.Age);
        TextView addressLineOne = (TextView) findViewById(R.id.AddressLineOne);
        TextView addressLineTwo = (TextView) findViewById(R.id.AddressLineTwo);

        country = (Spinner) findViewById(R.id.Country);
        String countryStr = country.getSelectedItem().toString();

        region = (Spinner) findViewById(R.id.Region);
        String regionStr = region.getSelectedItem().toString();

        User user = new User(
                firstName.getText().toString(), lastName.getText()
                .toString(), email.getText().toString(), password.getText()
                .toString(), Integer.parseInt(age.getText().toString()),
                addressLineOne.getText().toString(), addressLineTwo.getText()
                        .toString(), countryStr, regionStr);
        DonationServiceAPI.createUser(this, this, "Registering new user", user);
    }

    @Override
    public void setResponse(List<User> aList) {
    }

    @Override
    public void setResponse(User user) {
        app.users.add(user);
        startActivity(new Intent(this, Welcome.class));
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
