package com.teamyear.admin.formmodel;

import com.teamyear.common.entity.Role;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class UserForm {

    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirm;

    private List<Role> permission;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public List<Role> getPermission() {
        return permission;
    }

    public void setPermission(List<Role> permission) {
        this.permission = permission;
    }
}