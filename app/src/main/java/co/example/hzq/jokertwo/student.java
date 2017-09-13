package co.example.hzq.jokertwo;

/**
 * Created by Hzq on 2017/7/25.
 */
public class student {
    private String aName;
    private String aSpeak;
    private int aIcon;

    public student(){}

    public student(String aName,String aSpeak,int aIcon){
        this.aName = aName;
        this.aSpeak = aSpeak;
        this.aIcon = aIcon;
    }

    public String getName(){
        return aName;
    }

    public String getaSpeak(){
        return aSpeak;
    }

    public int getaIcon(){
        return aIcon;
    }

    public void setaName(String aName){
        this.aName = aName;
    }

    public void setaSpeak(String aSpeak){
        this.aSpeak = aSpeak;
    }

    public void setaIcon(int aIcon){
        this.aIcon = aIcon;
    }
}
