package nkucs1416.simpbook.fragments.Collection;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.database.AccountDb;
import nkucs1416.simpbook.database.CategoryDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.database.SubcategoryDb;
import nkucs1416.simpbook.record.ActivityRecord;
import nkucs1416.simpbook.util.Account;
import nkucs1416.simpbook.util.Class2;
import nkucs1416.simpbook.util.Collection;

import static nkucs1416.simpbook.util.Color.getColorIcon;
import static nkucs1416.simpbook.util.Money.setTextViewDecimalMoney;
import static nkucs1416.simpbook.util.Record.getRecordTypeName;

public class AdapterCollection extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap<String, Object>> listCollectionObjects;
    private Context context;

    private SQLiteDatabase sqLiteDatabase;
    private CategoryDb class1Db;
    private SubcategoryDb class2Db;
    private AccountDb accountDb;


    // RecyclerView.Adapter相关
    /**
     * 构造函数, 读取需要绘制的CollectionObjects列表
     *
     * @param tContext 传入的Context
     * @param tListCollectionObjects 传入的CollectionObjects列表
     */
    AdapterCollection(Context tContext, ArrayList<HashMap<String, Object>> tListCollectionObjects) {
        super();
        this.context = tContext;
        this.listCollectionObjects = tListCollectionObjects;
    }

    /**
     * 根据不同的viewType构建不同的ViewHolder
     *
     * @param parent default
     * @param viewType 1:CollectionDefault, 2:CollectionTransfer, 3:CollectionSummarize
     * @return viewHolder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        initDatabase();
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_statement_recorddefault, parent, false);
                return new ViewHolderCollectionDefault(view);
            case 2:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_statement_recordtransfer, parent, false);
                return new ViewHolderCollectionTransfer(view);
            case 3:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_collection_summerize, parent, false);
                return new ViewHolderCollectionSummarize(view);
        }
        return null;
    }

    /**
     * 根据不同的位置, 为holder构建不同的实例
     *
     * @param holder 传入的holder实例
     * @param position 位置(不同位置viewType不同)
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 1:
                ViewHolderCollectionDefault viewHolderCollectionDefault = (ViewHolderCollectionDefault) holder;
                final Collection collection_1 = (Collection)listCollectionObjects.get(position).get("Object");

                final int class2Id_1 = collection_1.getClass2Id();
                final Class2 class2_1 = class2Db.getSubcategoryListById(class2Id_1).get(0);

                final int colorId_1 = class2_1.getColor();
                final int colorIcon_1 = getColorIcon(colorId_1);
                final String text_1 = class2_1.getName();
                final float money_1 = collection_1.getMoney();
                final int collectionType_1 = collection_1.getType();
                final int collectionId_1 = collection_1.getId();

                final ImageView imageViewBackground_1 = viewHolderCollectionDefault.imageViewBackground;
                final Context context_1 = viewHolderCollectionDefault.context;

                imageViewBackground_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context_1, ActivityRecord.class);
                        intent.putExtra("RecordType", getRecordTypeName(collectionType_1));
                        intent.putExtra("RecordScheme", "InsertFromCollection");
                        intent.putExtra("RecordCollectionId", String.valueOf(collectionId_1));
                        context_1.startActivity(intent);
                    }
                });

                viewHolderCollectionDefault.imageViewColor.setImageResource(colorIcon_1);
                viewHolderCollectionDefault.textViewText.setText(text_1);
                setTextViewDecimalMoney(viewHolderCollectionDefault.textViewMoney, money_1);

                break;

            case 2:
                ViewHolderCollectionTransfer viewHolderCollectionTransfer = (ViewHolderCollectionTransfer) holder;
                final Collection collection_2 = (Collection)listCollectionObjects.get(position).get("Object");

                final int accountFromId_2 = collection_2.getAccountId();
                final int accountToId_2 = collection_2.getToAccountId();

                System.out.println("CollectionId" + collection_2.getId());
                System.out.println("AccountFromId" + accountFromId_2);
                System.out.println("AccountToId" + accountToId_2);

                final Account accountFrom = accountDb.getAccountListById(accountFromId_2).get(0);
                final Account accountTo = accountDb.getAccountListById(accountToId_2).get(0);

                final int colorIdFrom_2 = accountFrom.getColor();
                final int colorIconFrom_2 = getColorIcon(colorIdFrom_2);

                final String textFrom_2 = accountFrom.getName();
                final String textTo_2 = accountTo.getName();

                final float money_2 = collection_2.getMoney();
                final int recordType_2 = collection_2.getType();
                final int collectionId_2 = collection_2.getId();

                final ImageView imageViewBackground_2 = viewHolderCollectionTransfer.imageViewBackground;
                final Context context_2 = viewHolderCollectionTransfer.context;

                imageViewBackground_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context_2, ActivityRecord.class);
                        intent.putExtra("RecordType", getRecordTypeName(recordType_2));
                        intent.putExtra("RecordScheme", "InsertFromCollection");
                        intent.putExtra("RecordCollectionId", String.valueOf(collectionId_2));
                        context_2.startActivity(intent);
                    }
                });

                viewHolderCollectionTransfer.imageViewFrom.setImageResource(colorIconFrom_2);
                viewHolderCollectionTransfer.textViewTextFrom.setText(textFrom_2);
                viewHolderCollectionTransfer.textViewTextTo.setText(textTo_2);
                setTextViewDecimalMoney(viewHolderCollectionTransfer.textViewMoney, money_2);

                break;

            case 3:
                ViewHolderCollectionSummarize viewHolderCollectionSummarize = (ViewHolderCollectionSummarize) holder;
                CollectionSummarize collectionSummarize = (CollectionSummarize)listCollectionObjects.get(position).get("Object");

                final String text2 = collectionSummarize.getText();

                viewHolderCollectionSummarize.textViewText.setText(text2);

                break;
        }
    }

    /**
     * 获取View的类型
     *
     * @param position 数组的位置
     * @return 类型(1:CollectionDefault, 2:CollectionTransfer, 3:CollectionSummarize)
     */
    @Override
    public int getItemViewType(int position) {
        return (int)listCollectionObjects.get(position).get("CollectionObjectViewType");
    }

    /**
     * 获取Objects数量
     *
     * @return 数量
     */
    @Override
    public int getItemCount() {
        return listCollectionObjects.size();
    }


    // 数据库相关
    /**
     * 更新数据库
     */
    private void initDatabase() {
        CustomSQLiteOpenHelper customSQLiteOpenHelper = new CustomSQLiteOpenHelper(context);
        sqLiteDatabase = customSQLiteOpenHelper.getWritableDatabase();
        class1Db = new CategoryDb(sqLiteDatabase);
        class2Db = new SubcategoryDb(sqLiteDatabase);
        accountDb = new AccountDb(sqLiteDatabase);
    }

}
