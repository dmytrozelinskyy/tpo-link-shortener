package pl.edu.tpo_10.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.tpo_10.dto.LinkRequest;
import pl.edu.tpo_10.dto.LinkResponse;
import pl.edu.tpo_10.mapper.LinkMapper;
import pl.edu.tpo_10.model.Link;
import pl.edu.tpo_10.service.LinkService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/links")
public class LinkController {

   private LinkService linkService;

   public LinkController(LinkService linkService) {
      this.linkService = linkService;
   }

   @PostMapping
   public ResponseEntity<?> create(@Valid @RequestBody LinkRequest linkRequest,
                                   BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
         Map<String, String> errors = new HashMap<>();
         bindingResult.getFieldErrors().forEach(error ->
                 errors.put(error.getField(), error.getDefaultMessage())
         );
         return ResponseEntity.badRequest().body(errors);
      }

      Link link = linkService.create(linkRequest);
      LinkResponse linkResponse = LinkMapper.getResponse(link);
      return ResponseEntity.status(201).header("Location", "http://localhost:8080/api/links/" + link.getId()).body(linkResponse);
   }

   @GetMapping("/{id}")
   public ResponseEntity<LinkResponse> getById(@PathVariable String id) {
      Optional<Link> link = linkService.findById(id);
      return link.map(value -> new ResponseEntity<>(LinkMapper.getResponse(value), HttpStatus.OK))
              .orElse(ResponseEntity.notFound().build());
   }

   @PatchMapping("/{id}")
   public ResponseEntity<?> update(@PathVariable String id,
                                              @Valid @RequestBody LinkRequest linkRequest,
                                              BindingResult bindingResult)
   {
      if (bindingResult.hasErrors()) {
         Map<String, String> errors = new HashMap<>();
         bindingResult.getFieldErrors().forEach(error ->
                 errors.put(error.getField(), error.getDefaultMessage())
         );
         return ResponseEntity.badRequest().body(errors);
      }
      try {
         linkService.update(id, linkRequest, linkRequest.getPassword());
         return ResponseEntity.status(204).build();
      } catch (ResponseStatusException e) {
         return ResponseEntity.status(e.getStatusCode())
                 .header("Reason", e.getReason())
                 .build();
      }
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<LinkResponse> delete(@PathVariable String id,
                                              @RequestParam(required = false) String password) {
      try {
         linkService.delete(id, password);
         return ResponseEntity.status(204).build();
      } catch (ResponseStatusException e) {
         return ResponseEntity.status(e.getStatusCode())
                 .header("Reason", e.getReason())
                 .build();
      }
   }
}
