package com.baitaoshuo.hookactivity;

import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class HookHelper {
    /**
     * 给系统的ActivityManager 设置属性mInstance
     * 这个属性是一个单例对象  所以我们在初始化方法中用自己的代理对象替换了这个属性  所以  后续在调用的时候  都会去调用我们的代理对象
     */
    public static final String TARGET_INTENT = "target_intent";

    public static void hookAMS() throws Exception {
        Object defaultSingleton = null;
        if (Build.VERSION.SDK_INT >= 26) {//1
            Class<?> activityManageClazz = Class.forName("android.app.ActivityManager");
            //获取activityManager中的IActivityManagerSingleton字段
            defaultSingleton = FieldUtil.getField(activityManageClazz, null, "IActivityManagerSingleton");
        } else {
            Class<?> activityManagerNativeClazz = Class.forName("android.app.ActivityManagerNative");
            //获取ActivityManagerNative中的gDefault字段
            defaultSingleton = FieldUtil.getField(activityManagerNativeClazz, null, "gDefault");
        }
        Class<?> singletonClazz = Class.forName("android.util.Singleton");
        Field mInstanceField = FieldUtil.getField(singletonClazz, "mInstance");//2
        //获取iActivityManager
        Object iActivityManager = mInstanceField.get(defaultSingleton);//3
        Class<?> iActivityManagerClazz = Class.forName("android.app.IActivityManager");
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{iActivityManagerClazz}, new IActivityManagerProxy(iActivityManager));
        mInstanceField.set(defaultSingleton, proxy);
    }
}