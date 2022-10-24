package oit.is.z1665.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z1665.kaizi.janken.model.*;

@Controller
@RequestMapping()
public class JankenController {

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @PostMapping("/janken")
  public String janken(@RequestParam String name, ModelMap model) {
    model.addAttribute("name", name);
    return "janken.html";
  }

  @GetMapping("/janken")
  @Transactional
  public String janken(Principal prin, ModelMap model) {
    ArrayList<User> users = userMapper.selectAllByUser();
    ArrayList<Match> matches = matchMapper.selectAllByMatch();
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    return "janken.html";
  }

  @GetMapping("/janken/jankenchoice")
  public String janken(@RequestParam Integer hand, ModelMap model) {
    Janken janken = new Janken(hand);
    model.addAttribute("my_choice", janken.Get_myhand());
    model.addAttribute("cpu_choice", janken.Get_cpuhand());
    model.addAttribute("result", janken.result());

    return "janken.html";
  }

  @GetMapping("/fight")
  public String janken(Principal prin, @RequestParam Integer id, @RequestParam Integer hand, ModelMap model) {
    Janken janken = new Janken(hand);
    User users = userMapper.selectByName(id);
    User my_users = userMapper.selectByUser(prin.getName());

    Match match_data = new Match(my_users.getId(), users.getId(), janken.Get_myhand(), janken.Get_cpuhand());
    matchMapper.insertMatch(match_data);

    model.addAttribute("my_users", my_users);
    model.addAttribute("users", users);

    model.addAttribute("my_hand", janken.Get_myhand());
    model.addAttribute("cpu_hand", janken.Get_cpuhand());
    model.addAttribute("result", janken.result());

    return "match.html";
  }

  @GetMapping("/match")
  @Transactional
  public String match(@RequestParam Integer id, Principal prin, ModelMap model) {
    String login_name = prin.getName();
    User my_users = userMapper.selectByUser(login_name);
    User users = userMapper.selectByName(id);
    model.addAttribute("my_users", my_users);
    model.addAttribute("users", users);
    return "match.html";
  }



}
