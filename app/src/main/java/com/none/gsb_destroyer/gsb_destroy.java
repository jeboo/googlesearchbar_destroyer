package com.none.gsb_destroyer;

import android.view.View;
import android.view.ViewGroup.LayoutParams;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class gsb_destroy implements IXposedHookLoadPackage {
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.google.android.googlequicksearchbox"))
        {
            findAndHookMethod("com.android.launcher3.Launcher", lpparam.classLoader, "getOrCreateQsbBar",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        }
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            //XposedBridge.log("Inside GSBhook");
                            // 0x0 trick from theknut/GEL
                            View qsBar = (View) param.getResult();
                            LayoutParams vglp = qsBar.getLayoutParams();
                            vglp.width = vglp.height =  0;
                            qsBar.setLayoutParams(vglp);
                        }
                    });
        }
    }

}

