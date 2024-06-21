package org.hbrs.se2.project.hellocar.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;


public class Rolle {
    private String bezeichhnung;
    private List<User> users;


    public String getBezeichhnung() {
        return bezeichhnung;
    }

    public void setBezeichhnung(String bezeichhnung) {
        this.bezeichhnung = bezeichhnung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rolle rolle = (Rolle) o;
        return Objects.equals(bezeichhnung, rolle.bezeichhnung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bezeichhnung);
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
