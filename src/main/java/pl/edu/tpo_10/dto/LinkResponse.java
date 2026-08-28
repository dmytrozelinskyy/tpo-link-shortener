package pl.edu.tpo_10.dto;

public class LinkResponse {
   private String id;
   private String name;
   private String targetUrl;
   private String redirectUrl;
   private int visits;

   // -- Getters -- //
   public String getId() { return id; }
   public String getName() { return name; }
   public String getTargetUrl() { return targetUrl; }
   public String getRedirectUrl() { return redirectUrl; }
   public int getVisits() { return visits; }

   // -- Setters -- //
   public void setId(String id) { this.id = id; }
   public void setName(String name) { this.name = name; }
   public void setTargetUrl(String targetUrl) { this.targetUrl = targetUrl; }
   public void setRedirectUrl(String redirectUrl) { this.redirectUrl = redirectUrl; }
   public void setVisits(int visits) { this.visits = visits; }
}
