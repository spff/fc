package com.example.spff.fc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ItemDataAccessObject {

    public static final String TABLE_NAME = "item";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";
    // 其它表格欄位名稱
    public static final String THUMBNAILURI_COLUMN = "thumbnailuri";
    public static final String CROPURI_COLUMN = "cropuri";
    public static final String TEXT_COLUMN = "text";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    THUMBNAILURI_COLUMN + " INTEGER NOT NULL, " +
                    CROPURI_COLUMN + " TEXT NOT NULL, " +
                    TEXT_COLUMN + " TEXT NOT NULL)";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public ItemDataAccessObject(Context context) {
        db = DBHelper.getDatabase(context);
    }


    // 新增參數指定的物件
    public void insert(Map<String, Object> item){
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(THUMBNAILURI_COLUMN, item.get("thumbnailURI").toString());
        cv.put(CROPURI_COLUMN, item.get("cropURI").toString());
        cv.put(TEXT_COLUMN, (String) item.get("text"));
        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        item.put("SQLid", Long.toString(id));
        // 回傳結果
        return;
    }

    // 修改參數指定的物件
    public boolean update(Map<String, Object> item) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(THUMBNAILURI_COLUMN, item.get("thumbnailURI").toString());
        cv.put(CROPURI_COLUMN, item.get("cropURI").toString());
        cv.put(TEXT_COLUMN, (String) item.get("text"));


        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + item.get("SQLid");

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(String id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    // 讀取所有記事資料
    public List<Map<String, Object> > getAll() {
        List<Map<String, Object> > result = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // 把Cursor目前的資料包裝為物件
public Map<String, Object> getRecord(Cursor cursor) {
    // 準備回傳結果用的物件
    Map<String, Object> result = new HashMap<>();

    result.put("SQLid", Long.toString(cursor.getLong(0)));
    result.put("thumbnailURI", Uri.parse(cursor.getString(1)));
    result.put("cropURI", Uri.parse(cursor.getString(2)));
    result.put("text", cursor.getString(3));

    // 回傳結果
    return result;
}

}
