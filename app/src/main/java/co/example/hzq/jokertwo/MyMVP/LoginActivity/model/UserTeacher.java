package co.example.hzq.jokertwo.MyMVP.LoginActivity.model;

/**
 * Created by Hzq on 2017/11/29.
 */

public class UserTeacher {

    /**
     * name : 郝志强
     * password : 123
     */

    private String name;
    private String password;

    public UserTeacher(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
