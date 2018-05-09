package Vision;
//This sample uses the Apache HTTP client library(org.apache.httpcomponents:httpclient:4.2.4)
//and the org.json library (org.json:json:20170516).

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Vision {
	// ****************
	// * Update or verify the following values. *
	// ****************
	
	// Replace the subscriptionKey string value with your valid subscription key.
	public static final String subscriptionKey = "c46d684289c447fdbfffe7eee5308f0d";
	
	// Replace or verify the region.
	//
	// You must use the same region in your REST API call as you used to obtain your subscription keys.
	// For example, if you obtained your subscription keys from the westus region, replace
	// "westcentralus" in the URI below with "westus".
	//
	// NOTE: Free trial subscription keys are generated in the westcentralus region, so if you are using
	// a free trial subscription key, you should not need to change this region.
	public static final String uriBase = "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze";
	
	public static void main(String[] args) {
		System.out.println(getCaptionFromURL("https://pbs.twimg.com/media/Dbe73AeWAAIXGok.jpg:large"));
	}
	public static ArrayList<String> getPhotoTagsFromURL(String url) {
	   HttpClient httpclient = new DefaultHttpClient();
	
	   try {
	       URIBuilder builder = new URIBuilder(uriBase);
	
	       // Request parameters. All of them are optional.
	       builder.setParameter("visualFeatures","Categories,Tags,Description,Faces,ImageType,Color,Adult");
	       builder.setParameter("details", "Celebrities,Landmarks");        
	       builder.setParameter("language", "en");
	
	       // Prepare the URI for the REST API call.            
	       URI uri = builder.build();
	       HttpPost request = new HttpPost(uri);
	
	       // Request headers.
	       request.setHeader("Content-Type", "application/json");
	       request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
	
	       // Request body.
	       StringEntity reqEntity = new StringEntity("{\"url\":\""
	       		+ url + "\"}");
	       request.setEntity(reqEntity);
	
	       // Execute the REST API call and get the response entity.
	       HttpResponse response = httpclient.execute(request);
	       HttpEntity entity = response.getEntity();
	
	       if (entity != null) {
	           // Format and display the JSON response.
	           String jsonString = EntityUtils.toString(entity);
	           JSONObject json = new JSONObject(jsonString);
	           //System.out.println("REST Response:\n");
	           System.out.println(json.toString(1));
	           
	           
	          //JSONArray ja = (json.getJSONObject("description").getJSONArray("captions"));
	          // System.out.println(ja.getJSONObject(0).get("text"));
	          // System.out.println(json.getJSONObject("description").getJSONArray("captions").getJSONObject(0).get("text"));
	          JSONArray ja = json.getJSONObject("description").getJSONArray("tags");
	          
	          ArrayList<String> answer = new ArrayList<String>();
	          for(Object s: ja) {
	        	  answer.add(s.toString());
	          }
	          return answer;
	       }
	   }
	   catch (Exception e)   {
	       // Display error message.
	       System.out.println(e.getMessage());
	   }
	   return new ArrayList<String>();
	}
	public static String getCaptionFromURL(String url) {
		   HttpClient httpclient = new DefaultHttpClient();
		
		   try {
		       URIBuilder builder = new URIBuilder(uriBase);
		
		       // Request parameters. All of them are optional.
		       builder.setParameter("visualFeatures","Categories,Tags,Description,Faces,ImageType,Color,Adult");
		       builder.setParameter("details", "Celebrities,Landmarks");        
		       builder.setParameter("language", "en");
		
		       // Prepare the URI for the REST API call.            
		       URI uri = builder.build();
		       HttpPost request = new HttpPost(uri);
		
		       // Request headers.
		       request.setHeader("Content-Type", "application/json");
		       request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
		
		       // Request body.
		       StringEntity reqEntity = new StringEntity("{\"url\":\""
		       		+ url + "\"}");
		       request.setEntity(reqEntity);
		
		       // Execute the REST API call and get the response entity.
		       HttpResponse response = httpclient.execute(request);
		       HttpEntity entity = response.getEntity();
		
		       if (entity != null) {
		           // Format and display the JSON response.
		           String jsonString = EntityUtils.toString(entity);
		           JSONObject json = new JSONObject(jsonString);
		           //System.out.println("REST Response:\n");
		           System.out.println(json.toString(1));
		           
		           
		          //JSONArray ja = (json.getJSONObject("description").getJSONArray("captions"));
		          // System.out.println(ja.getJSONObject(0).get("text"));
		          // System.out.println(json.getJSONObject("description").getJSONArray("captions").getJSONObject(0).get("text"));
		          
		          return json.getJSONObject("description").getJSONArray("captions").getJSONObject(0).get("text").toString();
		       }
		   }
		   catch (Exception e)   {
		       // Display error message.
		       System.out.println(e.getMessage());
		   }
		   return "";
		}
}