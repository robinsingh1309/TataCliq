import java.io.IOException;

import pagination.CliqPaginationByFile;

/**
 * 1st flow -> extract out all the urls of subCategories
 * 2nd flow -> run the headless browser(in my case, i am using Selenium) on the extracted urls from the above steps to get the API endpoints of the frontend url API
 * 3rd flow -> once the API endpoints are extracted from the urls by using the above two steps, we can now do our pagination and write the data into the file
 */
public class Demo {

	public static void main(String[] args) throws IOException, InterruptedException {

		
        /*
         * UrlConnect connect = new UrlConnect();
         * 
         * String response = connect.getJsonResponse(
         * "https://www.tatacliq.com/marketplacewebservices/v2/mpl/cms/desktopservice/header?format=json");
         * 
         * List<String> urlsList = CategoriesTabUrl.getAllSubCategoryUrls(response);
         * 
         * String filePath = "/home/robin/eclipse-workspace/TataCliq/src/tata_cliq_product_urls.csv";
         * 
         * try (FileWriter fileWriter = new FileWriter(filePath)) {
         * 
         * for (String url : urlsList) { fileWriter.append(url).append("\n"); }
         * 
         * System.out.println("URL CSV file written successfully to: " + filePath);
         * 
         * } catch (Exception e) { e.printStackTrace(); }
         */
		
		
			
		String filePathToReadSubCategoryUrl = "/home/robin/eclipse-workspace/TataCliq/Url_data/tata_cliq_sub_category_product_urls.csv";
		
		String excelDataFilePath = "/home/robin/eclipse-workspace/TataCliq/product_bean_data";
		
		CliqPaginationByFile cliqPaginationByFile = new CliqPaginationByFile();

		cliqPaginationByFile.getAllDataAndWriteExcel(filePathToReadSubCategoryUrl, excelDataFilePath);

		
	}

}
