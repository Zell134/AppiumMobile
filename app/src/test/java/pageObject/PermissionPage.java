package pageObject;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;

public class PermissionPage implements BasePageInterface {

    private final SelenideElement grantPermissionButton = $(MobileBy.id("com.android.permissioncontroller:id/permission_allow_button"));
    private final SelenideElement denyPermissionButton = $(MobileBy.id("com.android.permissioncontroller:id/permission_deny_button"));

    @Step("Check if current page is Permission page")
    public boolean ifThisIsPermissionPage() {
        return grantPermissionButton.exists() && denyPermissionButton.exists();
    }

    @Step("Click garant permissions button")
    public MainActivityPage grantPermissions() {
        grantPermissionButton.should(Condition.visible).click();
        return new MainActivityPage();
    }

    @Step("Click deny permissions button")
    public MainActivityPage denyPermissions() {
        denyPermissionButton.should(Condition.visible).click();
        return new MainActivityPage();
    }

}
