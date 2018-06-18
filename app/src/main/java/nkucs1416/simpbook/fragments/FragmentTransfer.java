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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.database.AccountDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.database.RecordDb;
import nkucs1416.simpbook.database.TemplateDb;
import nkucs1416.simpbook.main.ActivityMain;
import nkucs1416.simpbook.record.ActivityRecord;
import nkucs1416.simpbook.statement.ActivityStatement;
import nkucs1416.simpbook.util.Account;
import nkucs1416.simpbook.util.Collection;
import nkucs1416.simpbook.util.Record;
import nkucs1416.simpbook.util.SpinnerAdapterAccount;
import nkucs1416.simpbook.util.Date;

import static nkucs1416.simpbook.util.Date.*;
import static nkucs1416.simpbook.util.Money.*;
import static nkucs1416.simpbook.util.Other.displayToast;
import static nkucs1416.simpbook.util.Remark.createDialogRemark;

public class FragmentTransfer extends Fragment {
    private View view;
    private Spinner spinnerAccountFrom;
    private Spinner spinnerAccountTo;
    private EditText editTextMoney;
    private TextView textViewDate;
    private TextView textViewRemark;
    private FloatingActionButton buttonAdd;

    private SpinnerAdapterAccount adapterAccount;

    private SQLiteDatabase sqLiteDatabase;
    private AccountDb accountDb;
    private RecordDb recordDb;
    private TemplateDb collectionDb;

    private ArrayList<Account> listAccounts;

    private String recordScheme;
    private int updateRecordId;
    private int insertCollectionId;

    private OnFragmentInteractionListener fragmentInteractionListener;


    // Fragment相关
    public FragmentTransfer() {
    }

    public static FragmentTransfer newInstance() {
        FragmentTransfer fragment = new FragmentTransfer();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transfer, container, false);
        initFindById();
        initRecordScheme();

        initMoney();
        initDate();
        initRemark();

        initDatabase();
        initData();

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
        editTextMoney = view.findViewById(R.id.ftransfer_editview_money);
        spinnerAccountFrom = view.findViewById(R.id.ftransfer_spinner_account);
        spinnerAccountTo = view.findViewById(R.id.ftransfer_spinner_accountto);
        textViewDate = view.findViewById(R.id.ftransfer_textview_date);
        textViewRemark = view.findViewById(R.id.ftransfer_textview_remark);
        buttonAdd = view.findViewById(R.id.ftransfer_button_add);
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
     * 初始化账户
     */
    private void initAccount() {
        adapterAccount = new SpinnerAdapterAccount(getActivity(), listAccounts);
        spinnerAccountFrom.setAdapter(adapterAccount);
        spinnerAccountTo.setAdapter(adapterAccount);
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

                        if (recordInsert.getAccountId() == recordInsert.getToAccountId()) {
                            displayToast("发送账户不能与接收账户相同", getContext(), 1);
                            break;
                        }

                        String messageInsert = insertRecord(recordInsert);
                        if (messageInsert.equals("成功")) {
                            displayToast(messageInsert, getContext(), 0);
                            Intent intent = new Intent(getContext(), ActivityMain.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            displayToast(messageInsert, getContext(), 1);
                        }
                        break;

                    case "Update":
                        Record recordUpdate = getRecordUpdate(updateRecordId);

                        if (recordUpdate.getAccountId() == recordUpdate.getToAccountId()) {
                            displayToast("发送账户不能与接收账户相同", getContext(), 1);
                            break;
                        }

                        String messageUpdate = updateRecord(recordUpdate);
                        if (messageUpdate.equals("成功")) {
                            displayToast(messageUpdate, getContext(), 0);
                            getActivity().finish();
                        } else {
                            displayToast(messageUpdate, getContext(), 1);
                        }
                        break;

                    case "Collection":
                        Collection collection = getCollectionInsert();

                        if (collection.getAccountId() == collection.getToAccountId()) {
                            displayToast("发送账户不能与接收账户相同", getContext(), 1);
                            break;
                        }

                        String messageCollectionInsert = insertCollection(collection);
                        if (messageCollectionInsert.equals("成功")) {
                            displayToast(messageCollectionInsert, getContext(), 0);
                            Intent intent = new Intent(getContext(), ActivityRecord.class);
                            intent.putExtra("RecordType","Collection");
                            intent.putExtra("RecordScheme","Insert");
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            displayToast(messageCollectionInsert, getContext(), 1);
                        }
                        break;

                    default:
                        displayToast("内部错误: RecordScheme值错误", getContext(), 1);
                        break;
                }
                getActivity().finish();
            }
        });
    }

    /**
     * 初始化数据库
     */
    private void initDatabase() {
        CustomSQLiteOpenHelper customSQLiteOpenHelper = new CustomSQLiteOpenHelper(getContext());
        sqLiteDatabase = customSQLiteOpenHelper.getWritableDatabase();
        accountDb = new AccountDb(sqLiteDatabase);
        recordDb = new RecordDb(sqLiteDatabase);
        collectionDb = new TemplateDb(sqLiteDatabase);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        updateListAccounts();
    }


    // 账户相关
    /**
     * 为SpinnerAccount设置与accountId实例相同的位置
     *
     * @param accountFromId 需要显示的发送account实例id
     * @param accountToId 需要显示的接收account实例id
     */
    private void setSpinnerPositionAccountById(int accountFromId, int accountToId) {
        for(int i = 0; i < listAccounts.size(); i++) {
            if (accountFromId == listAccounts.get(i).getId()) {
                spinnerAccountFrom.setSelection(i);
            }
        }
        for(int i = 0; i < listAccounts.size(); i++) {
            if (accountToId == listAccounts.get(i).getId()) {
                spinnerAccountTo.setSelection(i);
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
     * 更新所有账户信息
     */
    private void updateListAccounts() {
        listAccounts = accountDb.accountList();
    }

    /**
     * 若Scheme为更新状态, 则使用数据更新控件
     */
    private void updateDataForUpdateScheme() {
        if (recordScheme.equals("Update")) {
            Record record = recordDb.getRecordListById(updateRecordId).get(0);

            float money = record.getMoney();
            int accountFromId = record.getAccountId();
            int accountToId = record.getToAccountId();
            Date date = record.getDate();
            String remark = record.getRemark();

            setEditTextDecimalMoney(editTextMoney, money);
            setSpinnerPositionAccountById(accountFromId, accountToId);
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
            int accountFromId = collection.getAccountId();
            int accountToId = collection.getToAccountId();
            String remark = collection.getRemark();

            setEditTextDecimalMoney(editTextMoney, money);
            setSpinnerPositionAccountById(accountFromId, accountToId);
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
        int tAccountFromId = ((Account)spinnerAccountFrom.getSelectedItem()).getId();
        int tAccountToId = ((Account)spinnerAccountTo.getSelectedItem()).getId();
        float tMoney = getEditTextMoney(editTextMoney);
        int tType = 3; // 3->转账
        Date tDate = getDate(textViewDate);
        String tRemark = textViewRemark.getText().toString();
        return new Record(tAccountFromId, tMoney, tType, tDate, tRemark, tAccountToId);
    }

    /**
     * 获取用于更新的Record数据
     *
     * @return record数据
     */
    private Record getRecordUpdate(int tId) {
        int tAccountFromId = ((Account)spinnerAccountFrom.getSelectedItem()).getId();
        int tAccountToId = ((Account)spinnerAccountTo.getSelectedItem()).getId();
        float tMoney = getEditTextMoney(editTextMoney);
        int tType = 3; // 3->转账
        Date tDate = getDate(textViewDate);
        String tRemark = textViewRemark.getText().toString();
        return new Record(tId, tAccountFromId, tMoney, tType, tDate, tRemark, tAccountToId);
    }

    /**
     * 获取用于添加的Collection数据
     *
     * @return collection数据
     */
    private Collection getCollectionInsert() {
        int tAccountFromId = ((Account)spinnerAccountFrom.getSelectedItem()).getId();
        int tAccountToId = ((Account)spinnerAccountTo.getSelectedItem()).getId();
        float tMoney = getEditTextMoney(editTextMoney);
        int tType = 3; // 3->转账
        Date tDate = getDate(textViewDate);
        String tRemark = textViewRemark.getText().toString();
        return new Collection(tAccountFromId, tMoney, tType, tDate, tRemark, tAccountToId);
    }

}
