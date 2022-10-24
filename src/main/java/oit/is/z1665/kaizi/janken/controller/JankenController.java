package oit.is.z1665.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z1665.kaizi.janken.model.*;

@Controller
@RequestMapping("/janken")
public class JankenController {

  @Autowired
  UserMapper userMapper;

  @PostMapping()
  public String janken(@RequestParam String name, ModelMap model) {
    model.addAttribute("name", name);
    return "janken.html";
  }

  @GetMapping()
  @Transactional
  public String janken(Principal prin, ModelMap model) {
    ArrayList<User> users = userMapper.selectAllByUser();
    model.addAttribute("users", users);
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
