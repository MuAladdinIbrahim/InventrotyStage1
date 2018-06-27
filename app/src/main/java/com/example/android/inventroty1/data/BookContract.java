package com.example.android.inventroty1.data;

import android.provider.BaseColumns;

public final class BookContract {

    // give it an empty constructor to prevent anyone from instantiating contract class
    private BookContract() {}

    //Inner class to define constant values for each book
    public static final class BookEntry implements BaseColumns {

        public final static String TABLE_NAME = "books";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_Name = "name";
        public final static String COLUMN_Price = "price";
        public final static String COLUMN_Quantity = "quantity";
        public final static String COLUMN_Supplier_Name = "supplier_name";
        public final static String COLUMN_Supplier_Phone_Number = "phone";

        /**
         * Possible values for the suppliers.
         */
        public static final int SUPPLIER_UNKNOWN = 0;
        public static final int SUPPLIER_NAHDA = 1;
        public static final int SUPPLIER_AHRAM = 2;
        public static final int SUPPLIER_SHROUQ = 3;
        public static final int SUPPLIER_LEBNAN = 4;
        public static final int SUPPLIER_SALAM = 5;
    }
}