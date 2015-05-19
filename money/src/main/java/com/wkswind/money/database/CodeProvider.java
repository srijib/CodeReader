package com.wkswind.money.database;

import android.net.Uri;

import com.wkswind.money.database.schema.AccountColumns;
import com.wkswind.money.database.schema.TransactionColumns;
import com.wkswind.money.database.schema.TransactionTypeColumns;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Administrator on 2015/3/31.
 */
@ContentProvider(authority = CodeProvider.AUTHORITY,
        database =  CodeDatabase.class,
        packageName = "com.wkswind.money.provider"
    )
public class CodeProvider {
    private CodeProvider() {
    }

    public static final String AUTHORITY = "com.wkswind.money.CodeProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String TRANSACTION_TYPES = "transaction_types";
        String TRANSACTIONS = "transactions";
        String ACCOUNTS = "accounts";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = CodeDatabase.Tables.TRANSACTION_TYPES)
    public static class TransactionTypes {
        @ContentUri(path = Path.TRANSACTION_TYPES,
        type = "vnd.android.cursor.dir/list",
        defaultSort = TransactionTypeColumns.name + " DESC")
        public static final Uri CONTENT_URI = buildUri(Path.TRANSACTION_TYPES);

        @InexactContentUri(
                path = Path.TRANSACTION_TYPES +"/#",
                name = "TRANSACTION_TYPES_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = TransactionTypeColumns.ID,
                pathSegment = 1 )
        public static Uri withId(long id){return buildUri(Path.TRANSACTION_TYPES,String.valueOf(id));}
    }

    @TableEndpoint(table = CodeDatabase.Tables.ACCOUNS)
    public static class Accounts {
        @ContentUri(path = Path.ACCOUNTS,
                type = "vnd.android.cursor.dir/list",
                defaultSort = AccountColumns.name + " DESC")
        public static final Uri CONTENT_URI = buildUri(Path.ACCOUNTS);

        @InexactContentUri(
                path = Path.ACCOUNTS +"/#",
                name = "ACCOUNTS_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = AccountColumns.ID,
                pathSegment = 1 )
        public static Uri withId(long id){return buildUri(Path.ACCOUNTS,String.valueOf(id));}
    }

    @TableEndpoint(table = CodeDatabase.Tables.TRANSACTIONS)
    public static class Transactions {
        @ContentUri(path = Path.TRANSACTIONS,
                type = "vnd.android.cursor.dir/list",
                defaultSort = TransactionColumns.timeInMS + " DESC")
        public static final Uri CONTENT_URI = buildUri(Path.TRANSACTIONS);

        @InexactContentUri(
                path = Path.TRANSACTIONS +"/#",
                name = "TRANSACTIONS_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = TransactionColumns.ID,
                pathSegment = 1 )
        public static Uri withId(long id){return buildUri(Path.TRANSACTIONS,String.valueOf(id));}
    }
}
