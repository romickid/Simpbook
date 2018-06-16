package nkucs1416.simpbook.statement;

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
import nkucs1416.simpbook.database.CategoryDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.database.SubcategoryDb;
import nkucs1416.simpbook.edit.ActivityEdit;
import nkucs1416.simpbook.util.Class1;
import nkucs1416.simpbook.util.Class2;
import nkucs1416.simpbook.util.Date;
import nkucs1416.simpbook.util.Record;

import static nkucs1416.simpbook.util.Color.getColorIcon;
import static nkucs1416.simpbook.util.Date.getStrDate;
import static nkucs1416.simpbook.util.Date.setTextViewDate;
import static nkucs1416.simpbook.util.Money.setTextViewDecimalMoney;
import static nkucs1416.simpbook.util.Record.getRecordTypeName;

public class AdapterStatement extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap<String, Object>> listStatementObjects;
    private Context context;

    private SQLiteDatabase sqLiteDatabase;
    private CategoryDb class1Db;
    private SubcategoryDb class2Db;


    // RecyclerView.Adapter相关
    /**
     * 构造函数, 读取需要绘制的AccountObjects列表
     *
     * @param tContext 传入的Context
     * @param tListStatementObjects 传入的StatementObjects列表
     */
    AdapterStatement(Context tContext, ArrayList<HashMap<String, Object>> tListStatementObjects) {
        super();
        this.context = tContext;
        this.listStatementObjects = tListStatementObjects;
    }

    /**
     * 根据不同的viewType构建不同的ViewHolder
     *
     * @param parent default
     * @param viewType 1:Record, 2:StatementDate
     * @return viewHolder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        updateDatabase();
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_statement_record, parent, false);
                return new ViewHolderStatementElement(view);
            case 2:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_statement_date, parent, false);
                return new ViewHolderStatementDate(view);
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
                ViewHolderStatementElement viewHolderStatementElement = (ViewHolderStatementElement) holder;
                Record record = (Record)listStatementObjects.get(position).get("Object");

                final int class2Id = record.getClass2Id();
                final Class2 class2 = class2Db.getSubcategoryListById(class2Id).get(0);
                final int colorId1 = class2.getColor();
                final int colorIcon1 = getColorIcon(colorId1);
                final String text1 = class2.getName();
                final float money1 = record.getMoney();
                final int recordType1 = record.getType();
                final int recordId1 = record.getId();

                final ImageView imageViewBackground1 = viewHolderStatementElement.imageViewBackground;
                final Context context1 = viewHolderStatementElement.context;

                imageViewBackground1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context1, ActivityEdit.class);
                        intent.putExtra("RecordType", getRecordTypeName(recordType1));
                        intent.putExtra("RecordScheme", "Update");
                        intent.putExtra("RecordUpdateId", String.valueOf(recordId1));
                        context1.startActivity(intent);
                    }
                });

                viewHolderStatementElement.imageView.setImageResource(colorIcon1);
                viewHolderStatementElement.textViewText.setText(text1);
                setTextViewDecimalMoney(viewHolderStatementElement.textViewMoney, money1);

                break;
            case 2:
                ViewHolderStatementDate viewHolderStatementDate = (ViewHolderStatementDate) holder;
                StatementDate statementDate = (StatementDate)listStatementObjects.get(position).get("Object");

                final Date date2 = statementDate.getDate();

                setTextViewDate(viewHolderStatementDate.textViewText, date2);

                break;
        }
    }

    /**
     * 获取View的类型
     *
     * @param position 数组的位置
     * @return 类型(1:AccountElement, 2:AccountSummarize)
     */
    @Override
    public int getItemViewType(int position) {
        return (int)listStatementObjects.get(position).get("StatementObjectViewType");
    }

    /**
     * 获取Objects数量
     *
     * @return 数量
     */
    @Override
    public int getItemCount() {
        return listStatementObjects.size();
    }


    // 数据库相关
    /**
     * 更新数据库
     */
    private void updateDatabase() {
        CustomSQLiteOpenHelper customSQLiteOpenHelper = new CustomSQLiteOpenHelper(context);
        sqLiteDatabase = customSQLiteOpenHelper.getWritableDatabase();
        class1Db = new CategoryDb(sqLiteDatabase);
        class2Db = new SubcategoryDb(sqLiteDatabase);
    }

}
