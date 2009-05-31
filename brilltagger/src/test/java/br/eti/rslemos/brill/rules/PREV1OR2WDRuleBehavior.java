package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREV1OR2WDRuleBehavior {
	private boolean matches(String prev1or2Word) {
		Context context = buildContext();
		
		Rule rule = new PREV1OR2WDRule(THIS_TAG, TO_TAG, prev1or2Word);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV3_WORD));
		assertFalse(matches(THIS_WORD));
		assertFalse(matches(NEXT1_WORD));
		assertFalse(matches(NEXT2_WORD));
		assertFalse(matches(NEXT3_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV2_WORD));
		assertTrue(matches(PREV1_WORD));
	}
	
	@Test
	public void shouldCreateRule() {
		RuleFactoryBehaviorUtils.createAndTest(PREV1OR2WDRule.FACTORY1);
		RuleFactoryBehaviorUtils.createAndTest(PREV1OR2WDRule.FACTORY2);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(PREV1OR2WDRule.FACTORY1);
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(PREV1OR2WDRule.FACTORY2);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		RuleFactoryBehaviorUtils.createAndTestBrillText(PREV1OR2WDRule.FACTORY1, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2WD " + PREV1_WORD);
		RuleFactoryBehaviorUtils.createAndTestBrillText(PREV1OR2WDRule.FACTORY2, 
				THIS_TAG + " " + TO_TAG + " PREV1OR2WD " + PREV2_WORD);
	}
}
