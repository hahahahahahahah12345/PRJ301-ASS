/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ha
 */

public class User {
    private int uid;
    private String username;
    private String password;
    private String fullname;
    private int depId;
    private int roleId;
    private Integer managerId;

    // Constructors, Getters, Setters
    public User() {}

    public User(int uid, String username, String password, String fullname,
                int depId, int roleId, Integer managerId) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.depId = depId;
        this.roleId = roleId;
        this.managerId = managerId;
    }

    // Getter & Setter
    public int getUid() { return uid; }
    public void setUid(int uid) { this.uid = uid; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public int getDepId() { return depId; }
    public void setDepId(int depId) { this.depId = depId; }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public Integer getManagerId() { return managerId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }

    public void setDid(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setRid(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setRname(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

 