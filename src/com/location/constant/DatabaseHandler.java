package  com.location.constant;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DatabaseHandler  extends SQLiteOpenHelper {
    private static final String LOGCAT = null;
 
    public DatabaseHandler(Context context) {
        super(context, "androidsqlite.db", null, 1);
        Log.d(LOGCAT,"Created");
    }
     
    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE Favorites ( FavoriteId INTEGER PRIMARY KEY, FavoriteName TEXT, FavoriteReferance TEXT)";
        database.execSQL(query);
        Log.d(LOGCAT,"Favorites Created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS Favorites";
        database.execSQL(query);
        onCreate(database);
    }
     
    public void insertFarvorite(HashMap<String, String> queryValues) {
       Log.i("FavoriteName",""+queryValues.get("FavoriteName"));
 		Log.i("FavoriteReferance",""+queryValues.get("FavoriteReferance"));
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FavoriteName", queryValues.get("FavoriteName"));
        values.put("FavoriteReferance", queryValues.get("FavoriteReferance"));
        database.insert("Favorites", null, values);
        database.close();
    }
    public int updateFavorite(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();   
        ContentValues values = new ContentValues();
        values.put("FavoriteName", queryValues.get("FavoriteName"));
        return database.update("Favorites", values, "FavoriteId" + " = ?", new String[] { queryValues.get("FavoriteId") });
    }
    public void deleteFavorite(String id) {
        Log.d(LOGCAT,"delete");
        SQLiteDatabase database = this.getWritableDatabase();   
        String deleteQuery = "DELETE FROM  Favorites where FavoriteId='"+ id +"'";
        Log.d("query",deleteQuery);    
        database.execSQL(deleteQuery);
    }
     
    public ArrayList<HashMap<String, String>> getAllFavorite() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM Favorites";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("FavoriteId", cursor.getString(0));
                map.put("FavoriteName", cursor.getString(1));
                map.put("FavoriteReferance", cursor.getString(2));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
      
        // return contact list
        return wordList;
    }
     
    public HashMap<String, String> getFavorite(String id) {
        HashMap<String, String> wordList = new HashMap<String, String>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM Favorites where FavoriteId='"+id+"'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                    //HashMap<String, String> map = new HashMap<String, String>();
                wordList.put("FavoriteName", cursor.getString(1));
                wordList.put("FavoriteReferance", cursor.getString(2));
                   //wordList.add(map);
            } while (cursor.moveToNext());
        }                  
    return wordList;
    }  
}


