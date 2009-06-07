package br.eti.rslemos.brill;

import java.util.Queue;

import br.eti.rslemos.brill.RulesetTrainer.RuleSelectStrategy;
import br.eti.rslemos.brill.RulesetTrainer.Score;
import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;

public class ScoringRuleSelectStrategy implements RuleSelectStrategy {
	public static interface ScoringStrategy {
		void setTrainingContext(TrainingContext trainingContext);
		int compute(Rule rule, int positiveScore);
	}
	
	private final ScoringStrategy scoringStrategy;
	
	public ScoringRuleSelectStrategy(ScoringStrategy scoringStrategy) {
		this.scoringStrategy = scoringStrategy;
	}

	public void setTrainingContext(TrainingContext trainingContext) {
		scoringStrategy.setTrainingContext(trainingContext);
	}

	public Rule selectBestRule(Queue<Score> possibleRules) {
		Rule bestRule = null;
		int bestScore = 0;

		while(!possibleRules.isEmpty()) {
			Score entry = possibleRules.poll();
			
			Rule rule = entry.rule;
			int positiveScore = entry.getPositiveScore();

			if (positiveScore > bestScore) {
				int score = scoringStrategy.compute(rule, positiveScore);
	
				if (score > bestScore) {
					bestRule = rule;
					bestScore = score;
				}
			} else
				break; // cut
		}

		return bestRule;
	}

}
