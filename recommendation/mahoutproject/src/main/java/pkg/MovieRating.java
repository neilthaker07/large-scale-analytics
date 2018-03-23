package pkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class MovieRating
{

	public static void main(String[] args) {

		try {
			// Step 1:- Input CSV file (CSV file should be in userID, itemID,preference) format
			DataModel model = new FileDataModel(new File(("/home/neil/Neil_Work/MS_SJSU/256_LA/assignments/hw4/train.dat")));

			// Step 2:- Create UserSimilarity or ItemSimilarity Matrix
			ItemSimilarity similarity = new EuclideanDistanceSimilarity(model);
			//UserSimilarity similarity = new EuclideanDistanceSimilarity(model);

			// Step 3:- Create UserNeighbourHood object.
			//UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, model);
			
			// Step 4:- Create object of UserBasedRecommender or
			//final UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			
			final ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
			
			// Step 5:- Call the Generated Recommender in previous step to getting recommendation for particular user or Item
			String csvFile = "/home/neil/Neil_Work/MS_SJSU/256_LA/assignments/hw4/test.dat";
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ",";
	        List<String> op = new ArrayList<String>();
	        
	        try {

	            br = new BufferedReader(new FileReader(csvFile));
	            while ((line = br.readLine()) != null) {

	                String[] userMovie = line.split(cvsSplitBy);
	                
	                int user = Integer.parseInt(userMovie[0]);
	                int movie = Integer.parseInt(userMovie[1]);
	               
	                String rating = null;
	                boolean predicted = false;
	                
	                List<RecommendedItem> recommendations = recommender.recommend(user, 15877);
	    			for (RecommendedItem recommendation : recommendations) {
	    				if(recommendation.getItemID() == movie)
	    				{
	    					rating = String.valueOf(recommendation.getValue());
	    					predicted = true;
	    					break;
	    				}
	    			}
	    			
	    			if(!predicted)
	    			{
	    				rating = String.valueOf(2.5);
	    			}
	    			op.add(rating.toString());
	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        
	        FileWriter writer = new FileWriter("/home/neil/Neil_Work/MS_SJSU/256_LA/assignments/hw4/predict.dat"); 
	        for(String str: op) {
	          writer.write(str+"\n");
	        }
	        writer.close();

		} catch (Exception e) {
			System.out.println("There was an error.");
			e.printStackTrace();
		}
	}

}
