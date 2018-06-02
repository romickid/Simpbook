package nkucs1416.simpbook.statement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.util.MyDate;
import nkucs1416.simpbook.util.MySpinnerAdapter;

public class StatementActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private StatementAdapter statementAdapter;
    private ArrayList<Map<String,Object>> listMapStatement;
    private ArrayList<Map<String,Object>> listClass1;
    private MySpinnerAdapter adapterClass1;
    private MySpinnerAdapter adapterClass2;
    private MySpinnerAdapter adapterAccount;

    private FloatingActionButton buttonFilter;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);

        initFindById();
        initToolbar();
        initRecycleView();
        initSpinnerAdapter();
        setListenerFilter();
    }


    // 初始化相关
    /**
     * 初始化Id
     */
    private void initFindById() {
        toolbar = findViewById(R.id.statement_toolbar);
        recyclerView = findViewById(R.id.statement_recyclerview);
        buttonFilter = findViewById(R.id.statement_button_filter);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listMapStatement = new ArrayList<>();
        demoSetStatementList();

        statementAdapter = new StatementAdapter(this, listMapStatement);
        recyclerView.setAdapter(statementAdapter);
    }


    // 流水相关
    /**
     * 测试用StatementList
     */
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
    }


    // 查询筛选相关
    /**
     * 设置筛选按钮的Listener
     */
    private void setListenerFilter() {
        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                createDialogFilter().show();
            }
        });
    }

    /**
     * 构建筛选的Dialog
     * @return
     */
    private Dialog createDialogFilter() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this,3);
        View viewRemarkDialog = View.inflate(this, R.layout.dialog_statementfilter, null);
        final Spinner spinnerClass1 = viewRemarkDialog.findViewById(R.id.dstatementfilter_spinner_class1);
        final Spinner spinnerClass2 = viewRemarkDialog.findViewById(R.id.dstatementfilter_spinner_class2);
        final Spinner spinnerAccount = viewRemarkDialog.findViewById(R.id.dstatementfilter_spinner_account);
        final TextView textViewDateFrom = viewRemarkDialog.findViewById(R.id.dstatementfilter_textview_datefrom);
        final TextView textViewDateTo = viewRemarkDialog.findViewById(R.id.dstatementfilter_textview_dateto);
        spinnerClass1.setAdapter(adapterClass1);
        spinnerClass2.setAdapter(adapterClass2);
        spinnerAccount.setAdapter(adapterAccount);
        setDateTimeListener(textViewDateFrom);
        setDateTimeListener(textViewDateTo);

        builder.setTitle("筛选");
        builder.setView(viewRemarkDialog);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return builder.create();
    }

    /**
     * 初始化筛选框中,分类和账户的SpinnerAdapter
     */
    private void initSpinnerAdapter() {
        listClass1 = new ArrayList<>();
        demoSetSpinnerList();

        adapterClass1 = new MySpinnerAdapter(this, listClass1);
        adapterClass2 = new MySpinnerAdapter(this, listClass1);
        adapterAccount = new MySpinnerAdapter(this, listClass1);
    }

    /**
     * 测试用SpinnerList
     */
    private void demoSetSpinnerList() {
        listClass1=new ArrayList<Map<String,Object>>();
        Map map=new HashMap<String, Object>();
        map.put("text", "1");
        map.put("color", R.drawable.ic_lens_yellow_a400_24dp);
        listClass1.add(map);
        map=new HashMap<String, Object>();
        map.put("text", "2");
        listClass1.add(map);
        map.put("color", R.drawable.ic_lens_blue_a400_24dp);
        map=new HashMap<String, Object>();
        map.put("text", "3");
        map.put("color", R.drawable.ic_lens_yellow_a400_24dp);
        listClass1.add(map);
        map=new HashMap<String, Object>();
        map.put("text", "4");
        map.put("color", R.drawable.ic_lens_blue_a400_24dp);
        listClass1.add(map);
    }

    /**
     * 设置筛选框内时间
     */
    private void setTextViewDate(MyDate myDate, TextView textView) {
        textView.setText(myDate.getYear() + "年" + myDate.getMonth() + "月" + myDate.getDay() + "日");
    }

    /**
     * 设置筛选框内, 日期的Listener
     * @param textView 显示日期的textView
     */
    private void setDateTimeListener(final TextView textView) {
        setTextViewDate(new MyDate(), textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                createDialogDate(textView).show();
            }
        });

    }

    /**
     * 构建筛选框内, 选择日期的Dialog
     * @param ttextView 显示日期的textView
     * @return 返回Dialog
     */
    private Dialog createDialogDate(TextView ttextView) {
        final TextView textView = ttextView;
        Dialog dialog = null;
        DatePickerDialog.OnDateSetListener listener = null;
        MyDate myDate = new MyDate();

        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setTextViewDate(new MyDate(year, month+1, dayOfMonth), textView);
            }
        };

        dialog = new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, listener, myDate.getYear(), myDate.getMonth() - 1, myDate.getDay());
        return dialog;
    }

}
