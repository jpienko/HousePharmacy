package com.example.joanna.housepharmacy.HelpExpandable;

/**
 * Created by Joanna on 2018-04-24.
 */
import java.util.ArrayList;

public class GroupItemsInfo {

    private String listName;
    private ArrayList<ChildItemsInfo> list = new ArrayList<ChildItemsInfo>();

    public String getName() {
        return listName;
    }

    public void setName(String question) {
        this.listName = question;
    }

    public ArrayList<ChildItemsInfo> getAnswer() {
        return list;
    }

    public void setPlayerName(ArrayList<ChildItemsInfo> answer) {
        this.list = answer;
    }

}