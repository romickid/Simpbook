package nkucs1416.simpbook.fragments.Collection;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.database.TemplateDb;
import nkucs1416.simpbook.util.Collection;

import static nkucs1416.simpbook.util.Collection.getCollectionTypeName;

public class FragmentCollection extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonAdd;

    private SQLiteDatabase sqLiteDatabase;
    private TemplateDb collectionDb;

    private ArrayList<Collection> listCollections;
    private ArrayList<HashMap<String, Object>> listCollectionObjects;
    private AdapterCollection adapterCollection;

    private OnFragmentInteractionListener fragmentInteractionListener;


    // Fragment相关
    public FragmentCollection() {
    }

    public static FragmentCollection newInstance() {
        FragmentCollection fragment = new FragmentCollection();
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
        view = inflater.inflate(R.layout.fragment_collection, container, false);

        initFindById();
        initRecyclerView();
        initButtonAdd();

        initDatabase();
        initData();

        initRecycleView();

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
        buttonAdd = view.findViewById(R.id.fcollection_button_add);
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterCollection = new AdapterCollection(getActivity(), listCollectionObjects);
        recyclerView.setAdapter(adapterCollection);
    }

    /**
     * 初始化按钮
     */
    private void initButtonAdd() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(), ActivityCollectionAdd.class);
                intent.putExtra("CollectionAddType", "Default");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterCollection adapterCollection = new AdapterCollection(getContext(), listCollectionObjects);
        recyclerView.setAdapter(adapterCollection);
    }

    /**
     * 初始化数据库
     */
    private void initDatabase() {
        CustomSQLiteOpenHelper customSQLiteOpenHelper = new CustomSQLiteOpenHelper(getContext());
        sqLiteDatabase = customSQLiteOpenHelper.getWritableDatabase();
        collectionDb = new TemplateDb(sqLiteDatabase);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        updateListCollections();
        updateListCollectionObjects();
    }


    // 数据相关
    /**
     * 获取某个模板类型的CollectionSummarizeObject
     *
     * @param type 模板类型
     * @return CollectionSummarizeObject
     */
    private HashMap<String, Object> getCollectionSummarizeObject(int type) {
        CollectionSummarize collectionSummarize = new CollectionSummarize(getCollectionTypeName(type));

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("CollectionObjectViewType", 3); // 3->Summarize
        hashMap.put("Object", collectionSummarize);
        return hashMap;
    }

    /**
     * 更新所有模板信息
     */
    private void updateListCollections() {
        listCollections = collectionDb.templateList();
    }

    /**
     * 更新ListCollectionObjects
     */
    private void updateListCollectionObjects() {
        listCollectionObjects = new ArrayList<>();

        ArrayList<Collection> listCollections1 = getListCollectionsByType(listCollections, 1);
        if (listCollections1.size() != 0)
            listCollectionObjects.add(getCollectionSummarizeObject(1));
        listCollectionObjects.addAll(getListCollectionObjects(listCollections1, 1));

        ArrayList<Collection> listCollections2 = getListCollectionsByType(listCollections, 2);
        if (listCollections2.size() != 0)
            listCollectionObjects.add(getCollectionSummarizeObject(2));
        listCollectionObjects.addAll(getListCollectionObjects(listCollections2, 1));

        ArrayList<Collection> listCollections3 = getListCollectionsByType(listCollections, 3);
        if (listCollections3.size() != 0)
            listCollectionObjects.add(getCollectionSummarizeObject(3));
        listCollectionObjects.addAll(getListCollectionObjects(listCollections3, 2));
    }

    /**
     * 获取某个模板类型的listCollections
     *
     * @param listCollections 待获取的listCollections
     * @param type 模板类型
     * @return 筛选后的listCollections
     */
    private ArrayList<Collection> getListCollectionsByType(ArrayList<Collection> listCollections, int type) {
        ArrayList<Collection> listReturn = new ArrayList<>();
        for(Collection collection : listCollections) {
            if (collection.getType() == type) {
                listReturn.add(collection);
            }
        }
        return listReturn;
    }

    /**
     * 获取某个listCollections的listCollectionObjects
     *
     * @param listCollections 待获取的listCollections
     * @return listCollectionObjects
     */
    private ArrayList<HashMap<String, Object>> getListCollectionObjects(ArrayList<Collection> listCollections, int type) {
        ArrayList<HashMap<String, Object>> listReturn = new ArrayList<>();
        HashMap<String, Object> hashMap;

        for(Collection collection: listCollections) {
            hashMap = new HashMap<>();
            hashMap.put("CollectionObjectViewType", type); // 1->Default 2->Transfer
            hashMap.put("Object", collection);
            listReturn.add(hashMap);
        }
        return listReturn;
    }

}
