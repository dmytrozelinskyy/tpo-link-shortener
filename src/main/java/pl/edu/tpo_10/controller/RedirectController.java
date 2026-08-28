package pl.edu.tpo_10.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.tpo_10.dto.LinkResponse;
import pl.edu.tpo_10.model.Link;
import pl.edu.tpo_10.service.LinkService;

import java.util.Optional;

@RestController
public class RedirectController {

   private LinkService linkService;

   public RedirectController(LinkService linkService) {
      this.linkService = linkService;
   }

   @GetMapping("/red/{id}")
   public ResponseEntity<LinkResponse> redirect(@PathVariable String id) {
      Optional<Link> link = linkService.read(id);
      if (link.isPresent()) return ResponseEntity.status(302).build();
      else return ResponseEntity.status(404).build();
   }
}
