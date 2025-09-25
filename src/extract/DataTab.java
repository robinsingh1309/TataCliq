package extract;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import modal.CliqProduct;

public class DataTab {

    private static final Logger logger = Logger.getLogger(DataTab.class.getName());

    public static List<CliqProduct> searchedResultData(String response) {
    	
        List<CliqProduct> dataItemsForPage = new ArrayList<>();

        try {
        	
            JSONObject jsonResponseObject = new JSONObject(response);

            // Extract category safely
            String category = "Unknown";
            if (jsonResponseObject.has("seo")) {
            	
                JSONObject seoJsonObject = jsonResponseObject.getJSONObject("seo");
                
                if (seoJsonObject.has("breadcrumbs")) {
                	
                    JSONArray breadcrumbsJsonArray = seoJsonObject.getJSONArray("breadcrumbs");
                    
                    if (breadcrumbsJsonArray.length() > 0) {
                        category = breadcrumbsJsonArray.getJSONObject(0).optString("name", "Unknown");
                    }
                    
                }
            }

            // Check if searchresult exists
            if (!jsonResponseObject.has("searchresult")) {
                logger.warning("No search results found for this page. Skipping...");
                return dataItemsForPage; // empty list
            }

            
            JSONArray searchresultJsonArray = jsonResponseObject.getJSONArray("searchresult");

            for (int i = 0; i < searchresultJsonArray.length(); i++) {
            	
                JSONObject productObj = searchresultJsonArray.getJSONObject(i);

                String brandName = productObj.optString("brandname", "Unknown");
                String productName = productObj.optString("productname", "Unknown");
                String productCategory = productObj.optString("productCategoryType", "Unknown");

                double sellingPrice = 0.0;
                double mrpPrice = 0.0;

                if (productObj.has("price")) {
                	
                    JSONObject priceObject = productObj.getJSONObject("price");
                    
                    if (priceObject.has("sellingPrice")) {
                        sellingPrice = priceObject.getJSONObject("sellingPrice").optDouble("doubleValue", 0.0);
                    }
                    
                    if (priceObject.has("mrpPrice")) {
                        mrpPrice = priceObject.getJSONObject("mrpPrice").optDouble("doubleValue", 0.0);
                    }
                    
                }

                CliqProduct item = new CliqProduct(brandName, productName, productCategory, sellingPrice, mrpPrice, category);
                dataItemsForPage.add(item);
            }

            logger.info("Fetched " + dataItemsForPage.size() + " products for category: " + category);

        } catch (Exception e) {
            logger.severe("Error parsing JSON response: " + e.getMessage());
        }

        return dataItemsForPage;
    }

    public static int countPages(String response) {
        try {
            JSONObject jsonResponseObject = new JSONObject(response);
            if (jsonResponseObject.has("pagination")) {
                JSONObject paginationJsonObject = jsonResponseObject.getJSONObject("pagination");
                return paginationJsonObject.optInt("totalPages", 1);
            }
        } catch (Exception e) {
            logger.warning("Error fetching total pages: " + e.getMessage());
        }
        return 1; // default to 1 page if anything goes wrong
    }
}
