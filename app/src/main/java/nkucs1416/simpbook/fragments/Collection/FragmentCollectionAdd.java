package nkucs1416.simpbook.fragments.Collection;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import nkucs1416.simpbook.R;

public class FragmentCollectionAdd extends Fragment {
    private View view;

    private Button buttonExpense;
    private Button buttonIncome;
    private Button buttonTransfer;

    private OnFragmentInteractionListener fragmentInteractionListener;


    // Fragment相关
    public FragmentCollectionAdd() {
    }

    public static FragmentCollectionAdd newInstance() {
        FragmentCollectionAdd fragment = new FragmentCollectionAdd();
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
        view = inflater.inflate(R.layout.fragment_collectionadd, container, false);
        initFindById();
        initButton();

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
        buttonExpense = view.findViewById(R.id.fcollectionadd_expense);
        buttonIncome = view.findViewById(R.id.fcollectionadd_income);
        buttonTransfer = view.findViewById(R.id.fcollectionadd_transfer);
    }

    /**
     * 初始化按钮
     */
    private void initButton() {
        initButtonExpense();
        initButtonIncome();
        initButtonTransfer();
    }

    /**
     * 初始化支出模板按钮
     */
    private void initButtonExpense() {
        buttonExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(), ActivityCollectionAdd.class);
                intent.putExtra("CollectionAddType", "Expense");
                intent.putExtra("RecordScheme","Collection");
                startActivity(intent);
            }
        });

    }

    /**
     * 初始化收入模板按钮
     */
    private void initButtonIncome() {
        buttonIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(), ActivityCollectionAdd.class);
                intent.putExtra("CollectionAddType", "Income");
                intent.putExtra("RecordScheme","Collection");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化转账模板按钮
     */
    private void initButtonTransfer() {
        buttonTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(), ActivityCollectionAdd.class);
                intent.putExtra("CollectionAddType", "Transfer");
                intent.putExtra("RecordScheme","Collection");
                startActivity(intent);
            }
        });
    }

}
