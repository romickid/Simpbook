package nkucs1416.simpbook.fragments.Collection;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.util.CollectionElement;
import nkucs1416.simpbook.util.Date;


public class CollectionFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;

    private ArrayList<HashMap<String, Object>> listCollectionObjects;
    private CollectionAdapter collectionAdapter;

    private OnFragmentInteractionListener fragmentInteractionListener;


    // Fragment相关
    public CollectionFragment() {
        // Required empty public constructor
    }

    public static CollectionFragment newInstance(String param1, String param2) {
        CollectionFragment fragment = new CollectionFragment();
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

        view = inflater.inflate(R.layout.fragment_collection, container, false);

        initFindById();
        initRecyclerView();

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
        recyclerView = view.findViewById(R.id.fcollection_recyclerview);
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        demoCollectionObjectsList();
        collectionAdapter = new CollectionAdapter(getActivity(), listCollectionObjects);

        recyclerView.setAdapter(collectionAdapter);
    }


    // RecyclerView相关
    /**
     * 测试用ListCollectionObjects
     */
    private void demoCollectionObjectsList() {
        listCollectionObjects = new ArrayList<HashMap<String, Object>>();

        HashMap hashMap = null;
        CollectionElement accountElement = null;
        CollectionSummarize accountSummarize = null;
        Integer type = null;

        type = 0;
        accountSummarize = new CollectionSummarize("支出模板");
        hashMap = new HashMap<String, Object>();
        hashMap.put("type", type);
        hashMap.put("object", accountSummarize);
        listCollectionObjects.add(hashMap);

        type = 1;
        accountElement = new CollectionElement( 1, 1, 1.0f,
                1,  new Date(), "早餐", 1, 1);
        hashMap = new HashMap<String, Object>();
        hashMap.put("type", type);
        hashMap.put("object", accountElement);
        listCollectionObjects.add(hashMap);

        type = 0;
        accountSummarize = new CollectionSummarize("收入模板");
        hashMap = new HashMap<String, Object>();
        hashMap.put("type", type);
        hashMap.put("object", accountSummarize);
        listCollectionObjects.add(hashMap);

        type = 1;
        accountElement = new CollectionElement( 1, 1, 1.0f,
                1,  new Date(), "早餐", 1, 1);
        hashMap = new HashMap<String, Object>();
        hashMap.put("type", type);
        hashMap.put("object", accountElement);
        listCollectionObjects.add(hashMap);

        type = 0;
        accountSummarize = new CollectionSummarize("转账模板");
        hashMap = new HashMap<String, Object>();
        hashMap.put("type", type);
        hashMap.put("object", accountSummarize);
        listCollectionObjects.add(hashMap);

        type = 1;
        accountElement = new CollectionElement( 1, 1, 1.0f,
                1,  new Date(), "早餐", 1, 1);
        hashMap = new HashMap<String, Object>();
        hashMap.put("type", type);
        hashMap.put("object", accountElement);
        listCollectionObjects.add(hashMap);
    }

}
