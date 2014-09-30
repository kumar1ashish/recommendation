package com.prediction.recommender;

import java.io.*;
import java.util.*;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.neighborhood.*;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;

public class GenericUserBasedRecommender1 {

	public static void main(String[] args) throws Exception {
		// Create a data source from the CSV file
		File userPreferencesFile = new File("data/dataset1.csv");
		DataModel dataModel = new FileDataModel(userPreferencesFile);

		UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
		UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(10, userSimilarity, dataModel);

		// Create a generic user based recommender with the dataModel, the userNeighborhood and the userSimilarity
		Recommender genericRecommender =  new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);

		// Recommend 5 items for each user
		
		for (LongPrimitiveIterator iterator = dataModel.getUserIDs(); iterator.hasNext();)
		{
			long userId = iterator.nextLong();

			// Generate a list of 5 recommendations for the user
			List<RecommendedItem> itemRecommendations = genericRecommender.recommend(userId, 5);

			System.out.format("User Id: %d%n", userId);

			if (itemRecommendations.isEmpty())
			{
				System.out.println("No recommendations for this user.");
			}
			else
			{
				// Display the list of recommendations
				for (RecommendedItem recommendedItem : itemRecommendations)
				{
					System.out.format("Recommened Item Id %d. Strength of the preference: %f%n", recommendedItem.getItemID(), recommendedItem.getValue());
				}
			}
		}
	}
}
