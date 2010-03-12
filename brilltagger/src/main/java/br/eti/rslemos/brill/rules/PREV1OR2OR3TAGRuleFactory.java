package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2OR3TAGRuleFactory<T> extends AbstractRuleFactory<T> {
	public Collection<Rule<T>> create(T from, T to, Context<T> context) {
		T tag_1 = context.getToken(-1).getTag();
		T tag_2 = context.getToken(-2).getTag();
		T tag_3 = context.getToken(-3).getTag();

		return Arrays.<Rule<T>> asList(new PREV1OR2OR3TAGRule<T>(from, to,
				tag_1), new PREV1OR2OR3TAGRule<T>(from, to, tag_2),
				new PREV1OR2OR3TAGRule<T>(from, to, tag_3));
	}
}