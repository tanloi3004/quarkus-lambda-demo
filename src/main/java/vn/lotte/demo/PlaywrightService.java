package vn.lotte.demo;

import com.microsoft.playwright.*;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class PlaywrightService {

  private static final Logger LOGGER = Logger.getLogger(PlaywrightService.class.getName());

  @ConfigProperty(name = "playwright.chromium.path")
  String chromiumPath;

  public String getMetaDescription(ShareUrlRequest request) {
    LOGGER.log(Level.INFO, "Starting getMetaDescription for URL: {0}", request.getUrl());
    try (Playwright playwright = Playwright.create()) {
      LOGGER.log(Level.INFO, "Playwright created successfully.");
      BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
          .setHeadless(true)
          .setExecutablePath(Paths.get(chromiumPath)); // Use the configured Chromium path
      // Browser browser = playwright.chromium().launch(options);
      Browser browser = playwright.chromium().launch();
      LOGGER.log(Level.INFO, "Browser launched in headless mode.");
      Page page = browser.newPage();
      LOGGER.log(Level.INFO, "New page created.");
      page.navigate(request.getUrl());
      LOGGER.log(Level.INFO, "Navigated to URL: {0}", request.getUrl());
      String metaDescription = page.locator("meta[name='description']").getAttribute("content");
      LOGGER.log(Level.INFO, "Meta description retrieved: {0}", metaDescription);
      browser.close();
      LOGGER.log(Level.INFO, "Browser closed.");
      return metaDescription;
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error occurred while getting meta description.", e);
      return null;
    }
  }
}