package nkucs1416.simpbook.fragments;

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
import java.util.Map;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.record.CollectionAdapter;


public class CollectionFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Map<String,Object>> listClass1;
    private CollectionAdapter adapterClass1;

    private OnFragmentInteractionListener mListener;

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
        initRecycleView();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void initFindById() {
        recyclerView = (RecyclerView) view.findViewById(R.id.fcollection_recyclerview);
    }

    private void initRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listClass1 = new ArrayList<>();
        demoSetCollectionList();

        adapterClass1 = new CollectionAdapter(getActivity(), listClass1);
        recyclerView.setAdapter(adapterClass1);
    }


    private void demoSetCollectionList() {
        listClass1=new ArrayList<Map<String,Object>>();

        Map map=new HashMap<String, Object>();
        map.put("text", "中");
        map.put("money", "123.12");
        map.put("color", R.drawable.ic_lens_yellow_a400_24dp);
        listClass1.add(map);

        map=new HashMap<String, Object>();
        map.put("text", "美");
        map.put("money", "123.12");
        map.put("color", R.drawable.ic_lens_blue_a400_24dp);
        listClass1.add(map);

        map=new HashMap<String, Object>();
        map.put("text", "中国好声音");
        map.put("money", "123.12");
        map.put("color", R.drawable.ic_lens_yellow_a400_24dp);
        listClass1.add(map);

        map=new HashMap<String, Object>();
        map.put("text", "美国");
        map.put("money", "123.12");
        map.put("color", R.drawable.ic_lens_blue_a400_24dp);
        listClass1.add(map);
    }

}
