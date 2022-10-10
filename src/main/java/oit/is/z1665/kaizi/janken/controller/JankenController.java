package oit.is.z1665.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import oit.is.z1665.kaizi.janken.model.*;

@Controller
public class JankenController {

  @PostMapping("/janken")
  public String janken(@RequestParam String name, ModelMap model) {
    model.addAttribute("name", name);
    return "janken.html";
  }

  @GetMapping("/janken")
  public String janken() {
    return "janken.html";
  }

  @GetMapping("/jankenchoice")
  public String janken(@RequestParam Integer hand, ModelMap model) {
    Janken janken = new Janken(hand);
    model.addAttribute("my_choice", janken.Get_myhand());
    model.addAttribute("cpu_choice", janken.Get_cpuhand());
    model.addAttribute("result", janken.result());

    return "janken.html";
  }
}
