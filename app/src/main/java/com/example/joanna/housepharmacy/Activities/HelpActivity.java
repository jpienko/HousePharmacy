package com.example.joanna.housepharmacy.Activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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
        init(this, R.string.instruction_help, "Pomoc");
        loadData();
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        myExpandableListAdapter = new MyExpandableListAdapter(HelpActivity.this, deptList);
        simpleExpandableListView.setAdapter(myExpandableListAdapter);
    }

    private void loadData() {

        addProduct("1. Po co mi ta aplikacja?", " Aplikacja została stworzona, aby ułatwić poszukiwanie leków");
        addProduct("1. Po co mi ta aplikacja?","Dzięki tej aplikacji powinieneś/powinnaś mieć możliwość zawsze i wszędzie skontrolować stan apteczki, oczywiście, gdy na bieżąco uaktualniasz informacje");

        addProduct("2. Jak poruszać się po aplikacji?", "");

        addProduct("3. Co mogę robić?", "1. Szukać leków");
        addProduct("3. Co mogę robić?", "2. Dodawać/usuwać leki");
        addProduct("3. Co mogę robić?", "3. Aktualizować leki");
        addProduct("3. Co mogę robić?", "4. Dodawać/usuwać nowe miejsca przechowywania");
        addProduct("3. Co mogę robić?", "5. Aktualizować informacje o miejscu przechowywania");
        addProduct("3. Co mogę robić?", "6. Przeglądać listę dodanych leków");


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
