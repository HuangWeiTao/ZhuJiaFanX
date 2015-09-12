package test;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.test.InstrumentationTestCase;


public class LoginTester extends InstrumentationTestCase {
    private UiDevice device;
    UiObject2 testApp;

    @Override
    public void setUp() throws Exception {
        //super.setUp();

        device = UiDevice.getInstance(getInstrumentation());
        device.pressHome();
        device.waitForIdle();
        device.swipe(device.getDisplayWidth() - 100, 200, 20, 200, 10);//最后一个参数的设值过大会影响会testApp无法被点击
        device.waitForIdle();
        device.swipe(device.getDisplayWidth() - 100, 200, 20, 200, 10);
        device.waitForIdle();

        device.wait(Until.hasObject(By.text("住家饭")),4000);
        testApp=device.findObject(By.text("住家饭"));
        testApp.click();
    }

    public void testLoginSuccess() throws Exception {
        device.wait(Until.hasObject(By.text("我")), 30000);

        UiObject2 buttonWo=device.findObject(By.text("我"));
        buttonWo.click();

        device.waitForIdle();
        UiObject2 username=device.findObject(By.text("用户名"));
        UiObject2 password=device.findObject(By.text("密码"));
        UiObject2 loginButton=device.findObject(By.text("登录"));

        username.setText("4545");
        password.setText("4545");

        loginButton.click();
        device.wait(Until.hasObject(By.textContains("登录成功")), 10000);
        UiObject2 suceess=device.findObject(By.textContains("登录成功"));

        assertTrue(suceess!=null);
    }
}
