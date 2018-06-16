package nkucs1416.simpbook.account;

import java.util.ArrayList;

import nkucs1416.simpbook.R;

class AccountType {
    /**
     * id形式
     * 1->资产账户
     * 2->负债账户
     */
    private static int maxAccountType = 2;


    /**
     * 获取账户类型的Icon实例
     *
     * @param accountTypeId 账户类型id
     * @return Icon实例(id)
     */
    static int getAccountTypeIcon(int accountTypeId) {
        switch (accountTypeId) {
            case 1:
                return R.drawable.ic_lens_recordred_36dp;
            case 2:
                return R.drawable.ic_lens_recordgreen_36dp;
        }
        return 0;
    }

    /**
     * 获取账户类型名称
     *
     * @param accountTypeId 账户类型id
     * @return 账户类型名称
     */
    static String getAccountTypeName(int accountTypeId) {
        switch (accountTypeId) {
            case 1:
                return "资产账户";
            case 2:
                return "负债账户";
        }
        return null;
    }

    /**
     * 获取账户类型id列表
     *
     * @return id列表
     */
    static ArrayList<Integer> getListAccountTypes() {
        ArrayList<Integer> listAccountType = new ArrayList<Integer>();
        for(int i = 1; i <= maxAccountType; i++) {
            listAccountType.add(i);
        }
        return listAccountType;
    }

}
