package com.yuemai.game34999.presentaion.download.db.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/5  11:15
 * @email : 923080261@qq.com
 * @description :
 */
public interface DbOptions<T> {

    /**
     * 关闭数据库
     *
     * @param database 数据库
     * @param cursor 游标
     */
    void closeDatabase(SQLiteDatabase database, Cursor cursor);


    /*----------------------------------插入-----------------------------------------*/

    /**
     * 插入一条数据
     *
     * @param t
     * @return
     */
    boolean insert(T t);

    long insert(SQLiteDatabase database, T t);

    /**
     * 插入一个集合
     *
     * @param ts
     * @return
     */
    boolean insert(List<T> ts);

    boolean insert(SQLiteDatabase database, List<T> ts);

    /*----------------------------------删除-----------------------------------------*/

    /**
     * 删除所有
     *
     * @return true 成功 false 失败
     */
    boolean deleteAll();

    /**
     * 根据条件删除
     *
     * @param whereClause
     * @param whereArgs
     * @return true 成功 false 失败
     */
    boolean delete(String whereClause, String[] whereArgs);

    long delete(SQLiteDatabase database, String whereClause, String[] whereArgs);

    /**
     * 删除一个集合
     *
     * @param where
     * @return true 成功 false 失败
     */
    boolean deleteList(List<Pair<String, String[]>> where);

    /*----------------------------------替换-----------------------------------------*/

    /**
     * 替换对象
     *
     * @param t
     * @return
     */
    boolean replace(T t);

    long replace(SQLiteDatabase database, T t);

    boolean replace(ContentValues contentValues);

    /**
     * @param database
     * @param contentValues
     * @return id
     */
    long replace(SQLiteDatabase database, ContentValues contentValues);

    /**
     * 替换一个集合
     *
     * @param ts
     * @return true 成功 false 失败
     */
    boolean replace(List<T> ts);

    boolean replace(SQLiteDatabase database, List<T> ts);

    /*----------------------------------修改-----------------------------------------*/

    /**
     * 根据条件修改
     *
     * @param t
     * @param whereClause
     * @param whereArgs
     * @return true 成功 false 失败
     */
    boolean update(T t, String whereClause, String[] whereArgs);

    long update(SQLiteDatabase database, T t, String whereClause, String[] whereArgs);

    boolean update(ContentValues contentValues, String whereClause, String[] whereArgs);

    long update(SQLiteDatabase database, ContentValues contentValues, String whereClause, String[] whereArgs);

    /*----------------------------------查询-----------------------------------------*/

    /**
     * 查询所有
     *
     * @param database
     * @return 结果集合
     */
    List<T> queryAll(SQLiteDatabase database);

    /**
     * 根据条件查询符合的所有
     *
     * @param database
     * @param selection     查询语句
     * @param selectionArgs 条件参数
     * @return
     */
    List<T> query(SQLiteDatabase database, String selection, String[] selectionArgs);

    /**
     * 查询单个
     *
     * @param database
     * @param selection
     * @param selectionArgs
     * @return
     */
    T queryOne(SQLiteDatabase database, String selection, String[] selectionArgs);

    List<T> query(SQLiteDatabase database, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit);

    List<T> queryAll();

    List<T> query(String selection, String[] selectionArgs);

    T queryOne(String selection, String[] selectionArgs);

    List<T> query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit);


    /**
     * 获取对应的表名
     */
    String getTableName();

    void unInit();

    /**
     * 将Cursor解析成对应的JavaBean
     */
    T parseCursorToBean(Cursor cursor);

    /**
     * 将对象转为可以识别的键值对
     */
    ContentValues getContentValues(T t);

}