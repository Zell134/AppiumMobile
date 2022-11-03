package tests;

import pageObject.ControlsPage;
import pageObject.EqualizerPage;
import pageObject.MainActivityPage;
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
        new ControlsPage().isAllElementsVisible();
    }

    @Description("Image of \"Play/Pause\" button changed if click on this one")
    @Test
    public void anotherImageShownOnPlayButton_onPlayClicked() {

        ControlsPage controlsPage = new ControlsPage();
        BufferedImage actualImage = controlsPage.getPlayBtnImage();
        controlsPage.playPause();
        BufferedImage expectedImage = controlsPage.getPlayBtnImage();

        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();
        assertThat(imageComparisonResult.getImageComparisonState()).isEqualByComparingTo(ImageComparisonState.MISMATCH);

        controlsPage.playPause();
        expectedImage = controlsPage.getPlayBtnImage();
        imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();
        assertThat(imageComparisonResult.getImageComparisonState()).isEqualByComparingTo(ImageComparisonState.MATCH);

//        ImageComparisonUtil.saveImage(new File("src/test/resources/expectedScreenshots/playBtn.png"), imageComparisonResult.getResult());
    }

    @Description("Song name and song info fields should displayed on playing")
    @Test
    public void songInfoIsDisplayed_OnPlaying() {
        ControlsPage controlsPage = new ControlsPage();
        isSongInfoIsInvisible(controlsPage);
        controlsPage.playPause();
        isSongInfoIsDisplayed(controlsPage);
    }

    @Description("Song name and song info fields should disapears on stop playing")
    @Test
    public void songInfoIsInvisible_OnStopPlaying() {
        ControlsPage controlsPage = new ControlsPage();
        controlsPage.playPause();
        isSongInfoIsDisplayed(controlsPage);
        controlsPage.stop();
        isSongInfoIsInvisible(controlsPage);
    }

    @Description("The same song info and song name is displayed on pause and resume playing")
    @Test
    public void theSameSongInfoIsDisplayed_OnPlaying() {
        ControlsPage controlsPage = new ControlsPage();
        controlsPage.playPause();
        String expectedSongInfoText = controlsPage.getSongInfoText();
        String expectedSongNameText = controlsPage.getSongNameText();
        controlsPage.playPause();
        String songInfoText = controlsPage.getSongInfoText();
        String songNameText = controlsPage.getSongNameText();
        step("Song name and song info fields not changed after resume playing", () -> {
            assertThat(expectedSongInfoText).isEqualTo(songInfoText);
            assertThat(expectedSongNameText).isEqualTo(songNameText);
        });
    }

    @Description("The same song info and song name is displayed on change screen orientation")
    @Test
    public void theSameSongInfoIsDisplayed_OnScreenOrientationChanged() {
        ControlsPage controlsPage = new ControlsPage();
        controlsPage.playPause();
        String expectedSongInfoText = controlsPage.getSongInfoText();
        String expectedSongNameText = controlsPage.getSongNameText();

        rotateScreen(ScreenOrientation.LANDSCAPE);
        String actualSongInfoText = controlsPage.getSongInfoText();
        String actualSongNameText = controlsPage.getSongNameText();
        step("Song name and song info fields is the same after change screen orientation to landscape", () -> {
            assertThat(expectedSongInfoText).isEqualTo(actualSongInfoText);
            assertThat(expectedSongNameText).isEqualTo(actualSongNameText);
        });
    }

    @Description("The same song is playing on click \"Next\" and than \"Previous\" button")
    @Test
    public void theSameSongIsPlaying_OnClickNextAndPreviousButton() {
        ControlsPage controlsPage = new ControlsPage();
        controlsPage.playPause();
        String expectedSongInfoText = controlsPage.getSongInfoText();
        String expectedSongNameText = controlsPage.getSongNameText();
        controlsPage.nextSong();
        controlsPage.previousSong();
        String actualSongInfoText = controlsPage.getSongInfoText();
        String actualSongNameText = controlsPage.getSongNameText();

        step("Next song is plaing after click \"Next\" button", () -> {
            assertThat(expectedSongInfoText).isEqualTo(actualSongInfoText);
            assertThat(expectedSongNameText).isEqualTo(actualSongNameText);
        });
    }

    @Description("The same song is playing after restart application")
    @Test
    public void theSameSongIsPlaying_AfterRestartApp() {
        ControlsPage controlsPage = new ControlsPage();
        controlsPage.playPause();
        controlsPage.nextSong();
        String expectedSongInfoText = controlsPage.getSongInfoText();
        String expectedSongNameText = controlsPage.getSongNameText();
        closeApp();
        launchApp();
        String actualSongInfoText = controlsPage.getSongInfoText();
        String actualSongNameText = controlsPage.getSongNameText();

        step("The same song is playing", () -> {
            assertThat(expectedSongInfoText).isEqualTo(actualSongInfoText);
            assertThat(expectedSongNameText).isEqualTo(actualSongNameText);
        });
    }

    @Description("Equalizer opens and closes after clcking \"Equalizer\" button")
    @Test
    public void equalizerOpensAndCloses_OnClickingButton() {
        ControlsPage controlsPage = new ControlsPage();
        PlaylistPage playlist = new PlaylistPage();
        playlist.ifPlaylistExistOnPage();

        controlsPage.equalizerClick();
        EqualizerPage equalizerPage = new EqualizerPage();
        equalizerPage.isSeekBarsVisible();

        controlsPage.equalizerClick();
        playlist.ifPlaylistExistOnPage();
    }

    @Description("Clicking on seek bar change current song progress")
    @Test
    public void progressChanged_OnSeekbarClicking() {
        ControlsPage controlsPage = new ControlsPage();
        int initialProgress = controlsPage.getTimerProgress();
        step("Progress is equals to zero on not playing",
                () -> assertThat(initialProgress).isEqualTo(0)
        );
        controlsPage.playPause();
        controlsPage.seekBarClick();

        step("Progress is greater than initial progress",
                () -> assertThat(initialProgress).isLessThan(controlsPage.getTimerProgress())
        );
    }

    @Description("App do not crashed on clicking on seek bar on stopped state")
    @Test
    public void appNotCrashed_OnSeekbakClickingOnStoppedState() {
        ControlsPage controlsPage = new ControlsPage();
        step("Progress is equals to zero on not playing",
                () -> assertThat(controlsPage.getTimerProgress()).isEqualTo(0)
        );
        controlsPage.seekBarClick();
        step("Progress is equals to zero on not playing",
                () -> assertThat(controlsPage.getTimerProgress()).isEqualTo(0)
        );
    }

    private void isSongInfoIsInvisible(ControlsPage controlsPage) {
        String songInfoText = controlsPage.getSongInfoText();
        String songNameText = controlsPage.getSongNameText();
        step("Song info is invisible when not playing",
                () -> assertThat(songInfoText).isNull()
        );
        step("Song name is invisible when not playing",
                () -> assertThat(songNameText).isNull()
        );
    }

    private void isSongInfoIsDisplayed(ControlsPage controlsPage) {
        String songInfoText = controlsPage.getSongInfoText();
        String songNameText = controlsPage.getSongNameText();
        step("Song info is visible and filled with text",
                () -> assertThat(songInfoText).isNotNull().isNotEmpty()
        );
        step("Song name is visible and filled with text",
                () -> assertThat(songNameText).isNotNull().isNotEmpty()
        );
    }

}
