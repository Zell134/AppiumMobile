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
        PlaylistPage playlist = new PlaylistPage();
        playlist.ifPlaylistExistOnPage();

        HeaderPage header = new HeaderPage();
        header.menuBtnClick();
        MainMenuPage menu = new MainMenuPage();
        menu.qualizerBtnClick();
        EqualizerPage equalizer = new EqualizerPage();
        equalizer.isSeekBarsVisible();

        header.menuBtnClick();
        menu.qualizerBtnClick();
        playlist.ifPlaylistExistOnPage();
    }

    @Description("Equalizer is closed after \"Back\" button is clicked")
    @Test
    public void equalizerIsClosed_OnBackButtonClicked() {
        ControlsPage controls = new ControlsPage();
        controls.equalizerClick();
        EqualizerPage equalizer = new EqualizerPage();
        equalizer.isSeekBarsVisible();
        equalizer.clickBackButon();

        PlaylistPage playlist = new PlaylistPage();
        playlist.ifPlaylistExistOnPage();
    }

    @Description("Equalizer is closed after android back button is pressed")
    @Test
    public void equalizerIsClosed_OnBackPressed() {
        EqualizerPage equalizer = openEqualizer();
        equalizer.isSeekBarsVisible();
        DriverHelper.goBack();

        PlaylistPage playlist = new PlaylistPage();
        playlist.ifPlaylistExistOnPage();
    }

    @Description("Preset list is populated by presets")
    @Test
    public void usersPresetIsDefault() {
        EqualizerPage equalizer = openEqualizer();
        equalizer.presetListButtonIsVisible();
        step("Defaul preset is \"" + DEFAULT_PRESET + "\"", () -> {
            assertThat(DEFAULT_PRESET).isEqualTo(equalizer.getPresetListCurrentValue());
        });
    }

    @Description("Preset list is populated by presets")
    @Test
    public void presetListIsPopulatedByPresets() {
        EqualizerPage equalizer = openEqualizer();
        equalizer.presetListButtonIsVisible();
        equalizer.clickPresetListButton();
        equalizer.isPresetListPopulated();
    }

    @Description("Selected preset displays in preset list text field")
    @Test
    public void selectedPresetDisplays() {
        EqualizerPage equalizer = openEqualizer();
        selectAnyPreset(equalizer);
    }
    
    private void selectAnyPreset(EqualizerPage equalizer) {
        equalizer.presetListButtonIsVisible();
        equalizer.clickPresetListButton();
        int selectedPosition = new Random().nextInt(equalizer.getPresetsQuantity());
        String expectedValue = equalizer.getPresetListValue(selectedPosition);
        equalizer.selectPresetsByPosition(selectedPosition);
        String actualValue = equalizer.getPresetListCurrentValue();

        step("Selected preset displays in preset list text field", ()
                -> assertThat(actualValue).isEqualTo(expectedValue)
        );
    }

    private EqualizerPage openEqualizer() {
        ControlsPage controls = new ControlsPage();
        controls.equalizerClick();
        return new EqualizerPage();
    }

}
