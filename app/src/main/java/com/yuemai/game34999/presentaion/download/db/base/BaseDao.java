package com.yuemai.game34999.presentaion.download.db.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/5  11:44
 * @email : 923080261@qq.com
 * @description :数据库操作基类
 */
public abstract class BaseDao<T> implements DbOptions<T> {
    protected static String TAG;
    protected Lock lock = new ReentrantLock();
    protected SQLiteOpenHelper helper;
    protected SQLiteDatabase database;

    public BaseDao(SQLiteOpenHelper helper) {
        TAG = getClass().getSimpleName();
        lock = DBHelper.lock;
        this.helper = helper;
        this.database = openWriter();
    }

    public SQLiteDatabase openWriter() {
        return helper.getWritableDatabase();
    }

    @Override
    public void closeDatabase(SQLiteDatabase database, Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    @Override
    public boolean insert(T t) {
        if (t == null) {
            return false;
        }
        long start = System.currentTimeMillis();
        lock.lock();
        try {
            database.beginTransaction();
            database.insert(getTableName(), null, getContentValues(t));
            database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            lock.unlock();
            Log.v(TAG, System.currentTimeMillis() - start + " insertT");
        }
        return false;
    }

    @Override
    public long insert(SQLiteDatabase database, T t) {
        return database.insert(getTableName(), null, getContentValues(t));
    }

    @Override
    public boolean insert(List<T> ts) {
        if (ts == null) {
            return false;
        }
        long start = System.currentTimeMillis();
        lock.lock();
        try {
            database.beginTransaction();
            for (T t : ts) {
                database.insert(getTableName(), null, getContentValues(t));
            }
            database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            lock.unlock();
            Log.v(TAG, System.currentTimeMillis() - start + " insertList");
        }
        return false;
    }

    @Override
    public boolean insert(SQLiteDatabase database, List<T> ts) {
        try {
            for (T t : ts) {
                database.insert(getTableName(), null, getContentValues(t));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        return delete(null, null);
    }

    @Override
    public boolean delete(String whereClause, String[] whereArgs) {
        long start = System.currentTimeMillis();
        lock.lock();
        try {
            database.beginTransaction();
            database.delete(getTableName(), whereClause, whereArgs);
            database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            lock.unlock();
            Log.v(TAG, System.currentTimeMillis() - start + " delete");
        }
        return false;
    }

    @Override
    public long delete(SQLiteDatabase database, String whereClause, String[] whereArgs) {
        return database.delete(getTableName(), whereClause, whereArgs);
    }

    @Override
    public boolean deleteList(List<Pair<String, String[]>> where) {
        long start = System.currentTimeMillis();
        lock.lock();
        try {
            database.beginTransaction();
            for (Pair<String, String[]> pair : where) {
                database.delete(getTableName(), pair.first, pair.second);
            }
            database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            lock.unlock();
            Log.v(TAG, System.currentTimeMillis() - start + " deleteList");
        }
        return false;
    }

    /**
     * replace 语句有如下行为特点
     * 1. replace语句会删除原有的一条记录， 并且插入一条新的记录来替换原记录。
     * 2. 一般用replace语句替换一条记录的所有列， 如果在replace语句中没有指定某列， 在replace之后这列的值被置空 。
     * 3. replace语句根据主键的值确定被替换的是哪一条记录
     * 4. 如果执行replace语句时， 不存在要替换的记录， 那么就会插入一条新的记录。
     * 5. replace语句不能根据where子句来定位要被替换的记录
     * 6. 如果新插入的或替换的记录中， 有字段和表中的其他记录冲突， 那么会删除那条其他记录。
     */
    @Override
    public boolean replace(T t) {
        if (t == null) {
            return false;
        }
        long start = System.currentTimeMillis();
        lock.lock();
        try {
            database.beginTransaction();
            database.replace(getTableName(), null, getContentValues(t));
            database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            lock.unlock();
            Log.v(TAG, System.currentTimeMillis() - start + " replaceT");
        }
        return false;
    }

    @Override
    public long replace(SQLiteDatabase database, T t) {
        return database.replace(getTableName(), null, getContentValues(t));
    }

    @Override
    public boolean replace(ContentValues contentValues) {
        long start = System.currentTimeMillis();
        lock.lock();
        try {
            database.beginTransaction();
            database.replace(getTableName(), null, contentValues);
            database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            lock.unlock();
            Log.v(TAG, System.currentTimeMillis() - start + " replaceContentValues");
        }
        return false;
    }

    @Override
    public long replace(SQLiteDatabase database, ContentValues contentValues) {
        return database.replace(getTableName(), null, contentValues);
    }

    @Override
    public boolean replace(List<T> ts) {
        if (ts == null) {
            return false;
        }
        long start = System.currentTimeMillis();
        lock.lock();
        try {
            database.beginTransaction();
            for (T t : ts) {
                database.replace(getTableName(), null, getContentValues(t));
            }
            database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            lock.unlock();
            Log.v(TAG, System.currentTimeMillis() - start + " replaceList");
        }
        return false;
    }

    @Override
    public boolean replace(SQLiteDatabase database, List<T> ts) {
        try {
            for (T t : ts) {
                database.replace(getTableName(), null, getContentValues(t));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(T t, String whereClause, String[] whereArgs) {
        if (t == null) {
            return false;
        }
        long start = System.currentTimeMillis();
        lock.lock();
        try {
            database.beginTransaction();
            database.update(getTableName(), getContentValues(t), whereClause, whereArgs);
            database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            lock.unlock();
            Log.v(TAG, System.currentTimeMillis() - start + " updateT");
        }
        return false;
    }

    @Override
    public long update(SQLiteDatabase database, T t, String whereClause, String[] whereArgs) {
        return database.update(getTableName(), getContentValues(t), whereClause, whereArgs);
    }

    @Override
    public boolean update(ContentValues contentValues, String whereClause, String[] whereArgs) {
        long start = System.currentTimeMillis();
        lock.lock();
        try {
            database.beginTransaction();
            database.update(getTableName(), contentValues, whereClause, whereArgs);
            database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            lock.unlock();
            Log.v(TAG, System.currentTimeMillis() - start + " updateContentValues");
        }
        return false;
    }

    @Override
    public long update(SQLiteDatabase database, ContentValues contentValues, String whereClause, String[] whereArgs) {
        return database.update(getTableName(), contentValues, whereClause, whereArgs);
    }

    @Override
    public List<T> queryAll(SQLiteDatabase database) {
        return query(database, null, null);
    }

    @Override
    public List<T> query(SQLiteDatabase database, String selection, String[] selectionArgs) {
        return query(database, null, selection, selectionArgs, null, null, null, null);
    }

    @Override
    public T queryOne(SQLiteDatabase database, String selection, String[] selectionArgs) {
        List<T> query = query(database, null, selection, selectionArgs, null, null, null, "1");
        if (query.size() > 0) {
            return query.get(0);
        }
        return null;
    }

    @Override
    public List<T> query(SQLiteDatabase database, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        List<T> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.query(getTableName(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            while (!cursor.isClosed() && cursor.moveToNext()) {
                list.add(parseCursorToBean(cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabase(null, cursor);
        }
        return list;
    }

    @Override
    public List<T> queryAll() {
        return query(null, null);
    }

    @Override
    public List<T> query(String selection, String[] selectionArgs) {
        return query(null, selection, selectionArgs, null, null, null, null);
    }

    @Override
    public T queryOne(String selection, String[] selectionArgs) {
        long start = System.currentTimeMillis();
        List<T> query = query(null, selection, selectionArgs, null, null, null, "1");
        Log.v(TAG, System.currentTimeMillis() - start + " queryOne");
        return query.size() > 0 ? query.get(0) : null;
    }

    @Override
    public List<T> query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        long start = System.currentTimeMillis();
        lock.lock();
        List<T> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            database.beginTransaction();
            cursor = database.query(getTableName(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            while (!cursor.isClosed() && cursor.moveToNext()) {
                list.add(parseCursorToBean(cursor));
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabase(null, cursor);
            database.endTransaction();
            lock.unlock();
            Log.v(TAG, System.currentTimeMillis() - start + " query");
        }
        return list;
    }

    public interface Action {
        void call(SQLiteDatabase database);
    }

    /**
     * 用于给外界提供事物开启的模板
     */
    public void startTransaction(Action action) {
        lock.lock();
        try {
            database.beginTransaction();
            action.call(database);
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            lock.unlock();
        }
    }
}
