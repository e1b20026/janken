package oit.is.z1665.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JankenController {

  @PostMapping("/janken")
  public String janken(@RequestParam String NAME, ModelMap model) {
    model.addAttribute("name", NAME);
    return "janken.html";
  }

  @GetMapping("/janken")
  public String janken() {
    return "janken.html";
  }
}
