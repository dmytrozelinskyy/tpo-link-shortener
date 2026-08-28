package pl.edu.tpo_10.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import pl.edu.tpo_10.constraint.UniqueUrl;
import pl.edu.tpo_10.constraint.ValidHttpsUrl;
import pl.edu.tpo_10.constraint.ValidPassword;

public class LinkRequest {

   @NotBlank(message = "{name.notblank}")
   @Size(min = 5, max = 20, message = "{name.size}")
   private String name;

   @NotBlank(message = "{url.notblank}")
   @ValidHttpsUrl
   @UniqueUrl
   private String targetUrl;

   @ValidPassword(required = false)
   private String password;

   // -- Getters -- //
   public String getName() { return name; }
   public String getTargetUrl() { return targetUrl; }
   public String getPassword() { return password; }

   // -- Setters -- //
   public void setName(String name) { this.name = name; }
   public void setTargetUrl(String targetUrl) { this.targetUrl = targetUrl; }
   public void setPassword(String password) { this.password = password; }
}
