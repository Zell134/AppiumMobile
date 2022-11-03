package pageObject;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;
import static org.assertj.core.api.Assertions.assertThat;

public class MainMenuPage {
    private final SelenideElement externalStorageBtn = $(MobileBy.id("com.zell.musicplayer:id/external_storage"));
    private final SelenideElement allMediaBtn = $(MobileBy.id("com.zell.musicplayer:id/all_media"));
    private final SelenideElement artistsBtn = $(MobileBy.id("com.zell.musicplayer:id/artists"));
    private final SelenideElement qualizerBtn = $(MobileBy.id("com.zell.musicplayer:id/menu_equalizer"));
    private final SelenideElement exitBtn = $(MobileBy.id("com.zell.musicplayer:id/exit"));
    private final SelenideElement mainMenu = $(MobileBy.id("com.zell.musicplayer:id/nav_view"));
    
    @Step("Click main menu equalizer button")
    public void qualizerBtnClick(){
        qualizerBtn.should(Condition.enabled).click();
    }
    
    @Step("Menu is shown")
    public void isMenuShown(){
        assertThat(mainMenu.is(Condition.visible)).isTrue();
    }
}
