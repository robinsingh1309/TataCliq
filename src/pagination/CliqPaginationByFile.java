package pagination;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import extract.DataTab;
import modal.CliqProduct;
import service.UrlConnect;

public class CliqPaginationByFile {

	private static final Logger logger = Logger.getLogger(CliqPaginationByFile.class.getName());
	
	private UrlConnect urlConnection;

	public CliqPaginationByFile() { urlConnection = new UrlConnect(); }

	
	
	public void getAllDataAndWriteCsv(String filePathName, String outputCsvFilePath) throws IOException {

        try (
        		BufferedReader reader = new BufferedReader(new FileReader(filePathName));
        		BufferedWriter writer = new BufferedWriter(new FileWriter(outputCsvFilePath))
        ) {
        	
        	// to keep track of how many urls have been visited from the file where the sub_categories urls are stored
        	int count = 1;
        	
            String line;

            writer.append("Brand,Product,ProductCategory,SellingPrice,MRP,Category\n");

            while ( (line = reader.readLine() ) != null) {

                int page = 0;
                int totalPages = 1;

                while (page < totalPages) {

                    try {
                    	
                        String categoryUrl = line.replaceAll("page=\\d+", "page=" + page);
                        logger.info("Fetching category URL: " + categoryUrl);

                        String categoryResponse = urlConnection.getJsonResponse(categoryUrl);
                        List<CliqProduct> pageProducts = DataTab.searchedResultData(categoryResponse);

                        if (pageProducts.isEmpty()) {
                            logger.info("No products found on page " + page + ". Stopping this category.");
                            break;
                        }

                        // write each product to CSV
                        for (CliqProduct product : pageProducts) {
                        	
                            writer.append(
                            		String.format("\"%s\",\"%s\",\"%s\",%.2f,%.2f,\"%s\"\n",
                                    product.getBrandName(),
                                    product.getProductName(),
                                    product.getProductCategory(),
                                    product.getTataCliqSellingPrice(),
                                    product.getOriginalMrp(),
                                    product.getType()
                            					)
                            			);
                        }

                        // cleaning up the resource
                        writer.flush();
                     
                        if (page == 0) {
                            totalPages = DataTab.countPages(categoryResponse);
                            logger.info("Total pages: " + totalPages);
                        }

                        page++;
                        sleepRandom();

                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "Error fetching data from URL: " + line + " page: " + page, e);
                        break; 
                    }
                }
                
                // it is just for debugging
                System.out.println("Url Count: " + count);
                count++;
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error reading file or writing CSV", e);
        }
    }

	
	private void sleepRandom() {
		try {
			Thread.sleep(500 + (int) (Math.random() * 2000));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
}