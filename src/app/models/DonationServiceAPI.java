package app.models;

import java.util.List;
import android.content.Context;
import app.http.Request;
import app.http.Response;
import app.http.Rest;

public class DonationServiceAPI {
    public static void getUsers(Context context, Response<User> response, String dialogMesssage) {
        new GetUsers(context, response, dialogMesssage).execute();
    }

    public static void createUser(Context context, Response<User> response, String dialogMesssage, User user) {
        new CreateUser(context, response, dialogMesssage).execute(user);
    }

    public static void editUser(Context context, Response<User> response,String dialogMesssage, User user) {
        new EditUser(context, response, dialogMesssage).execute(user);
    }

    public static void User(Context context, Response<User> response,String dialogMesssage, User user) {
        new CreateUser(context, response, dialogMesssage).execute(user);
    }

    public static void getDonations(Context context, User user, Response<Donation> response, String dialogMesssage) {
        new GetDonations(context, user, response, dialogMesssage).execute();
    }
    
    public static void getCandidates(Context context, Response<Candidate> response, String dialogMesssage) {
        new GetCandidates(context, response, dialogMesssage).execute();
    }

    public static void createDonation(Context context, User user,
            Response<Donation> response, String dialogMesssage,
            Donation donation) {
        new CreateDonation(context, user, response, dialogMesssage)
                .execute(donation);
    }
    
    public static void getAdmins(Context context, Response<Admin> response, String dialogMesssage) {
        new GetAdmins(context, response, dialogMesssage).execute();
    }

    public static void createAdmin(Context context, Response<Admin> response, String dialogMesssage, Admin admin) {
        new CreateAdmin(context, response, dialogMesssage).execute(admin);
    }

    public static void editAdmin(Context context, Response<Admin> response,String dialogMesssage, Admin admin) {
        new EditAdmin(context, response, dialogMesssage).execute(admin);
    }

    public static void Admin(Context context, Response<Admin> response,String dialogMesssage, Admin admin) {
        new CreateAdmin(context, response, dialogMesssage).execute(admin);
    }
    
    public static void createCandidate(Context context, Response<Candidate> response, String dialogMesssage, Candidate candidate) {
        new CreateCandidate(context, response, dialogMesssage).execute(candidate);
    }
    
}

class GetUsers extends Request {
    public GetUsers(Context context, Response<User> callback, String message) {
        super(context, callback, message);
    }

    @Override
    protected List<User> doRequest(Object... params) throws Exception {
        String response = Rest.get("/api/users");
        List<User> userList = JsonParsers.json2Users(response);
        return userList;
    }
}

class CreateUser extends Request {
    public CreateUser(Context context, Response<User> callback, String message) {
        super(context, callback, message);
    }

    @Override
    protected User doRequest(Object... params) throws Exception {
        String response = Rest.post("/api/users",
                JsonParsers.user2Json(params[0]));
        return JsonParsers.json2User(response);
    }
}

class EditUser extends Request {
    public EditUser(Context context, Response<User> callback, String message) {
        super(context, callback, message);
    }

    @Override
    protected User doRequest(Object... params) throws Exception {
        String response = Rest.post("/api/edit",
                JsonParsers.user2Json(params[0]));
        return JsonParsers.json2User(response);
    }
}

class GetDonations extends Request {
    private User user;

    public GetDonations(Context context, User user,Response<Donation> callback, String message) {
        super(context, callback, message);
        this.user = user;
    }

    @Override
    protected List<Donation> doRequest(Object... params) throws Exception {
        String response = Rest.get("/api/users/" + user.id + "/donations");
        List<Donation> donationList = JsonParsers.json2Donations(response);
        return donationList;
    }
}

class GetCandidates extends Request {
    private User user;

    public GetCandidates(Context context, Response<Candidate> callback, String message) {
        super(context, callback, message);
    }

    @Override
    protected List<Candidate> doRequest(Object... params) throws Exception {
        String response = Rest.get("/api/candidates");
        List<Candidate> candidateList = JsonParsers.json2Candidates(response);
        return candidateList;
    }
}

class CreateDonation extends Request {
    private User user;

    public CreateDonation(Context context, User user,Response<Donation> callback, String message) {
        super(context, callback, message);
        this.user = user;
    }

    @Override
    protected Donation doRequest(Object... params) throws Exception {
        String response = Rest.post("/api/users/" + user.id + "/donations",
                JsonParsers.donation2Json(params[0]));
        return JsonParsers.json2Donation(response);
    }
}

class GetAdmins extends Request {
    public GetAdmins(Context context, Response<Admin> callback, String message) {
        super(context, callback, message);
    }

    @Override
    protected List<Admin> doRequest(Object... params) throws Exception {
        String response = Rest.get("/api/admins");
        List<Admin> adminList = JsonParsers.json2Admins(response);
        return adminList;
    }
}

class CreateAdmin extends Request {
    public CreateAdmin(Context context, Response<Admin> callback, String message) {
        super(context, callback, message);
    }

    @Override
    protected Admin doRequest(Object... params) throws Exception {
        String response = Rest.post("/api/admins",
                JsonParsers.admin2Json(params[0]));
        return JsonParsers.json2Admin(response);
    }
}

class EditAdmin extends Request {
    public EditAdmin(Context context, Response<Admin> callback, String message) {
        super(context, callback, message);
    }

    @Override
    protected Admin doRequest(Object... params) throws Exception {
        String response = Rest.post("/api/editAdmin",
                JsonParsers.admin2Json(params[0]));
        return JsonParsers.json2Admin(response);
    }
}

class CreateCandidate extends Request {
    public CreateCandidate(Context context, Response<Candidate> callback, String message) {
        super(context, callback, message);
    }

    @Override
    protected Candidate doRequest(Object... params) throws Exception {
        String response = Rest.post("/api/candidates",
                JsonParsers.candidate2Json(params[0]));
        return JsonParsers.json2Candidate(response);
    }
}





