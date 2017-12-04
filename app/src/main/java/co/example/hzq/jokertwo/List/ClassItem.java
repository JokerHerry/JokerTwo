package co.example.hzq.jokertwo.List;

/**
 * Created by Hzq on 2017/9/14.
 */

public class ClassItem {

    private int item_imageView;
    private String item_text_class;
    private String item_text_course;
    private String item_text_time;

    public ClassItem(int item_imageView, String item_text_class, String item_text_course, String item_text_time ){
        this.item_imageView = item_imageView;
        this.item_text_class = item_text_class;
        this.item_text_course = item_text_course;
        this.item_text_time = item_text_time;
    }

    //set函数
    public void setItem_imageView(int item_imageView) {
        this.item_imageView = item_imageView;
    }

    public void setItem_text_class(String item_text_class) {
        this.item_text_class = item_text_class;
    }

    public void setItem_text_course(String item_text_course) {
        this.item_text_course = item_text_course;
    }

    public void setItem_text_time(String item_text_time) {
        this.item_text_time = item_text_time;
    }

    //get函数
    public int getItem_imageView() {
        return item_imageView;
    }

    public String getItem_text_class() {
        return item_text_class;
    }

    public String getItem_text_course() {
        return item_text_course;
    }

    public String getItem_text_time() {
        return item_text_time;
    }

}
