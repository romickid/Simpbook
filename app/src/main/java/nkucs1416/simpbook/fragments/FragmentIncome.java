package nkucs1416.simpbook.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.database.AccountDb;
import nkucs1416.simpbook.database.CategoryDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.database.RecordDb;
import nkucs1416.simpbook.database.SubcategoryDb;
import nkucs1416.simpbook.database.TemplateDb;
import nkucs1416.simpbook.main.ActivityMain;
import nkucs1416.simpbook.record.ActivityRecord;
import nkucs1416.simpbook.statement.ActivityStatement;
import nkucs1416.simpbook.util.Account;
import nkucs1416.simpbook.util.Collection;
import nkucs1416.simpbook.util.Record;
import nkucs1416.simpbook.util.SpinnerAdapterAccount;
import nkucs1416.simpbook.util.Class1;
import nkucs1416.simpbook.util.Class2;
import nkucs1416.simpbook.util.SpinnerAdapterClass2;
import nkucs1416.simpbook.util.Date;
import nkucs1416.simpbook.util.SpinnerAdapterClass1;

import static nkucs1416.simpbook.util.Class1.sortListClass1s;
import static nkucs1416.simpbook.util.Date.*;
import static nkucs1416.simpbook.util.Money.*;
import static nkucs1416.simpbook.util.Remark.createDialogRemark;

public class FragmentIncome extends Fragment {
    private View view;
    private Spinner spinnerClass1;
    private Spinner spinnerClass2;
    private Spinner spinnerAccount;
    private EditText editTextMoney;
    private TextView textViewDate;
    private TextView textViewRemark;
    private FloatingActionButton buttonAdd;

    private SpinnerAdapterClass1 adapterClass1;
    private SpinnerAdapterClass2 adapterClass2;
    private SpinnerAdapterAccount adapterAccount;

    private SQLiteDatabase sqLiteDatabase;
    private CategoryDb class1Db;
    private SubcategoryDb class2Db;
    private AccountDb accountDb;
    private RecordDb recordDb;
    private TemplateDb collectionDb;

    private ArrayList<Class1> listClass1s;
    private ArrayList<Class2> listClass2s;
    private ArrayList<Account> listAccounts;
    private int class1Id;

    private String recordScheme;
    private int updateRecordId;
    private int insertCollectionId;

    private OnFragmentInteractionListener fragmentInteractionListener;


    // Fragment相关
    public FragmentIncome() {
    }

    public static FragmentIncome newInstance() {
        FragmentIncome fragment = new FragmentIncome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_income, container, false);
        initFindById();
        initRecordScheme();

        initMoney();
        initDate();
        initRemark();

        initDatabase();
        initData();

        initClass();
        initAccount();
        initButton();

        updateDataForUpdateScheme();
        updateDataForInsertCollectionScheme();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            fragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteractionListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    // 初始化相关
    /**
     * 初始化Id
     */
    private void initFindById() {
        editTextMoney = view.findViewById(R.id.fincome_editview_money);
        spinnerClass1 = view.findViewById(R.id.fincome_spinner_class1);
        spinnerClass2 = view.findViewById(R.id.fincome_spinner_class2);
        spinnerAccount = view.findViewById(R.id.fincome_spinner_account);
        textViewDate = view.findViewById(R.id.fincome_textview_date);
        textViewRemark = view.findViewById(R.id.fincome_textview_remark);
        buttonAdd = view.findViewById(R.id.fincome_button_add);
    }

    /**
     * 初始化记录形式: Insert / Update
     */
    private void initRecordScheme() {
        recordScheme = getActivity().getIntent().getStringExtra("RecordScheme");

        if (recordScheme.equals("Update")) {
            updateRecordId = Integer.valueOf(getActivity().getIntent().getStringExtra("RecordUpdateId"));
        }
        if (recordScheme.equals("InsertFromCollection")) {
            insertCollectionId = Integer.valueOf(getActivity().getIntent().getStringExtra("RecordCollectionId"));
        }
    }

    /**
     * 初始化金额
     */
    private void initMoney() {
        setEditTextDecimalScheme(editTextMoney);
    }

    /**
     * 初始化分类
     */
    private void initClass() {
        adapterClass1 = new SpinnerAdapterClass1(getActivity(), listClass1s);
        spinnerClass1.setAdapter(adapterClass1);

        adapterClass2 = new SpinnerAdapterClass2(getActivity(), listClass2s);
        spinnerClass2.setAdapter(adapterClass2);

        setListenerSpinnerClass1();
    }

    /**
     * 初始化账户
     */
    private void initAccount() {
        adapterAccount = new SpinnerAdapterAccount(getActivity(), listAccounts);
        spinnerAccount.setAdapter(adapterAccount);
    }

    /**
     * 初始化日期
     */
    private void initDate() {
        setDefaultDate();
        setListenerDate();
    }

    /**
     * 初始化备注
     */
    private void initRemark() {
        setDefaultRemark();
        setListenerRemark();
    }

    /**
     * 初始化按钮
     */
    private void initButton() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                switch (recordScheme) {
                    case "Insert":
                    case "InsertFromCollection":
                        Record recordInsert = getRecordInsert();
                        String messageInsert = insertRecord(recordInsert);
                        if (messageInsert.equals("成功")) {
                            Toast.makeText(getContext(), messageInsert, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), ActivityMain.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), messageInsert, Toast.LENGTH_LONG).show();
                        }
                        break;

                    case "Update":
                        Record recordUpdate = getRecordUpdate(updateRecordId);
                        String messageUpdate = updateRecord(recordUpdate);
                        if (messageUpdate.equals("成功")) {
                            Toast.makeText(getContext(), messageUpdate, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), ActivityStatement.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), messageUpdate, Toast.LENGTH_LONG).show();
                        }
                        break;

                    case "Collection":
                        Collection collection = getCollectionInsert();
                        String messageCollectionInsert = insertCollection(collection);
                        if (messageCollectionInsert.equals("成功")) {
                            Toast.makeText(getContext(), messageCollectionInsert, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), ActivityRecord.class);
                            intent.putExtra("RecordType","Collection");
                            intent.putExtra("RecordScheme","Insert");
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), messageCollectionInsert, Toast.LENGTH_LONG).show();
                        }
                        break;

                    default:
                        Toast.makeText(getContext(), "内部错误: RecordScheme值错误", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    /**
     * 初始化数据库
     */
    private void initDatabase() {
        CustomSQLiteOpenHelper customSQLiteOpenHelper = new CustomSQLiteOpenHelper(getContext());
        sqLiteDatabase = customSQLiteOpenHelper.getWritableDatabase();
        class1Db = new CategoryDb(sqLiteDatabase);
        class2Db = new SubcategoryDb(sqLiteDatabase);
        accountDb = new AccountDb(sqLiteDatabase);
        recordDb = new RecordDb(sqLiteDatabase);
        collectionDb = new TemplateDb(sqLiteDatabase);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        updateListClass1s();
        updateListClass2s();
        updateListAccounts();

        checkDataValidity();
    }


    // 分类相关
    /**
     * 为SpinnerClass1设置与class1Id实例相同的位置
     *
     * @param class1Id 需要显示的class1实例id
     */
    private void setSpinnerPositionClass1ById(int class1Id) {
        for(int i = 0; i<listClass1s.size(); i++) {
            if (class1Id == listClass1s.get(i).getId()) {
                spinnerClass1.setSelection(i);
            }
        }
    }

    /**
     * 为SpinnerClass2设置与class2Id实例相同的位置
     *
     * @param class2Id 需要显示的class2实例id
     */
    private void setSpinnerPositionClass2ById(int class2Id) {
        for(int i = 0; i < listClass2s.size(); i++) {
            if (class2Id == listClass2s.get(i).getId()) {
                spinnerClass2.setSelection(i);
            }
        }
    }

    /**
     * 为SpinnerClass1设置与Class2联动的Listener
     */
    private void setListenerSpinnerClass1() {
        spinnerClass1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                class1Id = ((Class1)spinnerClass1.getSelectedItem()).getId();
                updateListClass2s();
                adapterClass2 = new SpinnerAdapterClass2(getActivity(), listClass2s);
                spinnerClass2.setAdapter(adapterClass2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }


    // 账户相关
    /**
     * 为SpinnerAccount设置与accountId实例相同的位置
     *
     * @param accountId 需要显示的account实例id
     */
    private void setSpinnerPositionAccountById(int accountId) {
        for(int i = 0; i < listAccounts.size(); i++) {
            if (accountId == listAccounts.get(i).getId()) {
                spinnerAccount.setSelection(i);
            }
        }
    }


    // 日期相关
    /**
     * 设置日期的默认形式(使用者使用的当天日期)
     */
    private void setDefaultDate() {
        setTextViewDate(textViewDate, new Date());
    }

    /**
     * 设置日期的Listener
     */
    private void setListenerDate() {
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                createDialogDate(textViewDate, getContext()).show();
            }
        });
    }


    // 备注相关
    /**
     * 设置备注的默认形式
     */
    private void setDefaultRemark() {
        textViewRemark.setText("None");
    }

    /**
     * 设置备注的Listener
     */
    private void setListenerRemark() {
        textViewRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                createDialogRemark(textViewRemark, getContext(), getActivity()).show();
            }
        });
    }


    // 更新数据相关
    /**
     * 更新所有一级支出分类信息
     */
    private void updateListClass1s() {
        listClass1s = class1Db.getCategoryListByType(2); // 2->收入分类
        sortListClass1s(listClass1s);
        class1Id = listClass1s.get(0).getId();
    }

    /**
     * 更新所有二级支出分类信息
     */
    private void updateListClass2s() {
        listClass2s = class2Db.subcategoryList(class1Id);
    }

    /**
     * 更新所有账户信息
     */
    private void updateListAccounts() {
        listAccounts = accountDb.accountList();
    }

    /**
     * 检查数据合法性
     */
    private void checkDataValidity() {
        for (Class1 class1 : listClass1s) {
            ArrayList<Class2> listClass2 = class2Db.subcategoryList(class1.getId());
            if (listClass2.isEmpty()) {
                getActivity().finish();
                Toast.makeText(getContext(), "存在某个一级分类，其不含有二级分类。请更新二级分类数据", Toast.LENGTH_LONG).show();
            }
        }

        if (listAccounts.isEmpty()) {
            getActivity().finish();
            Toast.makeText(getContext(), "账户列表为空。请更新账户数据", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 若Scheme为更新状态, 则使用数据更新控件
     */
    private void updateDataForUpdateScheme() {
        if (recordScheme.equals("Update")) {
            Record record = recordDb.getRecordListById(updateRecordId).get(0);

            float money = record.getMoney();
            int class1Id = record.getClass1Id();
            int class2Id = record.getClass2Id();
            int accountId = record.getAccountId();
            Date date = record.getDate();
            String remark = record.getRemark();

            setEditTextDecimalMoney(editTextMoney, money);
            setSpinnerPositionClass1ById(class1Id);
            setSpinnerPositionClass2ById(class2Id);
            setSpinnerPositionAccountById(accountId);
            setTextViewDate(textViewDate, date);
            textViewRemark.setText(remark);
        }
    }

    /**
     * 若Scheme为模板添加状态, 则使用数据更新控件
     */
    private void updateDataForInsertCollectionScheme() {
        if (recordScheme.equals("InsertFromCollection")) {
            Collection collection = collectionDb.getTemplateListById(insertCollectionId).get(0);

            float money = collection.getMoney();
            int class1Id = collection.getClass1Id();
            int class2Id = collection.getClass2Id();
            int accountId = collection.getAccountId();
            String remark = collection.getRemark();

            setEditTextDecimalMoney(editTextMoney, money);
            setSpinnerPositionClass1ById(class1Id);
            setSpinnerPositionClass2ById(class2Id);
            setSpinnerPositionAccountById(accountId);
            textViewRemark.setText(remark);
        }
    }


    // 修改数据相关
    /**
     * 向数据库中添加数据
     */
    private String insertRecord(Record recordInsert) {
        return recordDb.insertRecord(recordInsert);
    }

    /**
     * 向数据库中更新数据
     */
    private String updateRecord(Record recordUpdate) {
        return recordDb.updateRecord(recordUpdate);
    }

    /**
     * 向数据库中添加模板数据
     */
    private String insertCollection(Collection collectionInsert) {
        return collectionDb.insertTemplate(collectionInsert);
    }

    /**
     * 获取用于添加的Record数据
     *
     * @return record数据
     */
    private Record getRecordInsert() {
        int tAccountId = ((Account)spinnerAccount.getSelectedItem()).getId();
        float tMoney = getEditTextMoney(editTextMoney);
        int tType = 2; // 2->收入
        Date tDate = getDate(textViewDate);
        String tRemark = textViewRemark.getText().toString();
        int tClass1Id = ((Class1)spinnerClass1.getSelectedItem()).getId();
        int tClass2Id = ((Class2)spinnerClass2.getSelectedItem()).getId();
        return new Record(tAccountId, tMoney, tType, tDate, tRemark, tClass1Id, tClass2Id);
    }

    /**
     * 获取用于更新的Record数据
     *
     * @return record数据
     */
    private Record getRecordUpdate(int tId) {
        int tAccountId = ((Account)spinnerAccount.getSelectedItem()).getId();
        float tMoney = getEditTextMoney(editTextMoney);
        int tType = 2; // 2->收入
        Date tDate = getDate(textViewDate);
        String tRemark = textViewRemark.getText().toString();
        int tClass1Id = ((Class1)spinnerClass1.getSelectedItem()).getId();
        int tClass2Id = ((Class2)spinnerClass2.getSelectedItem()).getId();
        return new Record(tId, tAccountId, tMoney, tType, tDate, tRemark, tClass1Id, tClass2Id);
    }

    /**
     * 获取用于添加的Collection数据
     *
     * @return collection数据
     */
    private Collection getCollectionInsert() {
        int tAccountId = ((Account)spinnerAccount.getSelectedItem()).getId();
        float tMoney = getEditTextMoney(editTextMoney);
        int tType = 2; // 2->收入
        Date tDate = getDate(textViewDate);
        String tRemark = textViewRemark.getText().toString();
        int tClass1Id = ((Class1)spinnerClass1.getSelectedItem()).getId();
        int tClass2Id = ((Class2)spinnerClass2.getSelectedItem()).getId();
        return new Collection(tAccountId, tMoney, tType, tDate, tRemark, tClass1Id, tClass2Id);
    }

}
