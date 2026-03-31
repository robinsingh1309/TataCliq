/**
 * The below program will throw Java Heap memory error if you are creating many objects
 * Due to which your program can get stop in the middle itself
 * so, it is better to freeup the objects once it's purpose is done
 */

/*
public class SeleniumDemo {

    public static void main(String[] args) {
        
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start(0); // 0 for random port
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        // Capture all request/response content
        proxy.enableHarCaptureTypes(
        		CaptureType.REQUEST_HEADERS, 
        		CaptureType.RESPONSE_HEADERS,
                CaptureType.REQUEST_CONTENT, 
                CaptureType.RESPONSE_CONTENT);
        
        proxy.newHar("MySite");

        // Set up ChromeOptions with proxy
        ChromeOptions options = new ChromeOptions();
        options.setProxy(seleniumProxy);
        options.addArguments("--ignore-certificate-errors");

        WebDriver driver = new ChromeDriver(options);

        String inputFilePath = "/home/robin/eclipse-workspace/TataCliq/Url_data/tata_cliq_product_urls.csv";
        String outputFilePath = "/home/robin/eclipse-workspace/TataCliq/Url_data/tata_cliq_sub_category_product_urls.csv";

        Set<String> uniqueUrls = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            
        	String line;
            
            while ( ( line = br.readLine() ) != null ) {
            	
            	// passing the url to the driver to navigate to the particular site
                driver.get(line);

                Thread.sleep(5000);

                proxy.getHar().getLog().getEntries().forEach(entry -> {
                	
                    HarRequest request = entry.getRequest();
                    
                    String url = request.getUrl();
                    
                    if (url.startsWith("https://searchbff.tatacliq.com/products/mpl/search")) {
                        uniqueUrls.add(url);
                    }
                    
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

      
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
        	
            for (String url : uniqueUrls) {
            	
                writer.write(url);
                writer.newLine();
                
            }
            
            System.out.println("Subcategory URLs CSV written successfully: " + outputFilePath);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
        proxy.stop();
    }
}
*/

