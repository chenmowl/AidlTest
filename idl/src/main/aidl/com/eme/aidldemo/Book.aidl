// Book.aidl
package com.eme.aidldemo;

// Declare any non-default types here with import statements

//第一类AIDL文件的例子
//这个文件的作用是引入了一个序列化对象 Book 供其他的AIDL文件使用
//注意：Book.aidl与Book.java的包名应当是一样的
//注意parcelable是小写
parcelable Book;
