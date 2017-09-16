package co.example.hzq.jokertwo.List;

/**
 * Created by Hzq on 2017/9/16.
 */

public class StuItem {

    private String name;
    private int id;

    public StuItem(int id,String name){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

}
