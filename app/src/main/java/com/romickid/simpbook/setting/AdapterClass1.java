package com.romickid.simpbook.setting;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

import com.romickid.simpbook.R;
import com.romickid.simpbook.database.CategoryDb;
import com.romickid.simpbook.database.CustomSQLiteOpenHelper;
import com.romickid.simpbook.util.Class1;
import com.romickid.simpbook.util.SpinnerAdapterColor;

import static com.romickid.simpbook.util.Color.getColorIcon;
import static com.romickid.simpbook.util.Color.getListColorIds;
import static com.romickid.simpbook.util.Other.displayToast;

public class AdapterClass1 extends RecyclerView.Adapter<ViewHolderClass> {
    private ArrayList<Class1> listClass1s;
    private Context context;
    private Activity activity;

    private SQLiteDatabase sqLiteDatabase;
    private CategoryDb class1Db;


    /**
     * 构造函数, 读取需要绘制的Class1列表
     *
     * @param tListClass1s 传入的Class1列表
     * @param tContext 传入的Context
     * @param tActivity 传入的Activity
     */
    AdapterClass1(ArrayList<Class1> tListClass1s, Context tContext, Activity tActivity) {
        super();
        this.listClass1s = tListClass1s;
        this.context = tContext;
        this.activity = tActivity;
    }


    /**
     * 构建ViewHolder
     *
     * @param parent default
     * @param viewType default
     * @return viewHolder
     */
    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_class_element, parent, false);

        return new ViewHolderClass(view);
    }

    /**
     * 根据不同的位置, 为holder构建不同的实例
     *
     * @param holder 传入的holder实例
     * @param position 位置
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        Class1 class1 = listClass1s.get(position);

        holder.textViewText.setText(class1.getName());
        holder.imageViewColor.setImageResource(getColorIcon(class1.getColor()));
        setListenerBackground(holder.imageViewBackground, class1.getId());
        setListenerEdit(holder.imageViewEdit, class1.getId());
        setListenerDelete(holder.imageViewDelete, class1.getId());
    }

    /**
     * 获取Class1数量
     *
     * @return 数量
     */
    @Override
    public int getItemCount() {
        return listClass1s.size();
    }


    // 跳转相关
    /**
     * 设置背景界面的Listener
     *
     * @param imageView 背景界面的imageView
     * @param class1Id 待跳转的class1Id
     */
    private void setListenerBackground(ImageView imageView, final int class1Id) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, ActivityClass2Expense.class);
                intent.putExtra("class1Id" ,String.valueOf(class1Id));
                context.startActivity(intent);
            }
        });
    }


    // 修改一级分类相关
    /**
     * 设置修改按钮的Listener
     *
     * @param imageView 修改按钮imageView
     * @param class1Id 待修改的class1Id
     */
    private void setListenerEdit(ImageView imageView, final int class1Id) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                createDialogEdit(class1Id).show();
            }
        });
    }

    /**
     * 构建修改class1的Dialog
     *
     * @return dialog
     */
    private Dialog createDialogEdit(final int class1Id) {
        updateDatabase();

        // 原数据形式
        Class1 class1 = class1Db.getCategoryListById(class1Id).get(0);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, 3);
        View viewRemarkDialog = View.inflate(context, R.layout.dialog_class1, null);

        // 绑定控件
        final EditText editText = viewRemarkDialog.findViewById(R.id.dclass1_edittext);
        final Spinner spinnerColor = viewRemarkDialog.findViewById(R.id.dclass1_spinner_color);

        // 设置控件初始值
        editText.setText(class1.getName());

        ArrayList<Integer> listColors = getListColorIds();
        SpinnerAdapterColor spinnerAdapterColor = new SpinnerAdapterColor(context, listColors);
        spinnerColor.setAdapter(spinnerAdapterColor);
        spinnerColor.setSelection(class1.getColor()-1);

        final int type = class1.getType();

        builder.setTitle("修改一级分类");
        builder.setView(viewRemarkDialog);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 获取控件内容
                String name = editText.getText().toString();
                int colorId = (int)spinnerColor.getSelectedItem();

                if (name.isEmpty()) {
                    displayToast("输入不能为空", context, 0);
                    dialog.cancel();
                    return;
                }

                Class1 class1 = getClass1Update(class1Id, name, colorId, type);
                String message = updateClass1(class1);

                if (message.equals("成功")) {
                    displayToast(message, context, 0);
                    ((Activity)(context)).recreate();
                }
                else {
                    displayToast(message, context, 1);
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }


    // 删除一级分类相关
    /**
     * 设置删除按钮的Listener
     *
     * @param imageView 删除按钮imageView
     * @param class1Id 待删除的class1Id
     */
    private void setListenerDelete(ImageView imageView, final int class1Id) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                updateDatabase();
                Class1 class1 = getClass1Delete(class1Id);
                String message = deleteClass1(class1);

                if (message.equals("成功")) {
                    displayToast(message, context, 0);
                    ((Activity)(context)).recreate();
                }
                else {
                    displayToast(message, context, 1);
                }
            }
        });
    }


    // 数据库相关
    /**
     * 更新数据库
     */
    private void updateDatabase() {
        CustomSQLiteOpenHelper customSQLiteOpenHelper = new CustomSQLiteOpenHelper(context);
        sqLiteDatabase = customSQLiteOpenHelper.getWritableDatabase();
        class1Db = new CategoryDb(sqLiteDatabase);
    }


    // 数据相关
    /**
     * 构建用于更新一级分类的Class1实例
     *
     * @param id id
     * @param name 名称
     * @param colorId 颜色id
     * @return 实例
     */
    private Class1 getClass1Update(int id, String name, int colorId, int type) {
        return new Class1(id, name, colorId, type);
    }

    /**
     * 构建用于删除一级分类的Class1实例
     *
     * @param id id
     * @return 实例
     */
    private Class1 getClass1Delete(int id) {
        return new Class1(id);
    }

    /**
     * 向数据库更新一级分类数据
     *
     * @param class1Update 更新的一级分类实例
     * @return 操作信息
     */
    private String updateClass1(Class1 class1Update) {
        return class1Db.updateCategory(class1Update);
    }

    /**
     * 向数据库删除一级分类数据
     *
     * @param class1Delete 删除的一级分类实例
     * @return 操作信息
     */
    private String deleteClass1(Class1 class1Delete) {
        return class1Db.deleteCategory(class1Delete);
    }

}
