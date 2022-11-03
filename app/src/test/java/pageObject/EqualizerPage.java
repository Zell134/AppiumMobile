package pageObject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.openqa.selenium.InvalidSelectorException;

public class EqualizerPage {

    private final SelenideElement backBtn = $(MobileBy.id("com.zell.musicplayer:id/back_button"));
    private final SelenideElement presetList = $(MobileBy.xpath("//android.widget.Spinner/android.widget.TextView"));

    @Step("Check seek bars is visible")
    public void isSeekBarsVisible() {
        for (int i = 0; i < 5; i++) {
            try {
                assertThat($(MobileBy.AndroidViewTag(String.valueOf(i))).is(Condition.visible)).isTrue();
            } catch (InvalidSelectorException e) {
                break;
            }
        }    
    }

    @Step("Click \"Back\" button")
    public void clickBackButon() {
        backBtn.should(Condition.enabled).click();
    }

    @Step("\"Preset list\" button is visible")
    public void presetListButtonIsVisible() {
        assertThat(presetList.isDisplayed()).isTrue();
    }

    @Step("Click \"Preset list\" button")
    public void clickPresetListButton() {
        presetList.should(Condition.enabled).click();
    }

    @Step("Is presets list populated by presets")
    public void isPresetListPopulated() {
        ElementsCollection presets = $$(MobileBy.id("android:id/text1"));
        assertThat(presets).isNotNull();
        assertThat(presets.size()).isGreaterThan(5);
    }

    public String getPresetListCurrentValue() {
        return presetList.should(Condition.enabled).getText();
    }

    public String getPresetListValue(int position) {
        SelenideElement preset = $$(MobileBy.id("android:id/text1")).get(position);
        return preset.getText();
    }

    public int getPresetsQuantity() {
        ElementsCollection preset = $$(MobileBy.id("android:id/text1"));
        return preset.size();
    }

    @Step("Select preset in postion {0}")
    public void selectPresetsByPosition(int position) {
        $$(MobileBy.id("android:id/text1")).get(position).click();
    }

    private List<SelenideElement> getSeekBars() {
        List<SelenideElement> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            try {
                list.add($(MobileBy.AndroidViewTag(String.valueOf(i))));
            } catch (InvalidSelectorException e) {
                break;
            }
        }
        return list;
    }

}
