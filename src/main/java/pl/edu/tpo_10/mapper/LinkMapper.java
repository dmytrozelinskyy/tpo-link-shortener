package pl.edu.tpo_10.mapper;

import pl.edu.tpo_10.dto.LinkResponse;
import pl.edu.tpo_10.model.Link;

public class LinkMapper {
   public static LinkResponse getResponse(Link link) {
      LinkResponse linkResponse = new LinkResponse();
      linkResponse.setId(link.getId());
      linkResponse.setName(link.getName());
      linkResponse.setTargetUrl(link.getTargetURL());
      linkResponse.setRedirectUrl("http://localhost:8080/red/" + link.getId());
      linkResponse.setVisits(link.getVisits());
      return linkResponse;
   }
}