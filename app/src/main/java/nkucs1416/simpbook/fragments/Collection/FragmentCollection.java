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
import nkucs1416.simpbook.database.CategoryDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.database.SubcategoryDb;
import nkucs1416.simpbook.database.TemplateDb;
import nkucs1416.simpbook.util.Class1;
import nkucs1416.simpbook.util.Class2;
import nkucs1416.simpbook.util.Collection;
import nkucs1416.simpbook.util.OnDeleteDataListener;

import static nkucs1416.simpbook.util.Collection.getCollectionTypeName;
import static nkucs1416.simpbook.util.Other.displayToast;

public class FragmentCollection extends Fragment implements OnDeleteDataListener {
    private View view;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonAdd;

    private SQLiteDatabase sqLiteDatabase;
    private TemplateDb collectionDb;
    private CategoryDb class1Db;
    private SubcategoryDb class2Db;

    private AdapterCollection adapterCollection;

    private ArrayList<Collection> listCollections;
    private ArrayList<HashMap<String, Object>> listCollectionObjects;
    private ArrayList<Class1> listClass1sExpense;
    private ArrayList<Class1> listClass1sIncome;

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
        checkDataValidityEnd();

        initRecycleView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        initData();
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
        class1Db = new CategoryDb(sqLiteDatabase);
        class2Db = new SubcategoryDb(sqLiteDatabase);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        updateListClass1s();
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


    // 更新数据相关
    /**
     * 更新所有一级支出分类信息
     */
    private void updateListClass1s() {
        listClass1sExpense = class1Db.getCategoryListByType(1); // 1->支出分类
        listClass1sIncome = class1Db.getCategoryListByType(2); // 2->支出分类
    }

    /**
     * 检查数据合法性
     */
    private void checkDataValidityEnd() {
        for (Class1 class1 : listClass1sExpense) {
            ArrayList<Class2> listClass2 = class2Db.subcategoryList(class1.getId());
            if (listClass2.isEmpty()) {
                getActivity().finish();
                displayToast("存在某个一级分类，其不含有二级分类\n请在设置中更新二级分类数据", getContext(), 1);
            }
        }
        for (Class1 class1 : listClass1sIncome) {
            ArrayList<Class2> listClass2 = class2Db.subcategoryList(class1.getId());
            if (listClass2.isEmpty()) {
                getActivity().finish();
                displayToast("存在某个一级分类，其不含有二级分类\n请在设置中更新二级分类数据", getContext(), 1);
            }
        }
    }


    // Adapter-Activity数据传递相关
    @Override
    public void OnDeleteData() {
        initData();
    }

}
