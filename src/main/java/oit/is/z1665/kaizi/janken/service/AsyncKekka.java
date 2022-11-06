package oit.is.z1665.kaizi.janken.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z1665.kaizi.janken.model.MatchMapper;

import oit.is.z1665.kaizi.janken.model.*;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;
  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper matchMapper;

  @Async
  public void result(SseEmitter emitter) throws IOException {
    ArrayList<Match> matches;
    try {
      matches = matchMapper.selectAllByMatch();
      for(Match match:matches){
        if (match.getIsActive() == true) {
          emitter.send("id: " + match.getId() + " User1: " + match.getUser1() + " User2: " + match.getUser2() + " User1Hand: " + match.getUser1Hand() + " User2Hand: " + match.getUser2Hand());
          dbUpdated = false;
        }
        else {
          logger.info("nothing");
        }
      }
        // sendによってcountがブラウザにpushされる
        // 1秒STOP
        TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    }
    emitter.complete();// emitterの後始末．明示的にブラウザとの接続を一度切る．
  }
}
