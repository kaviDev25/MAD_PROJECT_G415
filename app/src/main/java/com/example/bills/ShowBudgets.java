package com.example.bills;

import android.provider.BaseColumns;

//contract
public class ShowBudgets {
    //IT19097084 Ayodya Hettiarachchi

    public static final class BudgetEntry implements BaseColumns{
        public static final String BUDGETS_LIST_TABLE_NAME = "budget list";
        public static final String AVAILABLE_BUDGETS_COLUMN = "names";
        public static final String BUDGETS_TARGET_SPENDING_COLUMN = "target expenses";
        public static final String BUDGET_NOTES = "budget notes";
        public static final String BUDGETS_TOTAL_SPENT = "total spent";
        public static final String BUDGETS_COLUMN_TIMESTAMP = "timestamp";
        public static final String BUDGETS_REMAINDER = "remainder";
        public static final String BUDGETS_ADD_COLUMN = "add";
        public static final String BUDGETS_REMOVE_COLUMN = "remove";
    }
}
