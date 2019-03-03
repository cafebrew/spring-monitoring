package spring.monitoring.api.user;

import javax.persistence.*;

@Entity
@Table(name = "User")
class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long seq;

  @Column(unique = true, nullable = false)
  private String userId;

  @Column(nullable = false)
  private String realname;

  @Column(nullable = false)
  private String email;

  public Long getSeq() {
    return seq;
  }

  public void setSeq(Long seq) {
    this.seq = seq;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRealname() {
    return realname;
  }

  public void setRealname(String realname) {
    this.realname = realname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "User{" +
        "seq=" + seq +
        ", userId='" + userId + '\'' +
        ", realname='" + realname + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
