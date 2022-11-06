package oit.is.z1665.kaizi.janken.controller;

import java.io.IOException;
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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oit.is.z1665.kaizi.janken.model.*;

import oit.is.z1665.kaizi.janken.service.*;

@Controller
@RequestMapping()
public class JankenController {

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchinfoMapper matchinfoMapper;

  private final Logger logger = LoggerFactory.getLogger(JankenController.class);

  @Autowired
  private AsyncKekka kekka;

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
    ArrayList<Matchinfo> matchinfo = matchinfoMapper.selectTrueByMatchinfo();
    model.addAttribute("matchinfo",matchinfo);
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
    int count = 0;//取得したレコードに自分がいる場合のみカウントが増加する
    Janken janken = new Janken(hand);
    User users = userMapper.selectByName(id);
    User my_users = userMapper.selectByUser(prin.getName());

    ArrayList<Matchinfo> matchenemy = matchinfoMapper.selectTrueByMatchinfo();
    for (Matchinfo match : matchenemy) {
      if (match.getUser2() == my_users.id) {
        count++;
      }
    }

    if (count == 0) {
      Matchinfo matchinfo = new Matchinfo(my_users.getId(), users.getId(), janken.Get_myhand());
      matchinfo.setIsActive(true);
      matchinfoMapper.insertMatchinfo(matchinfo);
    }
    else{
      Matchinfo matchuser = matchinfoMapper.selectCheckUserByMatchinfo(users.getId(), my_users.getId());
      Match matches = new Match(my_users.getId(), users.getId(), janken.Get_myhand(), matchuser.getUser1Hand());
      matches.setIsActive(true);
      matchMapper.insertMatch(matches);
      count = 0;
    }


    //Match match_data = new Match(my_users.getId(), users.getId(), janken.Get_myhand(), janken.Get_cpuhand());
    //matchMapper.insertMatch(match_data);

    model.addAttribute("my_users", my_users);
    model.addAttribute("users", users);

    model.addAttribute("my_hand", janken.Get_myhand());
    model.addAttribute("cpu_hand", janken.Get_cpuhand());
    model.addAttribute("result", janken.result());

    return "wait.html";
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

  @GetMapping("/kekka/match")
  @Transactional
  public String match(Principal prin, @RequestParam Integer id, ModelMap model) {
    String login_name = prin.getName();
    User my_users = userMapper.selectByUser(login_name);
    User users = userMapper.selectByName(id);

    Match match = matchMapper.selectUpdataByMatch(my_users.getId(), users.getId());
    matchMapper.updateByisActive(match);
    Matchinfo matchinfo = matchinfoMapper.selectCheckUserByMatchinfo(my_users.getId(), users.getId());
    matchinfoMapper.updateByisActive(matchinfo);

    model.addAttribute("my_users", my_users);
    model.addAttribute("users", users);
    return "match.html";
  }

  @GetMapping("/fight/kekka")
  public SseEmitter result() {
    // infoレベルでログを出力する
    // finalは初期化したあとに再代入が行われない変数につける（意図しない再代入を防ぐ）
    final SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);//
    // 引数にLongの最大値をTimeoutとして指定する

    try {
      this.kekka.result(emitter);
    } catch (IOException e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
      emitter.complete();
    }
    return emitter;
  }
}
