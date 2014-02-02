package app.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import app.donation.R;
import app.main.DonationApp;

public class Login extends Activity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menuSignup:
            startActivity(new Intent(this, Signup.class));
            break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signinPressed(View view) {
        DonationApp app = (DonationApp) getApplication();

        TextView email = (TextView) findViewById(R.id.loginEmail);
        TextView password = (TextView) findViewById(R.id.loginPassword);

        if (app.validUser(email.getText().toString(), password.getText()
                .toString())) {
            if (app.currentUser instanceof app.models.Admin) {
                startActivity(new Intent(this, AdminPanel.class));
            } else {
                startActivity(new Intent(this, Donate.class));
            }
        } else {
            Toast toast = Toast.makeText(this, "Invalid Credentials",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}