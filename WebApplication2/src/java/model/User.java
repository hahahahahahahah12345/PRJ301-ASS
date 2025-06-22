/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class User {
    private int uid;
    private String username;
    private String password;
    private String fullname;
    private int depId;
    private int roleId;
    private Integer managerId;
    private String rname;

    public User() {}

    public User(int uid, String username, String password, String fullname,
                int depId, int roleId, Integer managerId, String rname) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.depId = depId;
        this.roleId = roleId;
        this.managerId = managerId;
        this.rname = rname;
    }

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

    public String getRname() { return rname; }
    public void setRname(String rname) { this.rname = rname; }

    public void setDid(int did) { this.depId = did; }
    public void setRid(int rid) { this.roleId = rid; }
}