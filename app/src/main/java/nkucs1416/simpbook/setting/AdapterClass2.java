package nkucs1416.simpbook.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import nkucs1416.simpbook.database.SubcategoryDb;
import nkucs1416.simpbook.util.Class1;
import nkucs1416.simpbook.util.SpinnerAdapterClass1;
import nkucs1416.simpbook.util.Class2;
import nkucs1416.simpbook.util.SpinnerAdapterColor;

import static nkucs1416.simpbook.util.Color.getColorIcon;
import static nkucs1416.simpbook.util.Color.getListColorIds;

public class AdapterClass2 extends RecyclerView.Adapter<ViewHolderClass2> {
    private ArrayList<Class2> listClass2s;
    private Context context;

    private SQLiteDatabase sqLiteDatabase;
    private CategoryDb class1Db;
    private SubcategoryDb class2Db;


    /**
     * 构造函数, 读取需要绘制的Class2列表
     *
     * @param tContext 传入的Context
     * @param tListClass2s 传入的Class2列表
     */
    AdapterClass2(Context tContext, ArrayList<Class2> tListClass2s) {
        super();
        this.context = tContext;
        this.listClass2s = tListClass2s;
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
    public ViewHolderClass2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_class_element, parent, false);

        return new ViewHolderClass2(view);
    }

    /**
     * 根据不同的位置, 为holder构建不同的实例
     *
     * @param holder 传入的holder实例
     * @param position 位置
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass2 holder, int position) {
        Class2 class2 = listClass2s.get(position);

        holder.textViewText.setText(class2.getName());
        holder.imageViewColor.setImageResource(getColorIcon(class2.getColor()));
        setListenerEdit(holder.imageViewEdit, class2.getId(), class2.getFatherId());
        setListenerDelete(holder.imageViewDelete, class2.getId());
    }

    /**
     * 获取Class2数量
     *
     * @return 数量
     */
    @Override
    public int getItemCount() {
        return listClass2s.size();
    }


    // 修改相关
    /**
     * 设置修改按钮的Listener
     */
    private void setListenerEdit(ImageView imageView, final int class2Id, final int class1Id) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                createDialogEdit(class2Id, class1Id).show();
            }
        });
    }

    /**
     * 构建修改class2的Dialog
     *
     * @return dialog
     */
    private Dialog createDialogEdit(final int class2Id, final int class1Id) {
        updateDatabase();

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, 3);
        View viewRemarkDialog = View.inflate(context, R.layout.dialog_class2edit, null);

        final EditText editText = viewRemarkDialog.findViewById(R.id.dclass2edit_edittext);
        final Spinner spinnerClass1 = viewRemarkDialog.findViewById(R.id.dclass2edit_spinner_class1);
        final Spinner spinnerColor = viewRemarkDialog.findViewById(R.id.dclass2edit_spinner_color);

        ArrayList<Integer> listColors = getListColorIds();
        SpinnerAdapterColor spinnerAdapterColor = new SpinnerAdapterColor(context, listColors);
        spinnerColor.setAdapter(spinnerAdapterColor);

        ArrayList<Class1> listClass1s = class1Db.categoryList();
        final SpinnerAdapterClass1 spinnerAdapterClass1 = new SpinnerAdapterClass1(context, listClass1s);
        spinnerClass1.setAdapter(spinnerAdapterClass1);

        int class1Position = 0;
        for (int i=0; i<listClass1s.size(); i++) {
            if (listClass1s.get(i).getId() == class1Id) {
                class1Position = i;
            }
        }

        builder.setTitle("修改一级分类");
        builder.setView(viewRemarkDialog);


        // TODO: 6/16/2018
        ArrayList<Class2> listClass2 = class2Db.subcategoryList(class1Id);
        Class2 class2 = null;
        for (Class2 c: listClass2) {
            if (c.getId() == class2Id) {
                class2 = c;
            }
        }

        editText.setText(class2.getName());
        spinnerColor.setSelection(class1Position);
        spinnerColor.setSelection(class2.getColor()-1);


        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editText.getText().toString();
                Class1 class1 = (Class1)spinnerClass1.getSelectedItem();
                int class1Id = class1.getId();
                int colorId = (int)spinnerColor.getSelectedItem();

                if (name.isEmpty()) {
                    Toast.makeText(context, "输入不能为空", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    return;
                }

                Class2 class2 = getClass2(class2Id, name, colorId, class1Id);
                String message = updateClass2(class2);

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
     * 获取一个Class2实例
     *
     * @param id id
     * @param name 名称
     * @param colorId 颜色id
     * @param fatherId 所属类id
     * @return 实例
     */
    private Class2 getClass2(int id, String name, int colorId, int fatherId) {
        return new Class2(id, name, colorId, fatherId);
    }


    // 删除相关
    /**
     * 设置删除按钮的Listener
     *
     * @param imageView 删除按钮imageView
     * @param class2Id 待删除的class2Id
     */
    private void setListenerDelete(ImageView imageView, final int class2Id) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                updateDatabase();
                Class2 class2 = new Class2(class2Id);
                String message = class2Db.deleteSubcategory(class2);

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
        class2Db = new SubcategoryDb(sqLiteDatabase);
    }

    /**
     * 向数据库中更新数据
     */
    private String updateClass2(Class2 class2Update) {
        return class2Db.updateSubcategory(class2Update);
    }

}
