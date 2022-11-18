package tests;

import com.codeborne.selenide.Configuration;
import driver.DeviceDriver;
import driver.DriverHelper;
import static io.qameta.allure.Allure.step;
import io.qameta.allure.Description;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pageObject.ControlsPage;
import pageObject.EqualizerPage;
import pageObject.HeaderPage;
import pageObject.MainMenuPage;
import pageObject.PlaylistPage;

public class EqualizerPageTest extends BaseTest {

    private final String DEFAULT_PRESET = "Пользовательский";

    @BeforeAll
    public static void init() {
        Configuration.browser = DeviceDriver.class.getName();
    }

    @Description("Equalizer is opened via main menu")
    @Test
    public void equalizerOpenedViaMainMenu() {

        ifPlaylistExistOnPage();

        HeaderPage header = new HeaderPage();
        EqualizerPage equalizer = header
                .menuBtnClick()
                .qualizerBtnClick()
                .isSeekBarsVisible();

        header.menuBtnClick()
                .qualizerBtnClick();

        ifPlaylistExistOnPage();
    }

    @Description("Equalizer is closed after \"Back\" button is clicked")
    @Test
    public void equalizerIsClosed_OnBackButtonClicked() {
        openEqualizer()
                .isSeekBarsVisible()
                .clickBackButon();
        ifPlaylistExistOnPage();
    }

    @Description("Equalizer is closed after android back button is pressed")
    @Test
    public void equalizerIsClosed_OnBackPressed() {
        openEqualizer()
                .isSeekBarsVisible();
        DriverHelper.goBack();
        ifPlaylistExistOnPage();
    }

    @Description("Default preset is \"" + DEFAULT_PRESET + "\"")
    @Test
    public void usersPresetIsDefault() {
        openEqualizer()
                .presetListButtonIsVisible()
                .checkPresetListCurrentValue(DEFAULT_PRESET);
    }

    @Description("Preset list is populated by presets")
    @Test
    public void presetListIsPopulatedByPresets() {
        openEqualizer()
                .presetListButtonIsVisible()
                .clickPresetListButton()
                .isPresetListPopulated();
    }

    @Description("Selected preset displays in preset list text field")
    @Test
    public void selectedPresetDisplays() {
        EqualizerPage equalizer = openEqualizer();

        equalizer.presetListButtonIsVisible()
                .clickPresetListButton();
        int selectedPosition = new Random().nextInt(equalizer.getPresetsQuantity());
        String expectedValue = equalizer.getPresetListValue(selectedPosition);
        equalizer
                .selectPresetsByPosition(selectedPosition);
        String actualValue = equalizer.getPresetListCurrentValue();

        equalizer
                .checkPresetListCurrentValue(actualValue);
    }

    private EqualizerPage openEqualizer() {
        ControlsPage controls = new ControlsPage();
        controls.equalizerClick();
        return new EqualizerPage();
    }

    private void ifPlaylistExistOnPage(){
        PlaylistPage playlist = new PlaylistPage();
        playlist.ifPlaylistExistOnPage();
    }

}
