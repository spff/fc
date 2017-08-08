package com.example.spff.fc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;




public class ItemDataAccessObject {

    public static final String TABLE_NAME = "item";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";
    // 其它表格欄位名稱
    public static final String THUMBNAILURI_COLUMN = "color";
    public static final String CROPURI_COLUMN = "title";


    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    THUMBNAILURI_COLUMN + " INTEGER NOT NULL, " +
                    CROPURI_COLUMN + " TEXT NOT NULL, ";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public ItemDataAccessObject(Context context) {
        db = DBHelper.getDatabase(context);
    }


    // 新增參數指定的物件
    public Item insert(Item item){
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(THUMBNAILURI_COLUMN, item.thumbnailURI.toString());
        cv.put(CROPURI_COLUMN, item.cropURI.toString());

        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        item.id = id;
        // 回傳結果
        return item;
    }

    // 修改參數指定的物件
    public boolean update(Item item) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(THUMBNAILURI_COLUMN, item.thumbnailURI.toString());
        cv.put(CROPURI_COLUMN, item.cropURI.toString());

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + item.id;

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    // 讀取所有記事資料
    public List<Item > getAll() {
        List<Item > result = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // 把Cursor目前的資料包裝為物件
public Item getRecord(Cursor cursor) {
    // 準備回傳結果用的物件
    Item result = new Item();

    result.id = cursor.getLong(0);
    result.thumbnailURI = Uri.parse(cursor.getString(1));
    result.cropURI = Uri.parse(cursor.getString(2));

    // 回傳結果
    return result;
}

}
