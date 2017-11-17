package net.ivdata.taskerauto;

/**
 * Created by MystX on 21/10/2017.
 */

public class TaskListItem {
    String name;
    int value; /* 0 -> checkbox disable, 1 -> checkbox enable */

    TaskListItem(String name, int value){
        this.name = name;
        this.value = value;
    }
    public String getName(){
        return this.name;
    }
    public int getValue(){
        return this.value;
    }
    public void toggle() {if (value == 0) value = 1; else value = 0;}
}
