package nkucs1416.simpbook.statement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import nkucs1416.simpbook.database.RecordDb;
import nkucs1416.simpbook.database.SubcategoryDb;
import nkucs1416.simpbook.edit.ActivityEdit;
import nkucs1416.simpbook.util.Account;
import nkucs1416.simpbook.util.Class2;
import nkucs1416.simpbook.util.Date;
import nkucs1416.simpbook.util.OnDeleteDataListener;
import nkucs1416.simpbook.util.Record;

import static nkucs1416.simpbook.util.Color.getColorIcon;
import static nkucs1416.simpbook.util.Date.setTextViewDate;
import static nkucs1416.simpbook.util.Money.setTextViewDecimalMoney;
import static nkucs1416.simpbook.util.Record.getRecordTypeName;

public class AdapterStatement extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap<String, Object>> listStatementObjects;
    private Context context;

    private SQLiteDatabase sqLiteDatabase;
    private CategoryDb class1Db;
    private SubcategoryDb class2Db;
    private AccountDb accountDb;
    private RecordDb recordDb;

    private OnDeleteDataListener onDeleteDataListener;


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
        this.onDeleteDataListener = (OnDeleteDataListener)tContext;
    }

    /**
     * 根据不同的viewType构建不同的ViewHolder
     *
     * @param parent default
     * @param viewType 1:RecordDefault, 2:RecordTransfer, 3:StatementDate
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
                return new ViewHolderStatementRecordDefault(view);
            case 2:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_statement_recordtransfer, parent, false);
                return new ViewHolderStatementRecordTransfer(view);
            case 3:
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
                ViewHolderStatementRecordDefault viewHolderStatementRecordDefault = (ViewHolderStatementRecordDefault) holder;
                final Record record_1 = (Record)listStatementObjects.get(position).get("Object");

                final int class2Id_1 = record_1.getClass2Id();
                final Class2 class2_1 = class2Db.getSubcategoryListById(class2Id_1).get(0);
                final int colorId_1 = class2_1.getColor();
                final int colorIcon_1 = getColorIcon(colorId_1);
                final String text_1 = class2_1.getName();
                final float money_1 = record_1.getMoney();
                final int recordType_1 = record_1.getType();
                final int recordId_1 = record_1.getId();

                final ImageView imageViewBackground_1 = viewHolderStatementRecordDefault.imageViewBackground;
                final Context context_1 = viewHolderStatementRecordDefault.context;

                imageViewBackground_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context_1, ActivityEdit.class);
                        intent.putExtra("RecordType", getRecordTypeName(recordType_1));
                        intent.putExtra("RecordScheme", "Update");
                        intent.putExtra("RecordUpdateId", String.valueOf(recordId_1));
                        context_1.startActivity(intent);
                    }
                });

                imageViewBackground_1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        createDialogDelete(record_1).show();
                        return true;
                    }
                });

                viewHolderStatementRecordDefault.imageView.setImageResource(colorIcon_1);
                viewHolderStatementRecordDefault.textViewText.setText(text_1);
                setTextViewDecimalMoney(viewHolderStatementRecordDefault.textViewMoney, money_1);

                break;

            case 2:
                ViewHolderStatementRecordTransfer viewHolderStatementRecordTransfer = (ViewHolderStatementRecordTransfer) holder;
                final Record record_2 = (Record)listStatementObjects.get(position).get("Object");

                final int accountFromId_2 = record_2.getAccountId();
                final int accountToId_2 = record_2.getToAccountId();
                System.out.println(record_2.getId());
                System.out.println(accountFromId_2);
                System.out.println(accountToId_2);
                final Account accountFrom = accountDb.getAccountListById(accountFromId_2).get(0);
                final Account accountTo = accountDb.getAccountListById(accountToId_2).get(0);

                final int colorIdFrom_2 = accountFrom.getColor();
                final int colorIconFrom_2 = getColorIcon(colorIdFrom_2);

                final String textFrom_2 = accountFrom.getName();
                final String textTo_2 = accountTo.getName();

                final float money_2 = record_2.getMoney();
                final int recordType_2 = record_2.getType();
                final int recordId_2 = record_2.getId();

                final ImageView imageViewBackground_2 = viewHolderStatementRecordTransfer.imageViewBackground;
                final Context context_2 = viewHolderStatementRecordTransfer.context;

                imageViewBackground_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context_2, ActivityEdit.class);
                        intent.putExtra("RecordType", getRecordTypeName(recordType_2));
                        intent.putExtra("RecordScheme", "Update");
                        intent.putExtra("RecordUpdateId", String.valueOf(recordId_2));
                        context_2.startActivity(intent);
                    }
                });

                imageViewBackground_2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        createDialogDelete(record_2).show();
                        return true;
                    }
                });

                viewHolderStatementRecordTransfer.imageViewFrom.setImageResource(colorIconFrom_2);
                viewHolderStatementRecordTransfer.textViewTextFrom.setText(textFrom_2);
                viewHolderStatementRecordTransfer.textViewTextTo.setText(textTo_2);
                setTextViewDecimalMoney(viewHolderStatementRecordTransfer.textViewMoney, money_2);

                break;

            case 3:
                ViewHolderStatementDate viewHolderStatementDate = (ViewHolderStatementDate) holder;
                StatementDate statementDate = (StatementDate)listStatementObjects.get(position).get("Object");

                final Date date_3 = statementDate.getDate();

                setTextViewDate(viewHolderStatementDate.textViewText, date_3);

                break;
        }
    }

    /**
     * 获取View的类型
     *
     * @param position 数组的位置
     * @return 类型(1:RecordDefault, 2:RecordTransfer, 3:StatementDate)
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
    private void initDatabase() {
        CustomSQLiteOpenHelper customSQLiteOpenHelper = new CustomSQLiteOpenHelper(context);
        sqLiteDatabase = customSQLiteOpenHelper.getWritableDatabase();
        class1Db = new CategoryDb(sqLiteDatabase);
        class2Db = new SubcategoryDb(sqLiteDatabase);
        accountDb = new AccountDb(sqLiteDatabase);
        recordDb = new RecordDb(sqLiteDatabase);
    }


    // 删除记录Dialog相关
    /**
     * 构建删除记录的Dialog
     *
     * @param recordDelete 待删除的记录实例
     * @return 返回Dialog
     */
    private Dialog createDialogDelete(final Record recordDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("删除该记录?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        recordDb.deleteRecord(recordDelete);
                        onDeleteDataListener.OnDeleteData();
                    }
                });

        return builder.create();
    }

}

