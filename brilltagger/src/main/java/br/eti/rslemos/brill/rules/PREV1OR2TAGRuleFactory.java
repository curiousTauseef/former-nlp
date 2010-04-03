package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Tag;

public class PREV1OR2TAGRuleFactory extends AbstractRuleFactory {
	
	public Collection<Rule> create(Tag from, Tag to, Context context) {
		Tag tag_1 = context.getToken(-1).getTag();
		Tag tag_2 = context.getToken(-2).getTag();

		return Arrays.<Rule> asList(
				new PREV1OR2TAGRule(from, to, tag_1),
				new PREV1OR2TAGRule(from, to, tag_2)
		);
	}
}