package com.eme.idlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.eme.aidldemo.Book;
import com.eme.aidldemo.BookManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dijiaoliang on 17/7/4.
 */
public class AdilService extends Service {

    private static final String TAG="AIDL";

    private List<Book> books;

    private final BookManager.Stub bookManager = new BookManager.Stub() {
        @Override
        public int getBookCount() throws RemoteException {
            return books == null ? 0 : books.size();
        }

        @Override
        public Book addBookIn(Book book) throws RemoteException {
            books.add(book);
            Log.e(TAG,"AdilService的方法addBookIn接收的信息为:"+book.toString());
            book.setName("addBookIn----11");
            book.setPrice(11);
            return book;
        }

        @Override
        public Book addBookOut(Book book) throws RemoteException {
            books.add(book);
            Log.e(TAG,"AdilService的方法addBookOut接收的信息为:"+book.toString());
            book.setName("addBookIn----22");
            book.setPrice(22);
            return book;
        }

        @Override
        public Book addBookInOut(Book book) throws RemoteException {
            books.add(book);
            Log.e(TAG,"AdilService的方法addBookInOut接收的信息为:"+book.toString());
            book.setName("addBookIn----33");
            book.setPrice(33);
            return book;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        books = new ArrayList<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return bookManager.asBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
