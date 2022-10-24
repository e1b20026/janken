package oit.is.z1665.kaizi.janken.model;

public class Janken {
  private int hand = 0;
  private int cpu_hand = 0;

  public Janken(int hand) {
    this.hand = hand;
  }

  public String result() {
    //0がグー、1がパー、2がチョキ
    if (this.cpu_hand == this.hand) {
      return "あいこ";
    }

    if (this.hand == 0) {
      if (this.cpu_hand == 1) {
        return "まけ";
      } else if (this.cpu_hand == 2) {
        return "かち";
      }
    } else if (this.hand == 1) {
      if (this.cpu_hand == 2) {
        return "まけ";
      } else if (this.cpu_hand == 0) {
        return "かち";
      }
    } else if (this.hand == 2) {
      if (this.cpu_hand == 0) {
        return "まけ";
      } else if (this.cpu_hand == 1) {
        return "かち";
      }
    }
    return "";
  }

  public String Get_myhand() {
    switch (this.hand) {
      case 0:
        return "Gu";
      case 1:
        return "Pa";
      case 2:
        return "Choki";
    }
    return "";
  }

  public String Get_cpuhand() {

    switch (this.cpu_hand) {
      case 0:
        return "Gu";
      case 1:
        return "Pa";
      case 2:
        return "Choki";
    }
    return "";
  }
}
