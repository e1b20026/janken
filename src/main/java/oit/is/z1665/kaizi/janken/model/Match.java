package oit.is.z1665.kaizi.janken.model;

public class Match {
  int id;
  String user1;
  String user2;
  String user1Hand;
  String user2Hand;


  // Thymeleafでフィールドを扱うためにはgetter/setterが必ず必要
  // vscodeのソースコード右クリック->ソースアクションでsetter/getterを簡単に追加できる
  public String getUser1Name() {
    return user1;
  }

  public void setUser1Name(String userName) {
    this.user1 = userName;
  }

  public String getUser2Name() {
    return user2;
  }

  public void setUser2Name(String chamberName) {
    this.user2 = chamberName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}
