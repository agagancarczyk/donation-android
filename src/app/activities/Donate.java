package app.activities;

import java.util.ArrayList;
import java.util.List;
import app.donation.R;
import app.http.Response;
import app.main.DonationApp;
import app.models.Candidate;
import app.models.Donation;
import app.models.DonationServiceAPI;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Donate extends Activity implements Response<Donation> {
    private RadioGroup paymentMethod;
    private ProgressBar progressBar;
    private NumberPicker amountPicker;
    private TextView amountText;
    private TextView amountTotal;
    private Spinner candidateDonated;
    private DonationApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        app = (DonationApp) getApplication();

        paymentMethod = (RadioGroup) findViewById(R.id.paymentMethod);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        amountPicker = (NumberPicker) findViewById(R.id.amountPicker);
        amountText = (TextView) findViewById(R.id.amountText);
        amountTotal = (TextView) findViewById(R.id.amountTotal);
        candidateDonated = (Spinner) findViewById(R.id.selectCandidate);

        ArrayList<String> spinnerArray = new ArrayList<String>();
        for (Candidate c : app.candidates) {
            spinnerArray.add(c.id + ". " + c.toString());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        candidateDonated.setAdapter(spinnerArrayAdapter);

        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);
        progressBar.setMax(app.target);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.donate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menuReport:
            startActivity(new Intent(this, Report.class));
            break;
        case R.id.menuLogout:
            startActivity(new Intent(this, Welcome.class));
            break;
        case R.id.menuEditDetails:
            startActivity(new Intent(this, EditDetails.class));
            break;
        }
        return true;
    }

    public void donateButtonPressed(View view) {
        String method = paymentMethod.getCheckedRadioButtonId() == R.id.PayPal ? "PayPal"
                : "Direct";
        int donatedAmount = amountPicker.getValue();
        if (donatedAmount == 0) {
            String text = amountText.getText().toString();
            if (!text.equals("")) donatedAmount = Integer.parseInt(text);
        }
        if (donatedAmount > 0) {
            int id = candidateDonated.getSelectedItemPosition();
            Candidate candidate = app.candidates.get(id);
            DonationServiceAPI.createDonation(this, app.currentUser, this,
                    "Registering new donation...", new Donation(candidate,
                            donatedAmount, method));
        }
    }

    @Override
    public void setResponse(Donation acceptedDonation) {
        Toast toast = Toast.makeText(this, "Donation Accepted",
                Toast.LENGTH_SHORT);
        toast.show();
        app.newDonation(acceptedDonation);
        progressBar.setProgress(app.totalDonated);
        String totalDonatedStr = "$" + app.totalDonated;
        amountTotal.setText(totalDonatedStr);
        amountText.setText("");
        amountPicker.setValue(0);
    }

    @Override
    public void errorOccurred(Exception e) {
        Toast toast = Toast.makeText(this,
                "Donation Service Unavailable. Try again later",
                Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void setResponse(List<Donation> aList) {
    }
}
