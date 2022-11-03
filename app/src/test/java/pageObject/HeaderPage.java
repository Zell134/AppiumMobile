package pageObject;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;

public class HeaderPage {
    
    private final SelenideElement menuBtn = $(MobileBy.xpath("//android.widget.ImageButton[@content-desc='Открыть меню']"));
    private final SelenideElement exitBtn = $(MobileBy.id("com.zell.musicplayer:id/exit"));
    
    @Step("Click main menu button")
    public void menuBtnClick(){
        menuBtn.should(Condition.enabled).click();
    }
    
     @Step("Click \"Close\" button")
    public void exitBtnClick(){
        exitBtn.should(Condition.enabled).click();
    }
}
