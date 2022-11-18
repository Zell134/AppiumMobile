package pageObject;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;

public class MainActivityPage implements BasePageInterface {

    private final SelenideElement grantPermissionButton = $(MobileBy.id("com.zell.musicplayer:id/button"));

    @Step("Check if current page is Main activity page")
    public MainActivityPage ifGrantPermissionButtonExistOnPage() {
        grantPermissionButton.should(Condition.visible).exists();
        return this;
    }    

    @Step("Click grant permissions button")
    public BasePageInterface grantPermissions() {
        if (grantPermissionButton != null) {
            grantPermissionButton.should(Condition.visible).click();
            return new PermissionPage();
        }
        return this;
    }

}
