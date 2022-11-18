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


    @Step("Song info is displayed")
    public ControlsPage isSongInfoIsDisplayed() {
        assertThat(songInfo.isDisplayed()).isTrue();
        assertThat(songName.isDisplayed()).isTrue();
        return this;
    }

    @Step("Song info is invisible")
    public ControlsPage isSongInfoIsInvisible() {
        assertThat(songInfo.isDisplayed()).isFalse();
        assertThat(songName.isDisplayed()).isFalse();
        return this;
    }

    public String getSongInfo() {
        if (songInfo.isDisplayed() && songName.isDisplayed()) {
            String info = songInfo.should(Condition.visible).getText();
            String name = songName.should(Condition.visible).getText();
            return info + " : " + name;
        }
        return null;
    }

    @Step("Click stop playing button")
    public ControlsPage stop() {
        stopBtn.should(Condition.enabled).click();
        return this;
    }

    @Step("Check that all elements is visible")
    public ControlsPage isAllElementsVisible() {
        boolean isVisible = previousBtn.is(Condition.visible)
                && playBtn.is(Condition.visible)
                && stopBtn.is(Condition.visible)
                && nextBtn.is(Condition.visible)
                && equqlizerBtn.is(Condition.visible)
                && seekBar.is(Condition.visible)
                && albumImage.is(Condition.visible)
                && timer.is(Condition.visible);
        assertThat(isVisible).isTrue();
        return this;
    }

    @Step("Click \"Play/Pause\" button")
    public ControlsPage playPause() {
        playBtn.should(Condition.enabled).click();
        return this;
    }

    @Step("Get \"PlayPause\" button")
    public BufferedImage getPlayBtnImage() {
        return playBtn.screenshotAsImage();
    }

    @Step("Click \"Next\" button")
    public ControlsPage nextSong() {
        nextBtn.should(Condition.enabled).click();
        return this;
    }

    @Step("Click \"Previous\" button")
    public ControlsPage previousSong() {
        previousBtn.should(Condition.enabled).click();
        return this;
    }

    @Step("Click \"Equalizer\" button")
    public EqualizerPage equalizerClick() {
        equqlizerBtn.should(Condition.enabled).click();
        return new EqualizerPage();
    }

    @Step("Click on seek bar")
    public ControlsPage seekBarClick() {
        seekBar.should(Condition.enabled).click();
        return this;
    }

    @Step("Progress is equals to zero")
    public ControlsPage timerProgressEqualsZero() {
        assertThat(getTimerProgress()).isEqualTo(0);
        return this;
    }

    @Step("Progress is greater than zero")
    public ControlsPage timerProgressGreaterThanZero() {
        assertThat(getTimerProgress()).isGreaterThan(0);
        return this;
    }
    public int getTimerProgress() {
        String timerText = getTimerValue().split("/")[0];

        if (timerText.equals("0")) {
            return 0;
        }
        String[] splittedTimer = timerText.split(":");
        return Integer.valueOf(splittedTimer[0].trim()) * 60 + Integer.valueOf(splittedTimer[1].trim());
    }
    private boolean isPlayinState(){
        int start = getTimerProgress();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int end = getTimerProgress();
        return start != end;
    }
}
