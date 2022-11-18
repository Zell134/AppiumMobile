package tests;

import pageObject.ControlsPage;
import pageObject.EqualizerPage;
import com.codeborne.selenide.Configuration;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import driver.DeviceDriver;
import static driver.DriverHelper.*;
import static io.qameta.allure.Allure.step;
import io.qameta.allure.Description;
import java.awt.image.BufferedImage;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.ScreenOrientation;
import pageObject.PlaylistPage;

public class ControlsPageTest extends BaseTest {

    @BeforeAll
    public static void init() {
        Configuration.browser = DeviceDriver.class.getName();
    }

    @Description("All elements is visible on start application")
    @Test
    public void isAllElementsVisible_OnStart() {
        new ControlsPage()
                .isAllElementsVisible();
    }

    @Description("Image of \"Play/Pause\" button changed if click on this one")
    @Test
    public void anotherImageShownOnPlayButton_onPlayClicked() {

        ControlsPage controlsPage = new ControlsPage();
        BufferedImage expectedStartImage = controlsPage.getPlayBtnImage();
        controlsPage.playPause();
        BufferedImage stopImage = controlsPage.getPlayBtnImage();

        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedStartImage, stopImage).compareImages();
        assertThat(imageComparisonResult.getImageComparisonState()).isEqualByComparingTo(ImageComparisonState.MISMATCH);

        controlsPage.playPause();
        BufferedImage actualStartImage = controlsPage.getPlayBtnImage();
        imageComparisonResult = new ImageComparison(expectedStartImage, actualStartImage).compareImages();
        assertThat(imageComparisonResult.getImageComparisonState()).isEqualByComparingTo(ImageComparisonState.MATCH);

//        ImageComparisonUtil.saveImage(new File("src/test/resources/expectedScreenshots/playBtn.png"), imageComparisonResult.getResult());
    }

    @Description("Song name and song info fields should displayed on playing")
    @Test
    public void songInfoIsDisplayed_OnPlaying() {
        new ControlsPage()
                .isSongInfoIsInvisible()
                .playPause()
                .isSongInfoIsDisplayed();
    }

    @Description("Song name and song info fields should disapears on stop playing")
    @Test
    public void songInfoIsInvisible_OnStopPlaying() {
        new ControlsPage()
                .playPause()
                .isSongInfoIsDisplayed()
                .stop()
                .isSongInfoIsInvisible();
    }

    @Description("The same song info and song name is displayed on pause and resume playing")
    @Test
    public void theSameSongInfoIsDisplayed_OnPlaying() {
        ControlsPage controlsPage = new ControlsPage();
        controlsPage.playPause();
        String expectedSongInfo = controlsPage.getSongInfo();
        controlsPage.playPause();
        String actualSongInfoText = controlsPage.getSongInfo();
        step("Song name and song info fields not changed after resume playing", () -> {
            assertThat(expectedSongInfo).isEqualTo(actualSongInfoText);
        });
    }

    @Description("The same song info and song name is displayed on change screen orientation")
    @Test
    public void theSameSongInfoIsDisplayed_OnScreenOrientationChanged() {
        ControlsPage controlsPage = new ControlsPage();
        controlsPage.playPause();
        String expectedSongInfo = controlsPage.getSongInfo();

        rotateScreen(ScreenOrientation.LANDSCAPE);
        String actualSongInfo = controlsPage.getSongInfo();
        step("Song name and song info fields is the same after change screen orientation to landscape", () -> {
            assertThat(expectedSongInfo).isEqualTo(actualSongInfo);
        });
    }

    @Description("The same song is playing on click \"Next\" and than \"Previous\" button")
    @Test
    public void theSameSongIsPlaying_OnClickNextAndPreviousButton() {
        ControlsPage controlsPage = new ControlsPage();
        controlsPage.playPause();
        String expectedSongInfo = controlsPage.getSongInfo();
        controlsPage.nextSong()
                .previousSong();
        String actualSongInfo = controlsPage.getSongInfo();

        step("Next song is plaing after click \"Next\" button", () -> {
            assertThat(expectedSongInfo).isEqualTo(actualSongInfo);
        });
    }

    @Description("The same song is playing after restart application")
    @Test
    public void theSameSongIsPlaying_AfterRestartApp() {
        ControlsPage controlsPage = new ControlsPage();
        controlsPage.playPause()
                .nextSong();
        String expectedSongInfo = controlsPage.getSongInfo();
        closeApp();
        launchApp();
        String actualSongInfo = controlsPage.getSongInfo();

        step("The same song is playing", () -> {
            assertThat(expectedSongInfo).isEqualTo(actualSongInfo);
        });
    }

    @Description("Equalizer opens and closes after clcking \"Equalizer\" button")
    @Test
    public void equalizerOpensAndCloses_OnClickingButton() {
        ControlsPage controlsPage = new ControlsPage();
        PlaylistPage playlist = new PlaylistPage();
        playlist.ifPlaylistExistOnPage();

        EqualizerPage equalizerPage = controlsPage.equalizerClick();
        equalizerPage.isSeekBarsVisible();

        controlsPage.equalizerClick();
        playlist.ifPlaylistExistOnPage();
    }

    @Description("Clicking on seek bar change current song progress")
    @Test
    public void progressChanged_OnSeekbarClicking() {
        new ControlsPage()
                .timerProgressEqualsZero()
                .playPause()
                .seekBarClick()
                .timerProgressGreaterThanZero();
    }

    @Description("App do not crashed on clicking on seek bar on stopped state")
    @Test
    public void appNotCrashed_OnSeekbakClickingOnStoppedState() {
        new ControlsPage()
                .timerProgressEqualsZero()
                .seekBarClick()
                .timerProgressEqualsZero();

    }
}
