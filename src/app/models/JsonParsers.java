package app.models;

import java.util.List;
import java.util.ArrayList;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class JsonParsers
{
    public static JSONSerializer userSerializer = new JSONSerializer()
    .prettyPrint(true)
    .include("donations")
    .exclude("*.class")
    .exclude("*.persistent")
    .exclude("*.entityId");

public static JSONSerializer donationSerializer = new JSONSerializer()
    .prettyPrint(true)
    .exclude("*.class")
    .exclude("*.persistent")
    .exclude("*.entityId")
    .include("candidate");

public static JSONSerializer candidateSerializer = new JSONSerializer()
    .prettyPrint(true)
    .exclude("class")
    .exclude("persistent")
    .exclude("entityId");

public static JSONSerializer adminSerializer = new JSONSerializer()
    .prettyPrint(true)
    .exclude("class")
    .exclude("persistent")
    .exclude("entityId");

  public static User json2User(String json)
  {
    return new JSONDeserializer<User>().deserialize(json, User.class); 
  }

  public static List<User> json2Users(String json)
  {
    return new JSONDeserializer<ArrayList<User>>().use("values", User.class)
                                                  .deserialize(json);
  }
  
  public static String user2Json(Object obj)
  {
    return userSerializer.serialize(obj);
  }
  
  public static List<User> users2Json(String json)
  {
    return new JSONDeserializer<ArrayList<User>>().use("values", User.class)
                                                  .deserialize(json);
  } 
    
 
  public static Donation json2Donation(String json)
  {
    return  new JSONDeserializer<Donation>().deserialize(json, Donation.class);    
  }
  
  public static String donation2Json(Object obj)
  {
    return donationSerializer.serialize(obj);
  }  
  
  public static List<Donation> json2Donations(String json)
  {
    return new JSONDeserializer<ArrayList<Donation>>().use("values", Donation.class)
        .deserialize(json);
  }  
  
  public static Candidate json2Candidate(String json)
  {
    return  new JSONDeserializer<Candidate>().deserialize(json, Candidate.class);    
  }
  
  public static String candidate2Json(Object obj)
  {
    return candidateSerializer.serialize(obj);
  }  
  
  public static List<Candidate> json2Candidates(String json)
  {
    return new JSONDeserializer<ArrayList<Candidate>>().use("values", Candidate.class).deserialize(json);
  }  
  
  public static List<Candidate> candidates2Json(String json)
  {
    return new JSONDeserializer<ArrayList<Candidate>>().use("values", Candidate.class).deserialize(json);
  } 
  
  public static Admin json2Admin(String json)
  {
    return new JSONDeserializer<Admin>().deserialize(json, Admin.class); 
  }

  public static List<Admin> json2Admins(String json)
  {
    return new JSONDeserializer<ArrayList<Admin>>().use("values", Admin.class)
                                                  .deserialize(json);
  }
  
  public static String admin2Json(Object obj)
  {
    return adminSerializer.serialize(obj);
  }
  
  public static List<Admin> admins2Json(String json)
  {
    return new JSONDeserializer<ArrayList<Admin>>().use("values", Admin.class)
                                                  .deserialize(json);
  } 
}