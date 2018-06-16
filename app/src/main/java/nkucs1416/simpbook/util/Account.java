package nkucs1416.simpbook.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Account {
    private int id;
    private String name;
    private float money;
    private int color;
    private int type; // 1->资产账户 2->负债账户
    private int status;


    /**
     * 构建一个Account实例
     *
     * @param tId id
     * @param tName 名称
     * @param tMoney 金额
     * @param tColor 标识颜色
     * @param tType 标识颜色
     * @param tStatus 账户状态
     */
    public Account(int tId, String tName, float tMoney, int tColor, int tType, int tStatus) {
        id = tId;
        name = tName;
        money = tMoney;
        color = tColor;
        type = tType;
        status = tStatus;
    }

    /**
     * 构建一个Account实例
     *
     * @param tId id
     * @param tName 名称
     * @param tMoney 金额
     * @param tColor 标识颜色
     * @param tType 标识颜色
     */
    public Account(int tId, String tName, float tMoney, int tColor, int tType) {
        id = tId;
        name = tName;
        money = tMoney;
        color = tColor;
        type = tType;
    }

    /**
     * 构建一个Account实例
     *
     * @param tName 名称
     * @param tMoney 金额
     * @param tColor 标识颜色
     * @param tType 标识颜色
     */
    public Account(String tName, float tMoney, int tColor, int tType) {
        id = -1;
        name = tName;
        money = tMoney;
        color = tColor;
        type = tType;
    }


    /**
     * 获取Id
     *
     * @return Id
     */
    public int getId() {
        return id;
    }

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取金额
     *
     * @return 金额
     */
    public float getMoney() {
        return money;
    }

    /**
     * 获取标识颜色
     *
     * @return 标识颜色
     */
    public int getColor() {
        return color;
    }

    /**
     * 获取账户类型
     *
     * @return 账户类型
     */
    public int getType() {
        return type;
    }

    /**
     * 获取账户状态
     *
     * @return 账户状态
     */
    public int getStatus() {
        return status;
    }


    // static函数
    /**
     * 为账户列表进行排序
     *
     * @param listAccounts 待排序的账户列表
     */
    public static void sortListAccounts(ArrayList<Account> listAccounts) {
        Collections.sort(listAccounts, new Comparator<Account>() {
            @Override
            public int compare(Account a1, Account a2) {
                if (a1.getType() < a2.getType()) {
                    return -1;
                }
                else {
                    if (a1.getColor() < a2.getColor()) {
                        return -1;
                    }
                    else {
                        return 1;
                    }
                }
            }

        });
    }

    /**
     * 获取特定类型账户列表的金额总和
     *
     * @param accountType 账户类型
     * @param listAccounts 账户列表
     * @return 金额总和
     */
    public static float getSumMoney(int accountType, ArrayList<Account> listAccounts) {
        float money = 0.0f;
        for(Account account: listAccounts) {
            if (account.getType() == accountType) {
                money += account.getMoney();
            }
        }
        return money;
    }

    /**
     * 获取账户列表的金额总和
     *
     * @param listAccounts 账户列表
     * @return 金额总和
     */
    public static float getSumMoney(ArrayList<Account> listAccounts) {
        float money = 0.0f;
        for(Account account: listAccounts) {
            money += account.getMoney();
        }
        return money;
    }

}
