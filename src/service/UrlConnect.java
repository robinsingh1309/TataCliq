package service;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class UrlConnect {

	public String getJsonResponse(String webUrl) throws IOException 
	{
		
		Response response = Jsoup.connect(webUrl)
		        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36")
		        .referrer("https://www.tatacliq.com/")
		        .header("content-type", "application/json")
		        .timeout(10000)
		        .ignoreContentType(true)
		        .method(Connection.Method.GET)
		        .execute();
		
		return response.body();
		
	}
	
}
