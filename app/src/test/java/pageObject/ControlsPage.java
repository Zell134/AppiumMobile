package pageObject;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;
import java.awt.image.BufferedImage;
import static org.assertj.core.api.Assertions.assertThat;

public class ControlsPage {

    private final SelenideElement previousBtn = $(MobileBy.id("com.zell.musicplayer:id/previous"));
    private final SelenideElement playBtn = $(MobileBy.id("com.zell.musicplayer:id/play"));
    private final SelenideElement stopBtn = $(MobileBy.id("com.zell.musicplayer:id/stop"));
    private final SelenideElement nextBtn = $(MobileBy.id("com.zell.musicplayer:id/next"));
    private final SelenideElement equqlizerBtn = $(MobileBy.id("com.zell.musicplayer:id/equqlizer_button"));
    private final SelenideElement seekBar = $(MobileBy.id("com.zell.musicplayer:id/seekbar"));
    private final SelenideElement albumImage = $(MobileBy.id("com.zell.musicplayer:id/album_art"));
    private final SelenideElement timer = $(MobileBy.id("com.zell.musicplayer:id/timer"));
    private final SelenideElement songInfo = $(MobileBy.id("com.zell.musicplayer:id/playing_song_info"));
    private final SelenideElement songName = $(MobileBy.id("com.zell.musicplayer:id/playing_song_name"));

    public String getTimerValue() {
        return timer.should(Condition.visible).getText();
    }

    public String getSongInfoText() {
        if (songInfo.isDisplayed()) {
            return songInfo.should(Condition.visible).getText();
        }
        return null;
    }

    public String getSongNameText() {
        if (songName.isDisplayed()) {
            return songName.should(Condition.visible).getText();
        }
        return null;
    }

    @Step("Click stop playing button")
    public void stop() {
        stopBtn.should(Condition.enabled).click();
    }

    @Step("Check that all elements is visible")
    public void isAllElementsVisible() {
        boolean isVisible = previousBtn.is(Condition.visible)
                && playBtn.is(Condition.visible)
                && stopBtn.is(Condition.visible)
                && nextBtn.is(Condition.visible)
                && equqlizerBtn.is(Condition.visible)
                && seekBar.is(Condition.visible)
                && albumImage.is(Condition.visible)
                && timer.is(Condition.visible);
        assertThat(isVisible).isTrue();
    }

    @Step("Click \"PlayPause\" button")
    public void playPause() {
        playBtn.should(Condition.enabled).click();
    }

    @Step("Get \"PlayPause\" button")
    public BufferedImage getPlayBtnImage() {
        return playBtn.screenshotAsImage();
    }

    @Step("Click \"Next\" button")
    public void nextSong() {
        nextBtn.should(Condition.enabled).click();
    }

    @Step("Click \"Previous\" button")
    public void previousSong() {
        previousBtn.should(Condition.enabled).click();
    }

    @Step("Click \"Equalizer\" button")
    public void equalizerClick() {
        equqlizerBtn.should(Condition.enabled).click();
    }

    @Step("Click on seek bar")
    public void seekBarClick() {
        seekBar.should(Condition.enabled).click();
    }

    public int getTimerProgress() {
        String timerText = getTimerValue().split("/")[0];

        if (timerText.equals("0")) {
            return 0;
        }
        String[] splittedTimer = timerText.split(":");
        return Integer.valueOf(splittedTimer[0].trim()) * 60 + Integer.valueOf(splittedTimer[1].trim());
    }
}
