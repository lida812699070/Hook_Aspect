package com.baitaoshuo.hookactivity.aspect;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class LoginAspect {

    @Pointcut("execution(@com.n_add.android.aspect.LoginAnnotation * *(..))")
    public void loginPointcut() {

    }

    @Around("loginPointcut()")
    public Object onLoginLisener(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //方法执行时
        Object object = null;
        try {
            Log.e("aspect","执行了");
//            if (AccountUtil.getInstance().isLogin()) {
//                proceedingJoinPoint.proceed();
//            } else {
//                Context context = NPlusApplication.getInstance();
//                Intent intent = new Intent(context, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                context.startActivity(intent);
//            }
            proceedingJoinPoint.proceed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 获取参数Map集合
     *
     * @param joinPoint
     * @return
     */
    Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>();

        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();

        for (int i = 0; i < paramNames.length; i++) {
            param.put(paramNames[i], paramValues[i]);
        }

        return param;
    }
}
