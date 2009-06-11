package br.eti.rslemos.brill;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.commons.lang.ObjectUtils;

import br.eti.rslemos.brill.RuleBasedTagger.BufferingContext;
import br.eti.rslemos.brill.rules.RuleCreationException;
import br.eti.rslemos.brill.rules.RuleFactory;

public class RulesetTrainer {
	private final Tagger baseTagger;

	private final List<RuleFactory> ruleFactories;
	private final int threshold;

	public RulesetTrainer(Tagger baseTagger, List<RuleFactory> ruleFactories) {
		this(baseTagger, ruleFactories, 1);
	}

	public RulesetTrainer(Tagger baseTagger, List<RuleFactory> ruleFactories, int threshold) {
		this.baseTagger = baseTagger;
		this.ruleFactories = ruleFactories;
		this.threshold = threshold;
	}

	private TrainingContext createTrainingContext(List<List<Token>> proofCorpus) {
		return new TrainingContext(proofCorpus);
	}

	public synchronized RuleBasedTagger train(List<List<Token>> proofCorpus) {
		TrainingContext trainingContext = createTrainingContext(proofCorpus);
		
		trainingContext.applyBaseTagger();
		List<Rule> rules = trainingContext.discoverRules();

		return new RuleBasedTagger(baseTagger, rules);
	}

	public class TrainingContext {

		public final List<List<Token>> proofCorpus;
		public final BufferingContext[] trainingCorpus;
		
		private int errorCount;

		public TrainingContext(List<List<Token>> proofCorpus) {
			this.proofCorpus = proofCorpus;
			this.trainingCorpus = new BufferingContext[proofCorpus.size()];
		}

		private void applyBaseTagger() {

			for (int i = 0; i < trainingCorpus.length; i++) {
				List<Token> proofSentence = proofCorpus.get(i);

				Token[] baseTaggedSentence = new DefaultToken[proofSentence.size()];
				for (int j = 0; j < baseTaggedSentence.length; j++) {
					baseTaggedSentence[j] = new DefaultToken(proofSentence.get(j).getWord());
				}

				baseTagger.tagSentence(Arrays.asList(baseTaggedSentence));
				trainingCorpus[i] = RuleBasedTagger.prepareContext(baseTaggedSentence);
			}
		}

		public List<Rule> discoverRules() {
			this.errorCount = countErrors();
			
			LinkedList<Rule> rules = new LinkedList<Rule>();

			boolean shouldTryMore;
			ScoreBoard board = new ScoreBoard();
			do {
				shouldTryMore = false;
				board.newRound();
				
				produceAllPossibleRules(board);
				
				Rule bestRule = selectBestRule(board.getRulesByPriority());

				if (bestRule != null) {
					applyRule(bestRule);

					if (shouldTryMore = updateAndTest()) {
						rules.add(bestRule);
						for (Iterator<Rule> iterator = board.iterator(); iterator.hasNext();) {
							Rule rule = iterator.next();
							if (rule == bestRule)
								iterator.remove();
							else {
								if (rule.firingDependsOnTag(bestRule.getFrom()) || rule.firingDependsOnTag(bestRule.getTo()))
									iterator.remove();
							}
						}
					}
				}
			} while (shouldTryMore);

			return rules;
		}

		private void applyRule(Rule bestRule) {
			for (BufferingContext trainingSentence : trainingCorpus)
				RuleBasedTagger.applyRule(trainingSentence, bestRule);
		}

		private void produceAllPossibleRules(ScoreBoard board) {
			int i = 0;
			for (List<Token> proofSentence : proofCorpus) {
				BufferingContext trainingSentence = trainingCorpus[i++];

				try {
					for (Token proofToken : proofSentence) {
						produceAllPossibleRules(board, proofToken, trainingSentence);
					}
				} finally {
					trainingSentence.reset();
				}
			}
		}

		private void produceAllPossibleRules(ScoreBoard board, Token proofToken, BufferingContext trainingSentence) {
			Token trainingToken = trainingSentence.next();

			if (!ObjectUtils.equals(proofToken.getTag(), trainingToken.getTag())) {
				Collection<Rule> localPossibleRules = produceAllPossibleRules(trainingSentence, proofToken);
				
				for (Rule localPossibleRule : localPossibleRules) {
					board.addTruePositive(localPossibleRule);
				}
			}
		}
		
		private Collection<Rule> produceAllPossibleRules(Context context, Token target) {
			try {
				Rule[] rules = new Rule[ruleFactories.size()];

				int i = 0;
				for (RuleFactory factory : ruleFactories)
					rules[i++] = factory.create(context, target);

				return new LinkedHashSet<Rule>(Arrays.asList(rules));
			} catch (RuleCreationException e) {
				throw new RuntimeException("Error creating rule", e);
			}
		}

		private int countErrors() {
			int errorCount = 0;

			int i = 0;
			for (List<Token> proofSentence : proofCorpus) {
				Context trainingSentence = trainingCorpus[i++];

				errorCount = countErrors(proofSentence, trainingSentence, errorCount);
			}

			return errorCount;
		}

		private int countErrors(List<Token> proofSentence, Context trainingSentence, int errorCount) {
			try {
				for (Token proofToken : proofSentence) {
					Token trainingToken = trainingSentence.next();

					if (!ObjectUtils.equals(proofToken.getTag(), trainingToken.getTag()))
						errorCount++;
				}
			} finally {
				trainingSentence.reset();
			}
			
			return errorCount;
		}

		private boolean updateAndTest() {
			int errorCount = countErrors();
			try {
				return !((this.errorCount - errorCount) < threshold);
			} finally {
				this.errorCount = errorCount;
			}
		}

		private Rule selectBestRule(Queue<Score> possibleRules) {
			Rule bestRule = null;
			int bestScore = 0;

			while(!possibleRules.isEmpty()) {
				Score entry = possibleRules.poll();
				
				if (entry.getScore() > bestScore) {
					computeNegativeScore(entry);
		
					if (entry.getScore() > bestScore) {
						bestRule = entry.rule;
						bestScore = entry.getScore();
					}
				} else
					break; // cut
			}

			return bestRule;
		}

		private void computeNegativeScore(Score entry) {
			if (!entry.negativeMatchesComputed()) {
				entry.dec();
				
				int i = 0;
				for (List<Token> proofSentence : proofCorpus) {
					BufferingContext trainingSentence = trainingCorpus[i++];
					computeNegativeScore(entry, proofSentence, trainingSentence);
				}
			}
		}

		private void computeNegativeScore(Score score, List<Token> proofSentence, BufferingContext trainingSentence) {
			try {
				for (Token proofToken : proofSentence) {
					trainingSentence.next();

					computeNegativeScore(score, proofToken, trainingSentence);
				}
			} finally {
				trainingSentence.reset();
			}
		}

		private void computeNegativeScore(Score score, Token proofToken, BufferingContext trainingSentence) {
			Rule rule = score.rule;

			if (rule.matches(trainingSentence))
				if (ObjectUtils.equals(rule.getFrom(), proofToken.getTag()))
					score.dec();
		}


	}
	
	public static class Score implements Comparable<Score> {
		public final Object roundCreated;
		public Object roundComputed;
		
		public final Rule rule;

		private int positiveMatches = 0;
		private int negativeMatches = -1;
		
		protected Score(Object roundCreated, Rule rule) {
			this.roundCreated = roundCreated;
			this.rule = rule;
		}
		
		public void inc() {
			positiveMatches++;
		}
		
		public void dec() {
			negativeMatches++;
		}
		
		public boolean negativeMatchesComputed() {
			return negativeMatches != -1;
		}
		
		public int getScore() {
			return positiveMatches - negativeMatches;
		}

		public int compareTo(Score o) {
			return o.getScore() - getScore();
		}
	}

	public static class ScoreBoard {
		private final Map<Rule, Score> rules = new HashMap<Rule, Score>();
		private Object round;
		
		public void addTruePositive(Rule rule) {
			Score score = rules.get(rule);
			
			if (score == null) {
				score = new Score(round, rule);
				rules.put(rule, score);
			}
			
			if (round == score.roundCreated)
				score.inc();
		}

		public Iterator<Rule> iterator() {
			return rules.keySet().iterator();
		}
		
		public Object newRound() {
			return round = new Object();
		}

		public Queue<Score> getRulesByPriority() {
			return new PriorityQueue<Score>(rules.values());
		}		
	}
}
