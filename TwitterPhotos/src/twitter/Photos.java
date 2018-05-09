package twitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class Photos {
	private static String token = "988372037805723649-aw2HfO3uDGCNNlTKmeh2UMZZmMlcsP2";
	private static String tokenSecret = "Qcsh4eWLT4iUZ1Wl538eROwgvqpGyzat4FEGJkpwG59zh";
	
	public static void main(String[] params) throws Exception {
		
		String user = enterUser();
		ArrayList<String> answers = getImageURLs(user);
		
		for(String s : answers){
        	System.out.println(s);
        }
	}
	
	public static ArrayList<String> getImageURLs(String userScreenName) throws Exception {
		//authorise();
        // gets Twitter instance with default credentials
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("yJfIgBJQH9GdP8fLLuGq8uY1r")
		  .setOAuthConsumerSecret("SuO2z2aruSDhr2DdsxBR28FCuIz3XzR6Tc3dUNEIcsFnIYeMp5")
		  .setOAuthAccessToken(token)
		  .setOAuthAccessTokenSecret(tokenSecret)
		  .setIncludeEntitiesEnabled(true);
        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        ArrayList<MediaEntity> media = new ArrayList<MediaEntity>();
		try {
	        List<Status> statuses = new ArrayList<Status>();
	        
	        int pageno = 1;
        	
	        while (true) {
            	Paging page = new Paging(pageno++, 100);
                int size = statuses.size();
                statuses = twitter.getUserTimeline(userScreenName, page);
                if(size == statuses.size()) {
                	break;
                }
	        }
            System.out.println("Showing @" + userScreenName + "'s user photos.");

            for(Status s : statuses) {
            	media.addAll(Arrays.asList(s.getMediaEntities())); //get the media entities from the status	
            }
            
            ArrayList<String> mediaURLs = new ArrayList<String>();
            for(MediaEntity m : media){ //search trough your entities
            	mediaURLs.add(m.getMediaURL());
            }
            
            return mediaURLs;
            
		}catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
            return null;
        }
	}
	
	public static String enterUser() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter a username");
		return scanner.nextLine();
		
	}
	
	public static void authorise() throws Exception {
	    Twitter twitter = TwitterFactory.getSingleton();
	    twitter.setOAuthConsumer("yJfIgBJQH9GdP8fLLuGq8uY1r", "SuO2z2aruSDhr2DdsxBR28FCuIz3XzR6Tc3dUNEIcsFnIYeMp5");
	    RequestToken requestToken = twitter.getOAuthRequestToken();
	    AccessToken accessToken = null;
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    while (null == token) {
	      System.out.println("Open the following URL and grant access to your account:");
	      System.out.println(requestToken.getAuthorizationURL());
	      System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
	      String pin = br.readLine();
	      try{
	         if(pin.length() > 0){
	           accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			   storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
	         }else{
	           accessToken = twitter.getOAuthAccessToken();
			   storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
	         }
	      } catch (TwitterException te) {
	        if(401 == te.getStatusCode()){
	          System.out.println("Unable to get the access token.");
	        }else{
	          te.printStackTrace();
	        }
	      }
	    }
	}
	
	public static void storeAccessToken(long l, AccessToken accessToken){
	    token = accessToken.getToken();
	    tokenSecret = accessToken.getTokenSecret();
	  }
}
