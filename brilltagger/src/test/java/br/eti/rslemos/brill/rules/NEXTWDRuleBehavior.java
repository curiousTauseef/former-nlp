package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTWDRuleBehavior {
	private boolean matches(String nextWord) {
		Context context = buildContext();
		
		Rule rule = new NEXTWDRule(THIS_TAG, TO_TAG, nextWord);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_WORD));
		assertFalse(matches(PREV3_WORD));
		assertFalse(matches(PREV2_WORD));
		assertFalse(matches(PREV1_WORD));
		assertFalse(matches(THIS_WORD));
		assertFalse(matches(NEXT2_WORD));
		assertFalse(matches(NEXT3_WORD));
		assertFalse(matches(NEXT4_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(NEXT1_WORD));
	}

	@Test
	public void shouldDependOnFromTag() {
		RuleBehaviorUtils.createAndTestBasicDependency(NEXTWDRule.FACTORY);
	}
	
	@Test
	public void shouldDependOnContextTag() {
		RuleBehaviorUtils.createAndTestContextIndependency(NEXTWDRule.FACTORY);
	}

	@Test
	public void shouldCreateRule() {
		RuleBehaviorUtils.createAndTestMatchability(NEXTWDRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleBehaviorUtils.createAndTestObjectSemantics(NEXTWDRule.FACTORY);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		RuleBehaviorUtils.createAndTestBrillText(NEXTWDRule.FACTORY, 
				THIS_TAG + " " + TO_TAG + " NEXTWD " + NEXT1_WORD);
	}
}
