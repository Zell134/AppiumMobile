package tests;

import pageObject.MainActivityPage;
import pageObject.PermissionPage;
import com.codeborne.selenide.Configuration;
import driver.DeviceDriverWithoutPermissions;
import io.qameta.allure.Description;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pageObject.PlaylistPage;

public class MainActivityGrantPermissionsTest extends BaseTest {

    @BeforeAll
    public static void init() {
        Configuration.browser = DeviceDriverWithoutPermissions.class.getName();
    }

    @Description("Request permissions button should appears on Main Activity if permissions are not granted")
    @Test
    public void MainActivity_PermissionRequestButtonAppears_OnDenyPermission() {
        PermissionPage permissionPage = permissionPageAssertion();
        denyPermissions_AndClickGrantButton(permissionPage);
        MainActivityPage mainPage = permissionPage.denyPermissions();
        assertThat(mainPage.ifGrantPermissionButtonExistOnPage());
    }

    @Description("Playlist should appears when permissions are granted on first start")
    @Test
    public void MainActivity_PalyListApperas_WhenGrantPermissionOnFirstStart() {
        PermissionPage permissionPage = permissionPageAssertion();
        MainActivityPage mainPage = permissionPage.grantPermissions();
        PlaylistPage playlist = new PlaylistPage();
        assertThat(playlist.ifPlaylistExistOnPage());
    }

    @Description("Playlist should appears when permissions are granted via Main activity button")
    @Test
    public void MainActivity_PalyListApperas_WhenGrantPermissionViaMainPage() {
        PermissionPage permissionPage = permissionPageAssertion();
        denyPermissions_AndClickGrantButton(permissionPage);
        MainActivityPage mainPage = permissionPage.grantPermissions();
        PlaylistPage playlist = new PlaylistPage();
        assertThat(playlist.ifPlaylistExistOnPage());
    }

    private PermissionPage permissionPageAssertion() {
        PermissionPage permissionPage = new PermissionPage();
        assertThat(permissionPage.ifThisIsPermissionPage());
        return permissionPage;
    }

    private PermissionPage denyPermissions_AndClickGrantButton(PermissionPage permissionPage) {
        MainActivityPage mainPage = permissionPage.denyPermissions();
        assertThat(mainPage.ifGrantPermissionButtonExistOnPage());
        mainPage.grantPermissions();
        assertThat(permissionPage.ifThisIsPermissionPage());
        return permissionPage;
    }

}
