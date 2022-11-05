package oit.is.z1665.kaizi.janken.model;

public class Matchinfo {
  int id;
  int user1;
  int user2;
  String user1Hand;
  boolean isActive;

  public Matchinfo(int user1, int user2, String user1Hand, boolean isActive) {
    this.user1 = user1;
    this.user2 = user2;
    this.user1Hand = user1Hand;
    this.isActive = isActive;
  }

  public int getUser1() {
    return user1;
  }

  public void setUser1(int user1) {
    this.user1 = user1;
  }

  public int getUser2() {
    return user2;
  }

  public void setUser2(int user2) {
    this.user2 = user2;
  }

  public String getUser1Hand() {
    return user1Hand;
  }

  public void setUser1Hand(String user1Hand) {
    this.user1Hand = user1Hand;
  }

  public boolean getIsActive() {
    return isActive;
  }

  public void setIsActieve(boolean isActive) {
    this.isActive = isActive;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}
