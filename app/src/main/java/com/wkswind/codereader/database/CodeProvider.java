package com.wkswind.codereader.database;

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

    public static final String AUTHORITY = "com.wkswind.codereader.CodeProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String STARS = "stars";
        String HISTORYS = "historys";
        String DOC_TYPES = "doc_types";
        String QUERY_RESULTS = "query_results";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = CodeDatabase.Tables.STARS)
    public static class Stars {
        @ContentUri(path = Path.STARS,
        type = "vnd.android.cursor.dir/list",
        defaultSort = StarsColumn.fileName + " DESC")
        public static final Uri CONTENT_URI = buildUri(Path.STARS);

        @InexactContentUri(
                path = Path.STARS+"/#",
                name = "STARS_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = StarsColumn.ID,
                pathSegment = 1 )
        public static Uri withId(long id){return buildUri(Path.STARS,String.valueOf(id));}
    }

    @TableEndpoint(table = CodeDatabase.Tables.QUERY_RESULTS)
    public static class QueryResults {
        @ContentUri(path = Path.QUERY_RESULTS,
        type = "vnd.android.cursor.dir/list",
        defaultSort = QueryResultColumn.fileName)
        public static final Uri CONTENT_URI = buildUri(Path.QUERY_RESULTS);

        @InexactContentUri(path = Path.QUERY_RESULTS +"/#",
        name = "QUERY_RESULT_ID",
        type = "vnd.android.cursor.item/list",
        whereColumn = QueryResultColumn.ID,
        pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.QUERY_RESULTS,String.valueOf(id));
        }
    }

    @TableEndpoint(table = CodeDatabase.Tables.DOC_TYPES)
    public static class DocTypes {
        @ContentUri(path = Path.DOC_TYPES,
        type = "vnd.android.cursor.dir/list",
        defaultSort = DocTypeColumn.docType )
        public static final Uri CONTENT_URI = buildUri(Path.DOC_TYPES);

        @InexactContentUri(
                path = Path.DOC_TYPES +"/#",
                name = "DOC_TYPE_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = DocTypeColumn.ID,
                pathSegment =  1
        )
        public static Uri withId(long id) {
            return buildUri(Path.DOC_TYPES,String.valueOf(id));
        }
    }

    @TableEndpoint(table = CodeDatabase.Tables.HISTORYS)
    public static class Historys {
        @ContentUri(path = Path.HISTORYS,
                type = "vnd.android.cursor.dir/list",
                defaultSort = HistorysColumn.lastReadTime + " DESC")
        public static final Uri CONTENT_URI = buildUri(Path.HISTORYS);

        @InexactContentUri(
                path = Path.HISTORYS+"/#",
                name = "STARS_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = HistorysColumn.ID,
                pathSegment = 1 )
        public static Uri withId(long id){return buildUri(Path.HISTORYS,String.valueOf(id));}
    }
}
