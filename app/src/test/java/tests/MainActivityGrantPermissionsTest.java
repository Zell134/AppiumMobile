package tests;

import pageObject.PermissionPage;
import com.codeborne.selenide.Configuration;
import driver.DeviceDriverWithoutPermissions;
import io.qameta.allure.Description;
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
        denyPermissions_AndClickGrantButton()
                .denyPermissions()
                .ifGrantPermissionButtonExistOnPage();
    }

    @Description("Playlist should appears when permissions are granted on first start")
    @Test
    public void MainActivity_PlayListAppears_WhenGrantPermissionOnFirstStart() {
        PermissionPage permissionPage = permissionPageAssertion();
        permissionPage.grantPermissions();
        new PlaylistPage().ifPlaylistExistOnPage();
    }

    @Description("Playlist should appears when permissions are granted via Main activity button")
    @Test
    public void MainActivity_PlayListAppears_WhenGrantPermissionViaMainPage() {
        PermissionPage permissionPage = denyPermissions_AndClickGrantButton();
        permissionPage.grantPermissions();
        new PlaylistPage().ifPlaylistExistOnPage();
    }

    private PermissionPage permissionPageAssertion() {
        return new PermissionPage()
                .ifThisIsPermissionPage();
    }

    private PermissionPage denyPermissions_AndClickGrantButton() {
        PermissionPage permissionPage = permissionPageAssertion();
        permissionPage
                .denyPermissions()
                .ifGrantPermissionButtonExistOnPage()
                .grantPermissions();

        permissionPage.ifThisIsPermissionPage();
        return permissionPage;
    }

}
