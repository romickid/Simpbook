package nkucs1416.simpbook.statement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nkucs1416.simpbook.R;

public class StatementActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private StatementAdapter statementAdapter;
    private ArrayList<Map<String,Object>> listMapStatement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);

        initFindById();
        initToolbar();
        initRecycleView();
    }

    private void initFindById() {
        toolbar = findViewById(R.id.statement_toolbar);
        recyclerView = findViewById(R.id.statement_recyclerview);
    }

    private void initToolbar(){
        toolbar.setTitle("流水");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_36dp);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listMapStatement = new ArrayList<>();
        demoSetStatementList();

        statementAdapter = new StatementAdapter(this, listMapStatement);
        recyclerView.setAdapter(statementAdapter);
    }

    private void demoSetStatementList() {
        listMapStatement =new ArrayList<Map<String,Object>>();

        Map map=new HashMap<String, Object>();
        map.put("text", "A一");
        map.put("money", "123.12");
        map.put("color", R.drawable.ic_lens_yellow_a400_24dp);
        listMapStatement.add(map);

        map=new HashMap<String, Object>();
        map.put("text", "B二");
        map.put("money", "123.12");
        map.put("color", R.drawable.ic_lens_blue_a400_24dp);
        listMapStatement.add(map);

        map=new HashMap<String, Object>();
        map.put("text", "C");
        map.put("money", "123.12");
        map.put("color", R.drawable.ic_lens_yellow_a400_24dp);
        listMapStatement.add(map);

        map=new HashMap<String, Object>();
        map.put("text", "D");
        map.put("money", "123.12");
        map.put("color", R.drawable.ic_lens_blue_a400_24dp);
        listMapStatement.add(map);
    }
}
