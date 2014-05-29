package ch.netzbarkeit.reminder.app;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("title")
    public String title;

    @SerializedName("subtitle")
    public String subtitle;

    @SerializedName("caret_priority")
    public Integer caret_priority;

    public int id;

    public String getId(){
        String _id = new Integer(this.id).toString();
        return _id;
    }

}