package app.activities;

import java.util.ArrayList;
import java.util.List;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import app.donation.R;
import app.http.Response;
import app.main.DonationApp;
import app.models.Donation;
import app.models.DonationServiceAPI;
import app.models.User;

public class AdminPanel extends ExpandableListActivity implements
        Response<Donation> {

    private DonationApp app;
    private static final String STR_CHECKED = " has Checked!";
    private static final String STR_UNCHECKED = " has unChecked!";
    private int ParentClickStatus = -1;
    private int ChildClickStatus = -1;
    private List<User> users = new ArrayList<User>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menuCreate:
            startActivity(new Intent(this, CreateCandidate.class));
            break;
        case R.id.menuLogout:
            startActivity(new Intent(this, Welcome.class));
            break;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (DonationApp) getApplication();

        for (User user : app.users) {
            if (!(user.firstName.equals("admin"))) {
                users.add(user);
                DonationServiceAPI.getDonations(this, user, this,
                        "Getting donations made by users");
                List<Donation> donations = new ArrayList<Donation>(
                        app.donations);
                user.donations.addAll(donations);
                Log.i("user", user.toString());
            }
        }

        Resources res = this.getResources();
        Drawable devider = res.getDrawable(R.drawable.line);

        // Set ExpandableListView values
        getExpandableListView().setGroupIndicator(null);
        getExpandableListView().setDivider(devider);
        getExpandableListView().setChildDivider(devider);
        getExpandableListView().setDividerHeight(1);

        registerForContextMenu(getExpandableListView());
        loadHosts(users);
    }

    private void loadHosts(List<User> users) {
        if (users == null) return;

        // Check for ExpandableListAdapter object
        if (this.getExpandableListAdapter() == null) {
            // Create ExpandableListAdapter Object
            final MyExpandableListAdapter mAdapter = new MyExpandableListAdapter();

            // Set Adapter to ExpandableList Adapter
            this.setListAdapter(mAdapter);
        } else {
            // Refresh ExpandableListView data
            ((MyExpandableListAdapter) getExpandableListAdapter())
                    .notifyDataSetChanged();
        }
    }

    @Override
    public void setResponse(List<Donation> aList) {
        app.donations = aList;
    }

    @Override
    public void setResponse(Donation anObject) {
    }

    @Override
    public void errorOccurred(Exception e) {
        Toast toast = Toast.makeText(this, "Donation Service Unavailable!",
                Toast.LENGTH_LONG);
        toast.show();
        startActivity(new Intent(this, Welcome.class));
    }

    //************************************************************//
    //                    ADAPTER                                 //
    //************************************************************//
    class MyExpandableListAdapter extends BaseExpandableListAdapter {
        private LayoutInflater inflater;

        public MyExpandableListAdapter() {
            super();
            this.inflater = LayoutInflater.from(AdminPanel.this);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return users.get(groupPosition).donations.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {

            // Log.i("Noise",
            // "parent == "+groupPosition+"=  child : =="+childPosition);
            if (ChildClickStatus != childPosition) {
                ChildClickStatus = childPosition;
            }
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                boolean isLastChild, View convertView, ViewGroup userView) {

            final User user = users.get(groupPosition);
            final Donation donation = user.donations.get(childPosition);

            convertView = inflater.inflate(R.layout.row_child, userView, false);
            ((TextView) convertView.findViewById(R.id.row_firstName))
                    .setText(donation.candidate.firstName);
            ((TextView) convertView.findViewById(R.id.row_lastName))
                    .setText(donation.candidate.lastName);
            ((TextView) convertView.findViewById(R.id.row_office))
                    .setText(donation.candidate.office);
            ((TextView) convertView.findViewById(R.id.row_amount))
                    .setText("" + donation.amount);
            ((TextView) convertView.findViewById(R.id.row_method))
                    .setText(donation.method);
            ImageView image = (ImageView) convertView.findViewById(R.id.image);

            image.setImageResource(getResources().getIdentifier(
                    "com.androidexample.customexpandablelist:drawable/setting"
                            + user.firstName, null, null));

           ImageView rightcheck = (ImageView) convertView
                   .findViewById(R.id.rightcheck);

            // Log.i("onCheckedChanged", "isChecked: "+parent.isChecked());

            // Change right check image on parent at runtime
            if (user.isChecked() == true) {
                rightcheck
                        .setImageResource(getResources()
                                .getIdentifier(
                                        "com.androidexample.customexpandablelist:drawable/rightcheck",
                                        null, null));
            } 

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return users.get(groupPosition).donations.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return users.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return users.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            Log.i("Parent", groupPosition + "=  getGroupId "
                    + ParentClickStatus);

            ParentClickStatus = groupPosition;
            if (ParentClickStatus == 0) ParentClickStatus = -1;

            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                View convertView, ViewGroup userView) {
            final User user = users.get(groupPosition);

            convertView = inflater
                    .inflate(R.layout.row_parent, userView, false);
            ((TextView) convertView.findViewById(R.id.row_userfirstName))
                    .setText(user.firstName);
            ((TextView) convertView.findViewById(R.id.row_userlastName))
                    .setText(user.lastName);
            ((TextView) convertView.findViewById(R.id.row_useremail))
                    .setText(user.email);

            return convertView;
        }

        @Override
        public void notifyDataSetChanged() {
            // Refresh List rows
            super.notifyDataSetChanged();
        }

        @Override
        public boolean isEmpty() {
            return ((users == null) || users.isEmpty());
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

    }

    private final class CheckUpdateListener implements OnCheckedChangeListener {
        private final User user;

        private CheckUpdateListener(User user) {
            this.user = user;
        }

        public void onCheckedChanged(CompoundButton buttonView,
                boolean isChecked) {
            Log.i("onCheckedChanged", "isChecked: " + isChecked);
            user.setChecked(isChecked);

            ((MyExpandableListAdapter) getExpandableListAdapter())
                    .notifyDataSetChanged();

            final Boolean checked = user.isChecked();
            Toast.makeText(
                    getApplicationContext(),
                    "Parent : " + user.firstName + " "
                            + (checked ? STR_CHECKED : STR_UNCHECKED),
                    Toast.LENGTH_LONG).show();
        }

    }
}
