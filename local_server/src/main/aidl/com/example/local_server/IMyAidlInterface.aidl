// IMyAidlInterface.aidl
package com.example.local_server;

// Declare any non-default types here with import statements
import com.example.local_server.IAidlCallback;
import com.example.local_server.IUserObj;

/**
* AIDL中的定向 tag 表示了在跨进程通信中数据的流向，其中
  in 表示数据只能由客户端流向服务端
  out 表示数据只能由服务端流向客户端，而
  inout 则表示数据可在服务端与客户端之间双向流通。
  其中，数据流向是针对在客户端中的那个传入方法的对象而言的。
  in 为定向 tag 的话表现为服务端将会接收到一个那个对象的完整数据，
  out 的话表现为服务端将会接收到那个对象的的空对象，
  但是在服务端对接收到的空对象有任何修改之后客户端将会同步变动；
  inout 为定向 tag 的情况下，服务端将会接收到客户端传来对象的完整信息，
  并且客户端将会同步服务端对该对象的任何变动。

  in、out、inout表示跨进程通信中数据的流向（基本数据类型默认是in，非基本数据类型可以使用其它数据流向out、inout）。

   in 表示数据只能由客户端流向服务端。（表现为服务端修改此参数，不会影响客户端的对象）

   out 表示数据只能由服务端流向客户端。（表现为服务端收到的参数是空对象，并且服务端修改对象后客户端会同步变动）

   inout 则表示数据可在服务端与客户端之间双向流通。（表现为服务端能接收到客户端传来的完整对象，并且服务端修改对象后客户端会同步变动）

  oneway 关键字用于修改远程调用的行为，被oneway修饰了的方法不可以有返回值，也不可以有带out或inout的参数。

  本地调用(同步调用)
  远程调用(异步调用，即客户端不会被阻塞)
  使用oneway时，远程调用不会阻塞；它只是发送事务数据并立即返回。接口的实现最终接收此调用时，是以正常远程调用形式将其作为来自 Binder 线程池的常规调用进行接收。

*/
interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

   /** oneway */ void setMsg(String message);

    String getServiceMsg();

    void setUserObj(IUserObj obj);

    IUserObj getUserObj();

    void setCallback(IAidlCallback callback);

    void removeCallback(IAidlCallback callback);
}