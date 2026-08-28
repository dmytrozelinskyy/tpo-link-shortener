package pl.edu.tpo_10.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import pl.edu.tpo_10.dto.LinkRequest;
import pl.edu.tpo_10.model.Link;
import pl.edu.tpo_10.service.LinkService;

import java.util.Locale;
import java.util.Optional;

@Controller
public class WebController {

   private LinkService linkService;

   public WebController(LinkService linkService) {
      this.linkService = linkService;
   }

   @GetMapping("/create")
   public String showCreateForm(Model model) {
      model.addAttribute("link", new LinkRequest());
      return "create";
   }

   @PostMapping("/create")
   public String create(@Valid @ModelAttribute("link") LinkRequest linkRequest,
                        BindingResult result, Model model) {
      if (result.hasErrors()) {
         return "create";
      }
      try {
         linkService.create(linkRequest);
         return "redirect:/create?success";
      } catch (Exception e) {
         model.addAttribute("error", "Error while creating link:" + e.getMessage());
         return "create";
      }
   }

   @GetMapping("/manage")
   public String showManageForm(@RequestParam(required = false) String name,
                                @RequestParam(required = false) String password,
                                @RequestParam(required = false) String updated,
                                Model model)
   {
      if (updated != null) {
         model.addAttribute("success", "Link updated successfully");
      }
      if (name != null) {
         Optional<Link> found = linkService.findByIdAndPassword(name, password);
         if (found.isPresent()) {
            model.addAttribute("link", found.get());
            model.addAttribute("updateRequest", new LinkRequest());
         } else {
            model.addAttribute("error", "{error.link.notfound}");
         }
      }
      return "manage";
   }

   @PostMapping("/manage/update")
   public String updateLink(@RequestParam String id,
                            @Valid @ModelAttribute("updateRequest") LinkRequest linkRequest,
                            BindingResult bindingResult,
                            @RequestParam String originalPassword,
                            Model model) {

      if (bindingResult.hasErrors()) {
         model.addAttribute("updateRequest", linkRequest);
         linkService.findById(id).ifPresent(link -> model.addAttribute("link", link));
         return "manage";
      }

      try {
         linkService.update(id, linkRequest, originalPassword);
         return "redirect:/manage?name=" + linkRequest.getName() + "&password=" + originalPassword + "&updated=true";
      } catch (ResponseStatusException e) {
         model.addAttribute("error", e.getReason());
         model.addAttribute("updateRequest", linkRequest);
         linkService.findById(id).ifPresent(link -> model.addAttribute("link", link));
         return "manage";
      }
   }


   @PostMapping("/manage/delete")
   public String deleteLink(@RequestParam String id,
                            @RequestParam String password) {
      try {
         linkService.delete(id, password);
         return "redirect:/manage?deleted=true";
      } catch (ResponseStatusException e) {
         return "redirect:/manage?error=delete_failed";
      }
   }

   @GetMapping("/change-lang")
   public String changeLanguage(HttpServletRequest request,
                                @RequestParam("lang") String lang)
   {
      Locale locale = Locale.forLanguageTag(lang);
      request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
      String referer = request.getHeader("Referer");
      return "redirect:" + (referer != null ? referer : "/create");
   }
}
