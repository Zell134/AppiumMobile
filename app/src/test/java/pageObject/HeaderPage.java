package pageObject;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;

public class HeaderPage {

    private final SelenideElement menuBtn = $(MobileBy.xpath("//android.view.ViewGroup/android.widget.ImageButton"));
    private final SelenideElement exitBtn = $(MobileBy.id("com.zell.musicplayer:id/exit"));
    
    @Step("Click main menu button")
    public MainMenuPage menuBtnClick(){
        menuBtn.should(Condition.enabled).click();
        return new MainMenuPage();
    }
    
     @Step("Click \"Close\" button")
    public HeaderPage exitBtnClick(){
        exitBtn.should(Condition.enabled).click();
         return this;
    }
}
