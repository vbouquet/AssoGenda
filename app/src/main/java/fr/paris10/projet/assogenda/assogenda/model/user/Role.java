package fr.paris10.projet.assogenda.assogenda.model.user;

public class Role {
    private RoleENUM role;
    private String nickname;

    public Role(RoleENUM role, String nickname) {
        this.role = role;
        this.nickname = nickname;
    }

    public RoleENUM getRole() {
        return role;
    }

    public void setRole(RoleENUM role) {
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
