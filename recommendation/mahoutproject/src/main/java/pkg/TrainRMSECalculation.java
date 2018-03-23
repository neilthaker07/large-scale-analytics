package pkg;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class TrainRMSECalculation {

	public static void main(String[] args) throws TasteException, IOException {
		// TODO Auto-generated method stub

		DataModel model = new FileDataModel(
				new File(
						"/home/neil/Neil_Work/MS_SJSU/256_LA/assignments/hw4/train.dat"));

		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model)
					throws TasteException {

				UserSimilarity similarity = new EuclideanDistanceSimilarity(model);

				/*UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
				//UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
				//UserSimilarity similarity = new LogLikelihoodSimilarity(model);
				//UserSimilarity similarity = new TanimotoCoefficientSimilarity(model);
				//UserSimilarity similarity = new EuclideanDistanceSimilarity(model);
				//UserSimilarity similarity = new GenericUserSimilarity(model);
				//UserSimilarity similarity = new SpearmanCorrelationSimilarity(model);

				
				//UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);
				
				final UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
				
				List<RecommendedItem> recommendations = recommender.recommend(2, 3);
				for (RecommendedItem recommendation : recommendations) {
				  System.out.println(recommendation);
				}
				*/
				
				
				
				// Optimizer optimizer = new NonNegativeQuadraticOptimizer();
				UserNeighborhood neighborhood = new ThresholdUserNeighborhood(
						0.1, similarity, model);
				return new GenericUserBasedRecommender(model, neighborhood,
						similarity);
			}
		};

		RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
		double score = evaluator.evaluate(recommenderBuilder, null, model, 0.7,
				1.0);
		System.out.println("RMSE: " + score);

		/*
		 * RecommenderIRStatsEvaluator statsEvaluator = new
		 * GenericRecommenderIRStatsEvaluator(); IRStatistics stats =
		 * statsEvaluator.evaluate(recommenderBuilder, null, model, null, 10, 4,
		 * 0.7); // evaluate precision recall at 10
		 * 
		 * System.out.println("Precision: " + stats.getPrecision());
		 * System.out.println("Recall: " + stats.getRecall());
		 * System.out.println("F1 Score: " + stats.getF1Measure());
		 */

	}

}
