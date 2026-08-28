package pl.edu.tpo_10.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.tpo_10.dto.LinkRequest;
import pl.edu.tpo_10.model.Link;
import pl.edu.tpo_10.repository.LinkRepository;

import java.util.Optional;
import java.util.Random;

@Service
public class LinkService {
   private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   private static final int LENGTH = 10;
   private final Random random = new Random();
   private LinkRepository linkRepository;

   public LinkService(LinkRepository linkRepository) {
      this.linkRepository = linkRepository;
   }

   public Link create(LinkRequest linkRequest) {
      Link link = new Link();
      link.setId(generateID());
      link.setName(linkRequest.getName());
      link.setTargetURL(linkRequest.getTargetUrl());
      link.setPassword(linkRequest.getPassword());
      link.setVisits(0);
      return linkRepository.save(link);
   }

   public Optional<Link> findById(String id) {
      return linkRepository.findById(id);
   }

   public Optional<Link> findByIdAndPassword(String id, String password) {
      if (password == null || password.isEmpty()) {
         Optional<Link> link = linkRepository.findById(id);
         return link.filter(l -> l.getPassword() == null || l.getPassword().isEmpty());
      } else
         return linkRepository.findByIdAndPassword(id, password);
   }


   public Optional<Link> read(String id) {
      return linkRepository.findById(id).map(link -> {
         link.setVisits(link.getVisits() + 1);
         linkRepository.save(link);
         return link;
      });
   }

   public void update(String id, LinkRequest request, String originalPassword) {
      Link link = linkRepository.findById(id)
              .filter(l -> {
                 String stored = l.getPassword();
                 return stored == null || stored.equals(originalPassword);
              })
              .orElseThrow(() ->
                      new ResponseStatusException(HttpStatus.NOT_FOUND, "No link found or wrong credentials"));

      link.setName(request.getName());
      link.setTargetURL(request.getTargetUrl());
      linkRepository.save(link);
   }


   public void delete(String id, String password)
   {
      Link link = linkRepository.findById(id).orElse(null);
      if (link == null) return;
      if (link.getPassword() != null || !link.getPassword().isEmpty())
         if (password == null || !link.getPassword().equals(password))
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Wrong password");
      linkRepository.deleteById(id);
   }

   // -- Generate Random ID (uppercase and lowercase letters, 10 chars) -- //
   private String generateID()
   {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < LENGTH; i++) {
         int index = random.nextInt(CHARACTERS.length());
         stringBuilder.append(CHARACTERS.charAt(index));
      }
      return stringBuilder.toString();
   }
}
