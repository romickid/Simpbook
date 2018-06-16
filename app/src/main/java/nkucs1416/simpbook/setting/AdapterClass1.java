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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.database.CategoryDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.util.Class1;
import nkucs1416.simpbook.util.SpinnerAdapterColor;

import static nkucs1416.simpbook.util.Color.getColorIcon;
import static nkucs1416.simpbook.util.Color.getListColorIds;

public class AdapterClass1 extends RecyclerView.Adapter<ViewHolderClass1> {
    private ArrayList<Class1> listClass1s;
    private Context context;

    private SQLiteDatabase sqLiteDatabase;
    private CategoryDb class1Db;


    /**
     * 构造函数, 读取需要绘制的Class1列表
     *
     * @param tContext 传入的Context
     * @param tListClass1s 传入的Class1列表
     */
    AdapterClass1(Context tContext, ArrayList<Class1> tListClass1s) {
        super();
        this.context = tContext;
        this.listClass1s = tListClass1s;
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
    public ViewHolderClass1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_class_element, parent, false);

        return new ViewHolderClass1(view);
    }

    /**
     * 根据不同的位置, 为holder构建不同的实例
     *
     * @param holder 传入的holder实例
     * @param position 位置
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass1 holder, int position) {
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


    // 修改相关
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, 3);
        View viewRemarkDialog = View.inflate(context, R.layout.dialog_class1edit, null);
        final EditText editText = viewRemarkDialog.findViewById(R.id.dclass1edit_edittext);
        final Spinner spinnerColor = viewRemarkDialog.findViewById(R.id.dclass1edit_spinner_color);

        ArrayList<Integer> listColors = getListColorIds();
        SpinnerAdapterColor spinnerAdapterColor = new SpinnerAdapterColor(context, listColors);
        spinnerColor.setAdapter(spinnerAdapterColor);

        builder.setTitle("修改一级分类");
        builder.setView(viewRemarkDialog);

        updateDatabase();

        // TODO: 6/16/2018
        ArrayList<Class1> listClass1 = class1Db.categoryList();
        Class1 class1 = null;
        for (Class1 c: listClass1) {
            if (c.getId() == class1Id) {
                class1 = c;
            }
        }

        editText.setText(class1.getName());
        spinnerColor.setSelection(class1.getColor()-1);


        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editText.getText().toString();
                int colorId = (int)spinnerColor.getSelectedItem();

                if (name.isEmpty()) {
                    Toast.makeText(context, "输入不能为空", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    return;
                }

                Class1 class1 = null;
                class1 = getClass1(class1Id, name, colorId);
                String message = updateClass1(class1);

                if (message.equals("成功")) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    ((Activity)(context)).recreate();
                }
                else {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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

    /**
     * 获取一个Class1实例
     *
     * @param id id
     * @param name 名称
     * @param colorId 颜色id
     * @return 实例
     */
    private Class1 getClass1(int id, String name, int colorId) {
        return new Class1(id, name, colorId);
    }


    // 删除相关
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
                Class1 class1 = new Class1(class1Id);
                String message = class1Db.deleteCategory(class1);

                if (message.equals("成功")) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    ((Activity)(context)).recreate();
                }
                else {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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

    /**
     * 向数据库中更新数据
     */
    private String updateClass1(Class1 class1Update) {
        return class1Db.updateCategory(class1Update);
    }

}
