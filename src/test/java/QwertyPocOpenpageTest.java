import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import org.junit.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class QwertyPocOpenpageTest {

    /**
     * Open main page in browser, validate that URL matches - so there are no redirections
     */
    @Test
    public void openPage() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://qualityminds.com/");
            assertThat(page).hasURL("https://qualityminds.com/");
        }
    }

    /**
     * Close cookie banner and validate it is hidden
     */
    @Test
    public void openPageCloseCookieBanner() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://qualityminds.com/");
            assertThat(page.locator(".cmplz-body")).isVisible();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Accept all")).click();
            assertThat(page.locator(".cmplz-body")).isHidden();
        }
    }

    /**
     * Hover on EN flag, select PL as new language and validate new URL
     */
    @Test
    public void openPageSetLangToPL() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://qualityminds.com/");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("PL")).isHidden();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("EN 3")).isVisible();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("EN 3")).hover();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("PL")).isVisible();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("PL")).click();
            page.locator("#top-menu").screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("target/screenshot.png")));
            assertThat(page).hasURL("https://qualityminds.com/pl/");
        }
    }


}



