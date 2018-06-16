package nkucs1416.simpbook.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Dialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

import java.util.ArrayList;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.util.Account;
import nkucs1416.simpbook.util.SpinnerAdapterAccount;
import nkucs1416.simpbook.util.Date;

import static nkucs1416.simpbook.util.Date.*;
import static nkucs1416.simpbook.util.Money.setEditTextMoneyDecimal;

public class FragmentTransfer extends Fragment {
    private View view;
    private Spinner spinnerAccount;
    private Spinner spinnerAccountTo;
    private EditText editTextMoney;
    private TextView textViewDate;
    private TextView textViewRemark;
    private FloatingActionButton buttonAdd;

    private ArrayList<Account> listAccounts;

    private OnFragmentInteractionListener fragmentInteractionListener;


    // Fragment相关
    public FragmentTransfer() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_transfer, container, false);
        initFindById();

        initMoney();
        initAccount();
        initDate();
        initRemark();

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
        spinnerAccount = view.findViewById(R.id.ftransfer_spinner_account);
        spinnerAccountTo = view.findViewById(R.id.ftransfer_spinner_accountto);
        textViewDate = view.findViewById(R.id.ftransfer_textview_date);
        textViewRemark = view.findViewById(R.id.ftransfer_textview_remark);
        buttonAdd = view.findViewById(R.id.ftransfer_button_add);
    }

    /**
     * 初始化金额
     */
    private void initMoney() {
        setEditTextMoneyDecimal(editTextMoney);
    }

    /**
     * 初始化账户
     */
    private void initAccount() {
        demoSetListAccount();

        SpinnerAdapterAccount adapterAccount = new SpinnerAdapterAccount(getActivity(), listAccounts);
        SpinnerAdapterAccount adapterAccountTo = new SpinnerAdapterAccount(getActivity(), listAccounts);
        spinnerAccount.setAdapter(adapterAccount);
        spinnerAccountTo.setAdapter(adapterAccountTo);
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


    // 账户相关
    /**
     * 测试用ListAccount
     */
    private void demoSetListAccount() {
        listAccounts = new ArrayList<>();
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
                createDialogDate().show();
            }
        });

    }

    /**
     * 构建选择日期的Dialog
     *
     * @return 返回Dialog
     */
    private Dialog createDialogDate() {
        Dialog dialog = null;
        OnDateSetListener listener = null;
        Date date = new Date();

        listener = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setTextViewDate(textViewDate, new Date(year, month+1, dayOfMonth));
            }
        };

        dialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, listener, date.getYear(), date.getMonth()-1, date.getDay());
        return dialog;
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
                // TODO Auto-generated method stub
                createDialogRemark().show();
            }
        });
    }

    /**
     * 构建备注的Dialog
     *
     * @return 返回Dialog
     */
    private Dialog createDialogRemark() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),3);
        View viewRemarkDialog = View.inflate(getActivity(), R.layout.dialog_remark, null);
        final EditText editTextDialog = viewRemarkDialog.findViewById(R.id.dremark_edittext);

        editTextDialog.setText(textViewRemark.getText());

        builder.setTitle("备注");
        builder.setView(viewRemarkDialog);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textViewRemark.setText(editTextDialog.getText());
                turnoffKeyboard();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                turnoffKeyboard();
            }
        });

        return builder.create();
    }


    // 其它
    /**
     * 关闭软键盘
     */
    private void turnoffKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&& getActivity().getCurrentFocus()!=null){
            if ( getActivity().getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow( getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
