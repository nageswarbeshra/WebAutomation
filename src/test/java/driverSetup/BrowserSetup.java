package driverSetup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class BrowserSetup {

    /**
     * Sets up Chrome WebDriver with automatic driver download and predefined options
     * @return Chrome WebDriver instance
     * @throws Exception if driver setup fails
     */
    public static WebDriver setup_driver() throws Exception {
        return setupChromeDriver();
    }

    /**
     * ChromeDriver setup with automatic download if missing (equivalent to Python setup_driver)
     */
    public static WebDriver setupChromeDriver() throws Exception {
        System.out.println("Initializing Chrome WebDriver...");

        // Ensure drivers directory exists
        String driverDir = "drivers";
        try {
            Files.createDirectories(Paths.get(driverDir));
            System.out.println("Drivers directory ensured: " + driverDir);
        } catch (Exception e) {
            System.out.println("[WARNING] Could not create drivers directory: " + e.getMessage());
        }

        // Clean up existing Chrome processes
        cleanupProcesses("chrome.exe", "chromedriver.exe");

        // Clear default Chrome user data directory
        clearChromeUserData();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-software-rasterizer");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-plugins-discovery");
        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
        chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--ignore-ssl-errors");
        chromeOptions.addArguments("--window-size=1920,1080");

        String chromedriverPath = getChromeDriverPath();
        System.setProperty("webdriver.chrome.driver", chromedriverPath);

        ChromeDriver driver = new ChromeDriver(chromeOptions);
        System.out.println("[OK] Chrome WebDriver initialized");
        return driver;
    }
    
    public static WebDriver setup_edgedriver() throws Exception {
        return setupEdgeDriver();
    }

    /**
     * Sets up Edge WebDriver with automatic driver download and predefined options
     */
    public static WebDriver setupEdgeDriver() throws Exception {
        System.out.println("Initializing WebDriver with enhanced EdgeDriver setup...");
        cleanupProcesses("msedge.exe", "msedgedriver.exe");

        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--start-maximized");
        edgeOptions.addArguments("--disable-infobars");
        edgeOptions.addArguments("--disable-extensions");
        edgeOptions.addArguments("--disable-notifications");
        edgeOptions.addArguments("--disable-popup-blocking");
        edgeOptions.addArguments("--no-sandbox");
        edgeOptions.addArguments("--disable-dev-shm-usage");
        edgeOptions.addArguments("--disable-gpu");
        edgeOptions.addArguments("--remote-debugging-port=9222");
        edgeOptions.addArguments("--disable-blink-features=AutomationControlled");
        edgeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        edgeOptions.setExperimentalOption("useAutomationExtension", false);
        edgeOptions.addArguments("--disable-web-security");
        edgeOptions.addArguments("--allow-running-insecure-content");
        edgeOptions.addArguments("--ignore-certificate-errors");
        edgeOptions.addArguments("--ignore-ssl-errors");
        edgeOptions.addArguments("--ignore-certificate-errors-spki-list");
        edgeOptions.addArguments("--ignore-urlfetcher-cert-requests");
        edgeOptions.addArguments("--disable-background-timer-throttling");
        edgeOptions.addArguments("--disable-renderer-backgrounding");
        edgeOptions.addArguments("--disable-backgrounding-occluded-windows");
        edgeOptions.addArguments("--disable-ipc-flooding-protection");
        edgeOptions.addArguments("--disable-features=TranslateUI,VizDisplayCompositor,AudioServiceOutOfProcess");
        edgeOptions.addArguments("--disable-extensions-except=");
        edgeOptions.addArguments("--disable-plugins-discovery");
        edgeOptions.addArguments("--disable-default-apps");
        edgeOptions.addArguments("--disable-component-extensions-with-background-pages");
        edgeOptions.addArguments("--disable-software-rasterizer");
        edgeOptions.addArguments("--disable-background-mode");
        edgeOptions.addArguments("--disable-features=IsolateOrigins,site-per-process");
        edgeOptions.addArguments("--disable-webgl");
        edgeOptions.addArguments("--disable-3d-apis");
        edgeOptions.addArguments("--disable-accelerated-2d-canvas");
        edgeOptions.addArguments("--disable-features=VizDisplayCompositor");
        edgeOptions.addArguments("--disable-sync");
        edgeOptions.addArguments("--disable-signin-promo");
        edgeOptions.addArguments("--force-device-scale-factor=1");
        edgeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0");

        String edgedriverPath = download_edge_driver_from_internet();
        System.setProperty("webdriver.edge.driver", edgedriverPath);

        EdgeDriver driver = new EdgeDriver(edgeOptions);
        System.out.println("[OK] Edge WebDriver initialized");
        return driver;
    }
    
    public static WebDriver setup_gekodriver() throws Exception {
        return setupFirefoxDriver();
    }

    /**
     * Sets up Firefox WebDriver with automatic driver download and predefined options
     */
    public static WebDriver setupFirefoxDriver() throws Exception {
        System.out.println("Initializing WebDriver with enhanced FirefoxDriver setup...");
        cleanupProcesses("firefox.exe", "geckodriver.exe");

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--start-maximized");
        firefoxOptions.addArguments("--disable-infobars");
        firefoxOptions.addArguments("--disable-extensions");
        firefoxOptions.addArguments("--disable-notifications");
        firefoxOptions.addArguments("--disable-popup-blocking");

        firefoxOptions.addPreference("dom.webdriver.enabled", false);
        firefoxOptions.addPreference("dom.webnotifications.enabled", false);
        firefoxOptions.addPreference("geo.enabled", false);
        firefoxOptions.addPreference("browser.startup.homepage_override.mstone", "ignore");
        firefoxOptions.addPreference("startup.homepage_welcome_url", "");
        firefoxOptions.addPreference("startup.homepage_welcome_url.additional", "");
        firefoxOptions.addPreference("browser.crashReports.unsubmittedCheck.autoSubmit2", false);
        firefoxOptions.addPreference("datareporting.healthreport.service.enabled", false);
        firefoxOptions.addPreference("datareporting.healthreport.uploadEnabled", false);
        firefoxOptions.addPreference("toolkit.telemetry.enabled", false);
        firefoxOptions.addPreference("toolkit.telemetry.unified", false);
        firefoxOptions.addPreference("toolkit.telemetry.archive.enabled", false);
        firefoxOptions.addPreference("experiments.enabled", false);
        firefoxOptions.addPreference("experiments.supported", false);
        firefoxOptions.addPreference("experiments.activeExperiment", false);
        firefoxOptions.addPreference("security.insecure_field_warning.contextual.enabled", false);
        firefoxOptions.addPreference("security.insecure_password.ui.enabled", false);
        firefoxOptions.addPreference("security.mixed_content.block_display_content", false);
        firefoxOptions.addPreference("security.mixed_content.upgrade_display_content", false);
        firefoxOptions.addPreference("security.enterprise_roots.enabled", true);
        firefoxOptions.addPreference("network.cookie.cookieBehavior", 1);
        firefoxOptions.addPreference("dom.max_script_run_time", 0);
        firefoxOptions.addPreference("dom.min_background_timeout_value", 10);
        firefoxOptions.addPreference("media.autoplay.default", 0);
        firefoxOptions.addPreference("permissions.default.microphone", 2);
        firefoxOptions.addPreference("permissions.default.camera", 2);
        firefoxOptions.addPreference("extensions.install.requireSecureOrigin", false);
        firefoxOptions.addPreference("devtools.chrome.enabled", true);
        firefoxOptions.addPreference("devtools.debugger.prompt-connection", false);
        firefoxOptions.addPreference("network.http.connection-timeout", 30);
        firefoxOptions.addPreference("network.http.response.timeout", 30);

        String geckodriverPath = download_geckodriver_from_internet();
        System.setProperty("webdriver.gecko.driver", geckodriverPath);

        FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
        System.out.println("[OK] Firefox WebDriver initialized");
        return driver;
    }

    private static String getChromeDriverPath() throws Exception {
        String driverDir = "drivers";
        Files.createDirectories(Paths.get(driverDir));
        String chromedriverPath = Paths.get(driverDir, "chromedriver.exe").toString();

        if (!Files.exists(Paths.get(chromedriverPath))) {
            System.out.println("ChromeDriver not found. Attempting to download...");
            String chromeVersion = getChromeVersion();
            if (chromeVersion == null) {
                throw new Exception("Could not detect Chrome version for ChromeDriver download");
            }
            System.out.println("Detected Chrome version: " + chromeVersion);
            download_chromedriver(chromeVersion, driverDir);
        }

        return chromedriverPath;
    }

    private static String getChromeVersion() {
        try {
            // Try different registry paths for Chrome version
            String[] regPaths = {
                "HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Google Chrome",
                "HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon",
                "HKEY_LOCAL_MACHINE\\SOFTWARE\\Google\\Chrome\\BLBeacon"
            };

            for (String regPath : regPaths) {
                try {
                    Process process = Runtime.getRuntime().exec("cmd /c reg query \"" + regPath + "\" /v Version");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("Version")) {
                            String[] parts = line.trim().split("\\s+");
                            if (parts.length >= 3) {
                                return parts[2];
                            }
                        }
                    }
                } catch (Exception e) {
                    // Continue to next path
                }
            }
            System.out.println("Could not find Chrome version in common registry locations.");
            return null;
        } catch (Exception e) {
            System.out.println("An error occurred while reading Chrome version: " + e.getMessage());
            return null;
        }
    }

    private static String getEdgeVersion() {
        try {
            String[] regPaths = {
                "HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Microsoft Edge",
                "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Edge\\BLBeacon",
                "HKEY_CURRENT_USER\\Software\\Microsoft\\Edge\\BLBeacon"
            };

            for (String regPath : regPaths) {
                try {
                    Process process = Runtime.getRuntime().exec("cmd /c reg query \"" + regPath + "\" /v version");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("version")) {
                            String[] parts = line.trim().split("\\s+");
                            if (parts.length >= 3) {
                                return parts[2];
                            }
                        }
                    }
                } catch (Exception e) {
                    // Continue to next path
                }
            }
            System.out.println("Could not find Edge version in common registry locations.");
            return null;
        } catch (Exception e) {
            System.out.println("An error occurred while reading Edge version: " + e.getMessage());
            return null;
        }
    }

    private static void download_chromedriver(String chromeVersion, String driverDir) throws Exception {
        if (chromeVersion == null) {
            System.out.println("Chrome version not provided. Cannot download ChromeDriver.");
            return;
        }

        String baseUrl = "https://storage.googleapis.com/chrome-for-testing-public";
        String downloadUrl = baseUrl + "/" + chromeVersion + "/win64/chromedriver-win64.zip";

        downloadAndExtractZip(downloadUrl, driverDir, "chromedriver.exe", null); // Remove hardcoded folder name
        System.out.println("[OK] ChromeDriver setup successful");
    }

    private static String download_edge_driver_from_internet() throws Exception {
        String driverDir = "drivers";
        Files.createDirectories(Paths.get(driverDir));

        // Try multiple fallback strategies like Python implementation
        Exception lastException = null;

        // Strategy 1: Try direct download with detected Edge version
        try {
            String edgeVersion = getEdgeVersion();
            if (edgeVersion == null) {
                throw new Exception("Could not get Edge version for EdgeDriver download");
            }
            System.out.println("Detected Edge version: " + edgeVersion);

            String downloadUrl = "https://msedgedriver.microsoft.com/" + edgeVersion + "/edgedriver_win64.zip";
            System.out.println("Attempting direct download from: " + downloadUrl);

            downloadAndExtractZip(downloadUrl, driverDir, "msedgedriver.exe", null);
            System.out.println("[OK] EdgeDriver downloaded successfully using direct method");
            return Paths.get(driverDir, "msedgedriver.exe").toString();
        } catch (Exception e) {
            System.out.println("[WARNING] Direct download failed: " + e.getMessage());
            lastException = e;
        }

        // Strategy 2: Try latest stable version
        try {
            System.out.println("Trying to download latest stable EdgeDriver...");
            String latestStableUrl = "https://msedgedriver.azureedge.net/LATEST_STABLE";
            URL url = new URL(latestStableUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String latestVersion = reader.readLine().trim();
                System.out.println("Latest stable EdgeDriver version: " + latestVersion);

                String downloadUrl = "https://msedgedriver.azureedge.net/" + latestVersion + "/edgedriver_win64.zip";
                downloadAndExtractZip(downloadUrl, driverDir, "msedgedriver.exe", null);
                System.out.println("[OK] EdgeDriver downloaded successfully using latest stable fallback");
                return Paths.get(driverDir, "msedgedriver.exe").toString();
            }
        } catch (Exception e) {
            System.out.println("[WARNING] Latest stable download failed: " + e.getMessage());
            lastException = e;
        }

        // Strategy 3: Use hardcoded latest known version
        try {
            System.out.println("Trying hardcoded fallback version...");
            String fallbackUrl = "https://msedgedriver.azureedge.net/141.0.3770.100/edgedriver_win64.zip";
            downloadAndExtractZip(fallbackUrl, driverDir, "msedgedriver.exe", null);
            System.out.println("[OK] EdgeDriver downloaded successfully using hardcoded fallback");
            return Paths.get(driverDir, "msedgedriver.exe").toString();
        } catch (Exception e) {
            System.out.println("[ERROR] Hardcoded fallback also failed: " + e.getMessage());
            lastException = e;
        }

        throw new Exception("EdgeDriver download failed after trying all strategies. Last error: " + lastException.getMessage());
    }

    private static String download_geckodriver_from_internet() throws Exception {
        String downloadUrl = "https://github.com/mozilla/geckodriver/releases/download/v0.34.0/geckodriver-v0.34.0-win64.zip";

        String driverDir = "drivers";
        Files.createDirectories(Paths.get(driverDir));

        downloadAndExtractZip(downloadUrl, driverDir, "geckodriver.exe", null);
        return Paths.get(driverDir, "geckodriver.exe").toString();
    }

    private static void downloadAndExtractZip(String url, String destDir, String extractName, String extractPath) throws Exception {
        System.out.println("Downloading from: " + url);
        URL downloadUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream inputStream = connection.getInputStream();
             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {

            ZipEntry entry;
            byte[] buffer = new byte[1024];

            while ((entry = zipInputStream.getNextEntry()) != null) {
                String entryName = entry.getName();

                if (!entry.isDirectory() && entryName.endsWith(extractName)) {
                    Path extractFile = Paths.get(destDir, extractName);
                    try (FileOutputStream fos = new FileOutputStream(extractFile.toFile())) {
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                    extractFile.toFile().setExecutable(true);
                    System.out.println("Extracted: " + extractName);
                    break;
                }
                zipInputStream.closeEntry();
            }
        }
    }

    /**
     * Cleans up running processes by name
     */
    private static void cleanupProcesses(String... processNames) {
        for (String processName : processNames) {
            try {
                System.out.println("Attempting to terminate process: " + processName);
                Process process = Runtime.getRuntime().exec("taskkill /F /IM " + processName + " /T");
                process.waitFor();
                System.out.println("[OK] Terminated process: " + processName);
            } catch (Exception e) {
                System.out.println("[WARNING] Could not terminate process " + processName + ": " + e.getMessage());
            }
        }

        // Wait a bit for processes to fully terminate
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Clears Chrome's default user data directory
     */
    private static void clearChromeUserData() {
        try {
            String localAppData = System.getenv("LocalAppData");
            if (localAppData != null) {
                Path userDataPath = Paths.get(localAppData, "Google", "Chrome", "User Data");
                deleteDirectory(userDataPath.toFile());
                System.out.println("[OK] Cleared default Chrome user data directory: " + userDataPath);
            }
        } catch (Exception e) {
            System.out.println("[WARNING] Could not clear default Chrome user data directory: " + e.getMessage());
        }
    }

    /**
     * Recursively deletes a directory and its contents
     */
    private static void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }
}
