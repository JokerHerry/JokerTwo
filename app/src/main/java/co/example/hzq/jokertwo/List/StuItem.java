package co.example.hzq.jokertwo.List;

/**
 * Created by Hzq on 2017/9/16.
 */

public class StuItem {

    private String name;
    private String id;
    private boolean checkBox = false;

    public StuItem(String id,String name){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public boolean getCheckBox() {
        return checkBox;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }


}
