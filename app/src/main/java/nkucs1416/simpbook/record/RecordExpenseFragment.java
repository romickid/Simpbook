package nkucs1416.simpbook.record;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
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
import android.text.TextWatcher;
import android.text.Editable;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.util.MyDate;
import nkucs1416.simpbook.util.MySpinnerAdapter;

public class RecordExpenseFragment extends Fragment {
    private View view;
    private Spinner spinnerClass1;
    private Spinner spinnerClass2;
    private Spinner spinnerAccount;
    private EditText editTextMoney;
    private TextView textViewDate;
    private TextView textViewRemark;
    private MySpinnerAdapter adapterClass1;
    private MySpinnerAdapter adapterClass2;
    private MySpinnerAdapter adapterAccount;
    private ArrayList<Map<String,Object>> listClass1;
    private ArrayList<Map<String,Object>> listClass2;
    private ArrayList<Map<String,Object>> listAccount;

    private OnFragmentInteractionListener fragmentInteractionListener;


    // Fragment相关
    public RecordExpenseFragment() {
        // Required empty public constructor
    }

    public static RecordExpenseFragment newInstance(String param1, String param2) {
        RecordExpenseFragment fragment = new RecordExpenseFragment();
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

        view = inflater.inflate(R.layout.fragment_record_expense, container, false);
        initFindById();

        initMoney();
        initClass();
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
        editTextMoney = view.findViewById(R.id.fexpense_text_money);
        spinnerClass1 = view.findViewById(R.id.fexpense_spinner_class1);
        spinnerClass2 = view.findViewById(R.id.fexpense_spinner_class2);
        spinnerAccount = view.findViewById(R.id.fexpense_spinner_account);
        textViewDate = view.findViewById(R.id.fexpense_text_date);
        textViewRemark = view.findViewById(R.id.fexpense_text_remark);
    }

    /**
     * 初始化金额
     */
    private void initMoney() {
        setEditTextMoneyDecimal();
    }

    /**
     * 初始化分类
     */
    private void initClass() {
        listClass1 = new ArrayList<>();
        demoSetSpinnerList();

        adapterClass1 = new MySpinnerAdapter(getActivity(), listClass1);
        spinnerClass1.setAdapter(adapterClass1);

        adapterClass2 = new MySpinnerAdapter(getActivity(), listClass1);
        spinnerClass2.setAdapter(adapterClass2);
    }

    /**
     * 初始化账户
     */
    private void initAccount() {
        adapterAccount = new MySpinnerAdapter(getActivity(), listClass1);
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


    // 金额相关
    /**
     * 设置金额的格式化(输入框设置为2位小数)
     */
    public void setEditTextMoneyDecimal() {
        editTextMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editTextMoney.setText(s);
                        editTextMoney.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editTextMoney.setText(s);
                    editTextMoney.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editTextMoney.setText(s.subSequence(0, 1));
                        editTextMoney.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }


    // 分类相关
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


    // 账户相关


    // 日期相关
    /**
     * 设置日期的默认形式(使用者使用的当天日期)
     */
    private void setDefaultDate() {
        setTextViewDate(new MyDate());
    }

    /**
     * 设置日期的显示
     * @param myDate 被设置的日期
     */
    private void setTextViewDate(MyDate myDate) {
        textViewDate.setText(myDate.getYear() + "年" + myDate.getMonth() + "月" + myDate.getDay() + "日");
    }

    /**
     * 设置日期的Listener
     */
    private void setListenerDate() {
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                createDialogDate().show();
            }
        });

    }

    /**
     * 构建选择日期的Dialog
     * @return 返回Dialog
     */
    private Dialog createDialogDate() {
        Dialog dialog = null;
        OnDateSetListener listener = null;
        MyDate myDate = new MyDate();

        listener = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setTextViewDate(new MyDate(year, month+1, dayOfMonth));
            }
        };


        dialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, listener, myDate.getYear(), myDate.getMonth(), myDate.getDay());
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
     * @return 返回Dialog
     */
    private Dialog createDialogRemark() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),3);
        View viewRemarkDialog = View.inflate(getActivity(), R.layout.dialog_remark, null);
        final EditText editTextDialog = viewRemarkDialog.findViewById(R.id.dremark_edittext);
        builder.setTitle("备注");
        builder.setView(viewRemarkDialog);
        editTextDialog.setText(textViewRemark.getText());

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

