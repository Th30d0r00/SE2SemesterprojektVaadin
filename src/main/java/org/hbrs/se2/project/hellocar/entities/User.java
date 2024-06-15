package org.hbrs.se2.project.hellocar.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.hbrs.se2.project.hellocar.util.AccountType;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name ="user" , schema = "carlook" )
public class User {
    private int id;
    private String email;
    private String password;
    private List<Rolle> roles;
    private AccountType accountType;
    private String userid;
    private Student studentenInfos;
    private Company firmenInfos;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(userid, user.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, userid);
    }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_to_rolle",
      catalog = "demouser",
      schema = "carlook",
      joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false),
      inverseJoinColumns =
          @JoinColumn(
              name = "bezeichnung",
              referencedColumnName = "bezeichhnung",
              nullable = false))
  public List<Rolle> getRoles() {
        return roles;
    }

    public void setRoles(List<Rolle> roles) {
        this.roles = roles;
    }
}
