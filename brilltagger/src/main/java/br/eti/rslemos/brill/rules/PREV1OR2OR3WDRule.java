package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3WDRule extends AbstractRule implements SerializableAsBrillText  {
	public static final RuleFactory FACTORY1 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word_1 = context.getToken(-1).getWord();
			return new PREV1OR2OR3WDRule(from, to, word_1);
		}
		
	};

	public static final RuleFactory FACTORY2 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word_2 = context.getToken(-2).getWord();
			return new PREV1OR2OR3WDRule(from, to, word_2);
		}
		
	};

	public static final RuleFactory FACTORY3 = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word_3 = context.getToken(-3).getWord();
			return new PREV1OR2OR3WDRule(from, to, word_3);
		}
		
	};

	private final String prev1or2or3Word;

	public PREV1OR2OR3WDRule(String from, String to, String prev1or2or3Word) {
		super(from, to);
		
		this.prev1or2or3Word = prev1or2or3Word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word_1 = context.getToken(-1).getWord();
		String word_2 = context.getToken(-2).getWord();
		String word_3 = context.getToken(-3).getWord();
		
		return prev1or2or3Word != null 
		? (prev1or2or3Word.equals(word_1) | prev1or2or3Word.equals(word_2) | prev1or2or3Word.equals(word_3)) 
		: (word_1 == null | word_2 == null | word_3 == null);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		PREV1OR2OR3WDRule other = (PREV1OR2OR3WDRule) o;
		
		return prev1or2or3Word != null ? prev1or2or3Word.equals(other.prev1or2or3Word) : other.prev1or2or3Word == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 61;
		hashCode += prev1or2or3Word != null ? prev1or2or3Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + prev1or2or3Word;
	}
}