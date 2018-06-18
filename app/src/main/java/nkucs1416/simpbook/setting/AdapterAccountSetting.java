package nkucs1416.simpbook.setting;

import android.app.Activity;
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
import nkucs1416.simpbook.account.AccountSummarize;
import nkucs1416.simpbook.account.ActivityAccount;
import nkucs1416.simpbook.account.ActivityAccountEdit;
import nkucs1416.simpbook.account.ViewHolderAccountDefault;
import nkucs1416.simpbook.account.ViewHolderAccountSummarize;
import nkucs1416.simpbook.database.AccountDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.util.Account;
import nkucs1416.simpbook.util.OnDeleteDataListener;

import static nkucs1416.simpbook.util.Color.getColorIcon;
import static nkucs1416.simpbook.util.Money.setTextViewDecimalMoney;
import static nkucs1416.simpbook.util.Other.displayToast;

public class AdapterAccountSetting extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap<String, Object>> listAccountObjects;
    private Context context;
    private Activity activity;

    private AccountDb accountDb;

    private OnDeleteDataListener onDeleteDataListener;


    // RecyclerView.Adapter相关
    /**
     * 构造函数, 读取需要绘制的AccountObjects列表
     *
     * @param tListAccountObjects 传入的AccountObjects列表
     * @param tContext 传入的Context
     * @param tActivity 传入的Activity
     */
    public AdapterAccountSetting(ArrayList<HashMap<String, Object>> tListAccountObjects, Context tContext, Activity tActivity) {
        super();
        this.listAccountObjects = tListAccountObjects;
        this.context = tContext;
        this.activity = tActivity;
        this.onDeleteDataListener = (OnDeleteDataListener)tContext;
    }

    /**
     * 根据不同的viewType构建不同的ViewHolder
     *
     * @param parent default
     * @param viewType 1:AccountDefault, 2:AccountSummarize
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
                        .inflate(R.layout.item_account_default, parent, false);
                return new ViewHolderAccountDefault(view);
            case 2:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_account_summarize, parent, false);
                return new ViewHolderAccountSummarize(view);
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
                ViewHolderAccountDefault viewHolderAccountDefault = (ViewHolderAccountDefault) holder;
                final Account account = (Account)listAccountObjects.get(position).get("Object");

                final int colorIcon1 = getColorIcon(account.getColor());
                final String text1 = account.getName();
                final float money1 = account.getMoney();

                final ImageView imageViewBackground1 = viewHolderAccountDefault.imageViewBackground;
                final Context context1 = viewHolderAccountDefault.context;

                imageViewBackground1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context1, ActivityAccountEdit.class);
                        intent.putExtra("AccountEditScheme", "Update");
                        intent.putExtra("AccountUpdateId", String.valueOf(account.getId()));
                        context1.startActivity(intent);
                    }
                });

                imageViewBackground1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        createDialogDelete(account).show();
                        return true;
                    }
                });

                viewHolderAccountDefault.textViewText.setText(text1);
                setTextViewDecimalMoney(viewHolderAccountDefault.textViewMoney, money1);
                viewHolderAccountDefault.imageViewColor.setImageResource(colorIcon1);
                break;

            case 2:
                ViewHolderAccountSummarize viewHolderAccountSummarize = (ViewHolderAccountSummarize) holder;
                AccountSummarize accountSummarize = (AccountSummarize)listAccountObjects.get(position).get("Object");

                final String text2 = accountSummarize.getText();
                final float money2 = accountSummarize.getMoney();

                viewHolderAccountSummarize.textViewText.setText(text2);
                setTextViewDecimalMoney(viewHolderAccountSummarize.textViewMoney, money2);
                break;
        }
    }

    /**
     * 获取View的类型
     *
     * @param position 数组的位置
     * @return 类型(1:AccountDefault, 2:AccountSummarize)
     */
    @Override
    public int getItemViewType(int position) {
        return (int)listAccountObjects.get(position).get("AccountObjectViewType");
    }

    /**
     * 获取Objects数量
     *
     * @return 数量
     */
    @Override
    public int getItemCount() {
        return listAccountObjects.size();
    }


    // 数据库相关
    /**
     * 更新数据库
     */
    private void initDatabase() {
        CustomSQLiteOpenHelper customSQLiteOpenHelper = new CustomSQLiteOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = customSQLiteOpenHelper.getWritableDatabase();
        accountDb = new AccountDb(sqLiteDatabase);
    }


    // 删除记录Dialog相关
    /**
     * 构建删除账户的Dialog
     *
     * @param accountDelete 待删除的账户实例
     * @return 返回Dialog
     */
    private Dialog createDialogDelete(final Account accountDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("删除该账户?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String message = accountDb.deleteAccount(accountDelete);
                        if (message.equals("成功")) {
                            displayToast(message, context, 0);
                        }
                        else {
                            displayToast(message, context, 1);
                        }
                        onDeleteDataListener.OnDeleteData();
                    }
                });

        return builder.create();
    }

}
