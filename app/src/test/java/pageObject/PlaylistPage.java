package pageObject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;

public class PlaylistPage {
    
    private final ElementsCollection menuBtn = $$(MobileBy.id("com.zell.musicplayer:id/song_title"));
    private final SelenideElement playlist = $(MobileBy.id("com.zell.musicplayer:id/playlist"));
    
    @Step("Check if playlist exists on the page")
    public boolean ifPlaylistExistOnPage() {
        return playlist.should(Condition.visible).exists();
    }    
}
