package app.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import app.activities.Donate;
import app.activities.Signup;
import app.models.Admin;
import app.models.Candidate;
import app.models.Donation;
import app.models.User;

public class DonationApp extends Application {
    public boolean donationServiceAvailable = false;

    public final int target = 10000;
    public int totalDonated = 0;

    public List<User> users = new ArrayList<User>();
    public List<Donation> donations = new ArrayList<Donation>();
    public List<Candidate> candidates = new ArrayList<Candidate>();
    public List<Admin> admins = new ArrayList<Admin>();

    public User currentUser;

    public boolean newDonation(Donation donation) {
        boolean targetAchieved = totalDonated > target;
        if (!targetAchieved) {
            donations.add(donation);
            totalDonated += donation.amount;
        } else {
            Toast toast = Toast.makeText(this, "Target Exceeded!",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        return targetAchieved;
    }

    public void newUser(User user) {
        users.add(user);
    }

    public boolean validUser(String email, String password) {
        for (User admin : admins) {
            if (admin.email.equals(email) && admin.checkPassword(password) == true) {
                currentUser = admin;
                Toast toast = Toast.makeText(this, "Logged in as Admin",
                        Toast.LENGTH_SHORT);
                toast.show();
                return true;

            }
        }
        for (User user : users) {
            if (user.email.equals(email) && user.checkPassword(password) == true) {
                currentUser = user;
                Toast toast = Toast.makeText(this, "Logged in as User",
                        Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("Donation", "Donation App Started");
    }
}
