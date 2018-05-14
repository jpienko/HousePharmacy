package com.example.joanna.housepharmacy.Activities;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.joanna.housepharmacy.HelpExpandable.ChildItemsInfo;
import com.example.joanna.housepharmacy.HelpExpandable.GroupItemsInfo;
import com.example.joanna.housepharmacy.HelpExpandable.MyExpandableListAdapter;
import com.example.joanna.housepharmacy.InterfacesAbstracts.Toolbar;
import com.example.joanna.housepharmacy.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.ButterKnife;

public class HelpActivity extends Toolbar {
    private LinkedHashMap<String, GroupItemsInfo> questionList = new LinkedHashMap<String, GroupItemsInfo>();
    private ArrayList<GroupItemsInfo> deptList = new ArrayList<GroupItemsInfo>();

    private MyExpandableListAdapter myExpandableListAdapter;
    private ExpandableListView simpleExpandableListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_help);
        init(this, R.string.instruction_help, getString(R.string.help_title));
        inicializeExpandableList();
    }

    private void inicializeExpandableList() {
        loadData();
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        myExpandableListAdapter = new MyExpandableListAdapter(HelpActivity.this, deptList);
        simpleExpandableListView.setAdapter(myExpandableListAdapter);
    }

    private void loadData() {

        addProduct(getString(R.string.question1), getString(R.string.answer1_1));
        addProduct(getString(R.string.question1), getString(R.string.answer1_2));

        addProduct(getString(R.string.question2), getString(R.string.answer2_1));
        addProduct(getString(R.string.question2), getString(R.string.answer2_2));
        addProduct(getString(R.string.question2), getString(R.string.answer2_3));
        addProduct(getString(R.string.question2), getString(R.string.answer2_4));
        addProduct(getString(R.string.question2), getString(R.string.answer2_5));

        addProduct(getString(R.string.question3), getString(R.string.answer3_1));
        addProduct(getString(R.string.question3), getString(R.string.answer3_2));
        addProduct(getString(R.string.question3), getString(R.string.answer3_3));
        addProduct(getString(R.string.question3), getString(R.string.answer3_4));
        addProduct(getString(R.string.question3), getString(R.string.answer3_5));
        addProduct(getString(R.string.question3), getString(R.string.answer3_6));


    }


    private int addProduct(String question, String answer) {

        int groupPosition = 0;
        GroupItemsInfo headerInfo = questionList.get(question);

        if (headerInfo == null) {
            headerInfo = new GroupItemsInfo();
            headerInfo.setName(question);
            questionList.put(question, headerInfo);
            deptList.add(headerInfo);
        }

        ArrayList<ChildItemsInfo> productList = headerInfo.getAnswer();
        int listSize = productList.size();
        listSize++;

        ChildItemsInfo detailInfo = new ChildItemsInfo();
        detailInfo.setAnswer(answer);
        productList.add(detailInfo);
        headerInfo.setPlayerName(productList);

        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }


}
