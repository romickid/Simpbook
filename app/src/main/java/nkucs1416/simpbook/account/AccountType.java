package nkucs1416.simpbook.account;

import java.util.ArrayList;

import nkucs1416.simpbook.R;

public class AccountType {
    private static int maxAccountType = 2;

    public static int getAccountTypeIcon(int accountTypeId) {
        switch (accountTypeId) {
            case 1:
                return R.drawable.ic_lens_recordred_36dp;
            case 2:
                return R.drawable.ic_lens_recordgreen_36dp;
        }
        return 0;
    }

    public static String getAccountTypeName(int accountTypeId) {
        switch (accountTypeId) {
            case 1:
                return "资产账户";
            case 2:
                return "负债账户";
        }
        return null;
    }

    public static ArrayList<Integer> getListAccountTypes() {
        ArrayList<Integer> listAccountType = new ArrayList<Integer>();
        for(int i = 1; i <= maxAccountType; i++) {
            listAccountType.add(i);
        }

        return listAccountType;
    }
}
