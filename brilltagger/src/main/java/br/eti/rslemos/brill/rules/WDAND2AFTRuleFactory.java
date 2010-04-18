package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2AFTRuleFactory extends AbstractSingleRuleFactory {
	public static final RuleFactory INSTANCE = new WDAND2AFTRuleFactory();

	@Override
	public Rule createRule(Object from, Object to, Context context) {
		String word0 = context.getToken(0).getWord();
		String word2 = context.getToken(2).getWord();

		return new WDAND2AFTRule(from, to, word0, word2);
	}
}