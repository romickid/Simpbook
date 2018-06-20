package nkucs1416.simpbook.setting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.database.AccountDb;
import nkucs1416.simpbook.database.CategoryDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.database.RecordDb;
import nkucs1416.simpbook.database.SubcategoryDb;
import nkucs1416.simpbook.database.TemplateDb;
import nkucs1416.simpbook.database.UserDb;
import nkucs1416.simpbook.network.SynchAllData;
import nkucs1416.simpbook.util.User;
import nkucs1416.simpbook.util.UserListener;

import static nkucs1416.simpbook.util.Other.displayToast;

public class ActivityUser extends AppCompatActivity implements UserListener {
    private Toolbar toolbar;

    private ImageView imageViewSignIn;
    private ImageView imageViewLogin;
    private ImageView imageViewUpload;
    private ImageView imageViewDownload;

    private SQLiteDatabase sqLiteDatabase;
    private AccountDb accountDb;
    private CategoryDb categoryDb;
    private RecordDb recordDb;
    private SubcategoryDb subcategoryDb;
    private TemplateDb templateDb;
    private UserDb userDb;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initFindById();
        initToolbar();
        initImageView();
        initDatabase();
    }


    // 初始化相关
    /**
     * 初始化Id
     */
    private void initFindById() {
        toolbar = findViewById(R.id.user_toolbar);
        imageViewSignIn = findViewById(R.id.user_imageview_signin);
        imageViewLogin = findViewById(R.id.user_imageview_login);
        imageViewUpload = findViewById(R.id.user_imageview_upload);
        imageViewDownload = findViewById(R.id.user_imageview_download);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化按钮
     */
    private void initImageView() {
        initImageViewSignIn();
        initImageViewLogin();
        initImageViewUpload();
        initImageViewDownload();
    }

    /**
     * 初始化注册账户按钮
     */
    private void initImageViewSignIn() {
        imageViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                createDialogSignIn().show();
            }
        });
    }

    /**
     * 初始化登录账户按钮
     */
    private void initImageViewLogin() {
        imageViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                createDialogLogin().show();
            }
        });
    }

    /**
     * 初始化上传数据按钮
     */
    private void initImageViewUpload() {
        final Context context = this;
        imageViewUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                accountDb.synchDataBackend(context);
                categoryDb.synchDataBackend(context);
                recordDb.synchDataBackend(context);
                subcategoryDb.synchDataBackend(context);
                templateDb.synchDataBackend(context);
            }
        });
    }

    /**
     * 初始化下载数据按钮
     */
    private void initImageViewDownload() {
        final Context context = this;
        imageViewDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String token = userDb.getUserToken();
                new SynchAllData(token, context, sqLiteDatabase);
            }
        });
    }

    /**
     * 初始化数据库
     */
    private void initDatabase() {
        CustomSQLiteOpenHelper customSQLiteOpenHelper = new CustomSQLiteOpenHelper(this);
        sqLiteDatabase = customSQLiteOpenHelper.getWritableDatabase();
        accountDb = new AccountDb(sqLiteDatabase);
        categoryDb = new CategoryDb(sqLiteDatabase);
        recordDb = new RecordDb(sqLiteDatabase);
        subcategoryDb = new SubcategoryDb(sqLiteDatabase);
        templateDb = new TemplateDb(sqLiteDatabase);
        userDb = new UserDb(this);
    }


    // 注册账户相关
    /**
     * 构建"注册账户"的Dialog
     *
     * @return dialog实例
     */
    private Dialog createDialogSignIn() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, 3);
        View viewRemarkDialog = View.inflate(this, R.layout.dialog_user_signin, null);

        final EditText editTextEmail = viewRemarkDialog.findViewById(R.id.dusersignin_edittext_email);
        final EditText editTextName = viewRemarkDialog.findViewById(R.id.dusersignin_edittext_name);
        final EditText editTextPassword = viewRemarkDialog.findViewById(R.id.dusersignin_edittext_password);

        builder.setTitle("注册账户");
        builder.setView(viewRemarkDialog);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = editTextEmail.getText().toString();
                String name = editTextName.getText().toString();
                String password = editTextPassword.getText().toString();
                userDb.createUser(new User(name, email, password));
                displayToast("用户注册中, 无法进行登录操作", getApplicationContext(), 0);
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


    // 登录账户相关
    /**
     * 构建"登录账户"的Dialog
     *
     * @return dialog实例
     */
    private Dialog createDialogLogin() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, 3);
        View viewRemarkDialog = View.inflate(this, R.layout.dialog_user_login, null);

        final EditText editTextEmail = viewRemarkDialog.findViewById(R.id.duserlogin_edittext_email);
        final EditText editTextPassword = viewRemarkDialog.findViewById(R.id.duserlogin_edittext_password);

        builder.setTitle("登录账户");
        builder.setView(viewRemarkDialog);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                userDb.loginUser(new User(email, password));
                displayToast("用户登录中, 无法进行登录操作", getApplicationContext(), 0);
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


    // User-DB数据传递相关
    @Override
    public void OnUserSignInSuccess() {
        displayToast("用户注册成功!", this, 0);
    }

    @Override
    public void OnUserSignInFail() {
        displayToast("用户注册失败!", this, 0);
    }

    @Override
    public void OnUserDownloadSuccess() {
        displayToast("数据下载成功!", this, 0);
    }

    @Override
    public void OnUserDownloadFail() {
        displayToast("数据下载失败!", this, 0);
    }

}
