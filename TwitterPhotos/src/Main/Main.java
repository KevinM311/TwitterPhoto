package Main;

import twitter.Photos;

import java.util.ArrayList;

import Vision.Vision;

public class Main {

	public static void main(String[] args) throws Exception {
		String screenName = "NatGeo";
		String target = "dog";
		
		if(screenName==null) {
			screenName = Photos.enterUser();
		}
		
		ArrayList<String> urls = Photos.getImageURLs(screenName);
		ArrayList<String> ansURLs = new ArrayList<String>();
		
		ArrayList<String> ansURLs1 = new ArrayList<String>();
		
 		int index = 0;
		for(String url: urls) {
			System.out.println("=======================================");
			System.out.println(index);
			
			if(Vision.getPhotoTagsFromURL(url).contains(target)) {
				ansURLs.add(url);
			}
			if(Vision.getCaptionFromURL(url).contains(target)) {
				ansURLs1.add(url);
			}
			index++;
		}
		
		System.out.println(ansURLs);
		System.out.println(ansURLs1);
	}

}


