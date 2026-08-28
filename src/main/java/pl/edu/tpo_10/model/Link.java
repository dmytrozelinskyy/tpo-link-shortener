package pl.edu.tpo_10.model;

import jakarta.persistence.*;

@Entity
public class Link {
   @Id
   @Column(name = "LinkID")
   private String id;
   @Column(name = "LinkName")
   private String name;
   @Column(name = "LinkTargetURL")
   private String targetURL;
   @Column(name = "LinkPassword")
   private String password;
   @Column(name = "LinkVisits")
   private int visits;

   // -- Getters -- //
   public String getId() { return id; }
   public String getName() { return name; }
   public String getTargetURL() { return targetURL; }
   public String getPassword() { return password; }
   public int getVisits() { return visits; }

   // -- Setters -- //
   public void setId(String id) { this.id = id; }
   public void setName(String name) { this.name = name; }
   public void setTargetURL(String targetURL) { this.targetURL = targetURL; }
   public void setPassword(String password) { this.password = password; }
   public void setVisits(int visits) { this.visits = visits; }
}
