package service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class CategoriesTabUrl {

    public static List<String> getAllSubCategoryUrls(String headerResponse) {
    	
        List<String> urlsList = new ArrayList<>();
        JSONObject responseObject = new JSONObject(headerResponse);

        JSONArray itemsArray = responseObject.optJSONArray("items");
        if (itemsArray == null || itemsArray.length() == 0) return urlsList;

        JSONObject firstItem = itemsArray.getJSONObject(0);
        JSONArray categoriesTabList = firstItem.optJSONArray("categoriesTabAZListComponent");
        
        if (categoriesTabList == null) return urlsList;

        for (int i = 0; i < categoriesTabList.length(); i++) {
        	
            JSONObject category = categoriesTabList.getJSONObject(i);

            // first-level subcategories
            JSONArray subCategories = category.optJSONArray("subCategories");
            
            if (subCategories != null) {
            	
                for (int j = 0; j < subCategories.length(); j++) {
                	
                    JSONObject subCategory = subCategories.getJSONObject(j);

                    // second-level subcategories (nested)
                    JSONArray nestedSubCategories = subCategory.optJSONArray("subCategories");
                    
                    if (nestedSubCategories != null) {
                    	
                        for (int k = 0; k < nestedSubCategories.length(); k++) {
                        	
                            JSONObject nestedSubCategory = nestedSubCategories.getJSONObject(k);
                            String webURL = nestedSubCategory.optString("webURL", "");
                            
                            if (!webURL.isEmpty()) {
                                urlsList.add(webURL);
                            }
                        }
                    }

                    // add webURL from first-level subCategory as well
                    String subCategoryUrl = subCategory.optString("webURL", "");
                    if (!subCategoryUrl.isEmpty()) {
                        urlsList.add(subCategoryUrl);
                    }
                }
            }

            // add webURL from top-level category
            String categoryUrl = category.optString("webURL", "");
            if (!categoryUrl.isEmpty()) {
                urlsList.add(categoryUrl);
            }
        }

        return urlsList;
    }
}
