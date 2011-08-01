package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTTAGRuleBehavior {
	private boolean matches(Object nextObject) {
		Context context = buildContext();
		
		Rule rule = NEXTTAGRuleFactory.INSTANCE.createRule(THIS_TAG, TO_TAG, nextObject);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_TAG));
		assertFalse(matches(PREV3_TAG));
		assertFalse(matches(PREV2_TAG));
		assertFalse(matches(PREV1_TAG));
		assertFalse(matches(THIS_TAG));
		assertFalse(matches(NEXT2_TAG));
		assertFalse(matches(NEXT3_TAG));
		assertFalse(matches(NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(NEXT1_TAG));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(NEXTTAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(NEXTTAGRuleFactory.INSTANCE.createRule(THIS_TAG, THIS_TAG, NEXT1_TAG), F, F, F, F, T, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(NEXTTAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(NEXTTAGRuleFactory.INSTANCE);
	}
	
	@Test
	public void shouldBeSerializableToBrillString() {
		createAndTestBrillString(NEXTTAGRuleFactory.INSTANCE, 
				THIS_TAG + " " + TO_TAG + " NEXTTAG " + NEXT1_TAG);
	}
}
