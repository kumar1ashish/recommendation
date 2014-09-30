package com.prediction.recommender;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * User based product recommendation system
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	DataModel model = new FileDataModel(new File("data/trdata.csv"));
    	UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
    	UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
    	ItemSimilarity sim = new LogLikelihoodSimilarity(model);
    	Recommender recommender = new GenericItemBasedRecommender(model,sim);
    	List<RecommendedItem> recommendations = recommender.recommend(2,3);
    	for (RecommendedItem recommendation : recommendations) {
    	  System.out.println(recommendation);
    	}

    }
}
