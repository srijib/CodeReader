package com.wkswind.money.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Administrator on 2015/3/31.
 */
@ContentProvider(authority = CodeProvider.AUTHORITY,
        database =  CodeDatabase.class,
        packageName = "com.wkswind.codereader.provider"
    )
public class CodeProvider {
    private CodeProvider() {
    }

    public static final String AUTHORITY = "com.wkswind.money.CodeProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String TRANSACTION_TYPES = "transaction_types";
        String TRANSACTIONS = "transactions";
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
        defaultSort = TransactionType.name + " DESC")
        public static final Uri CONTENT_URI = buildUri(Path.TRANSACTION_TYPES);

        @InexactContentUri(
                path = Path.TRANSACTION_TYPES +"/#",
                name = "TRANSACTION_TYPES_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = TransactionType.ID,
                pathSegment = 1 )
        public static Uri withId(long id){return buildUri(Path.TRANSACTION_TYPES,String.valueOf(id));}
    }

    @TableEndpoint(table = CodeDatabase.Tables.TRANSACTIONS)
    public static class Transactions {
        @ContentUri(path = Path.TRANSACTIONS,
                type = "vnd.android.cursor.dir/list",
                defaultSort = Transaction.timeInMS + " DESC")
        public static final Uri CONTENT_URI = buildUri(Path.TRANSACTIONS);

        @InexactContentUri(
                path = Path.TRANSACTIONS +"/#",
                name = "TRANSACTIONS_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = Transaction.ID,
                pathSegment = 1 )
        public static Uri withId(long id){return buildUri(Path.TRANSACTIONS,String.valueOf(id));}
    }
}
