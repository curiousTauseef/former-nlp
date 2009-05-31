package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDAND2AFTRuleBehavior {
	private boolean matches(String word, String next2Word) {
		Context context = buildContext();
		
		Rule rule = new WDAND2AFTRule(THIS_TAG, TO_TAG, word, next2Word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV3_WORD, NEXT2_WORD));
		assertFalse(matches(PREV2_WORD, NEXT2_WORD));
		assertFalse(matches(PREV1_WORD, NEXT2_WORD));
		assertFalse(matches(NEXT1_WORD, NEXT2_WORD));
		assertFalse(matches(NEXT2_WORD, NEXT2_WORD));
		assertFalse(matches(NEXT3_WORD, NEXT2_WORD));
		
		assertFalse(matches(THIS_WORD, PREV3_WORD));
		assertFalse(matches(THIS_WORD, PREV2_WORD));
		assertFalse(matches(THIS_WORD, PREV1_WORD));
		assertFalse(matches(THIS_WORD,  THIS_WORD));
		assertFalse(matches(THIS_WORD, NEXT1_WORD));
		assertFalse(matches(THIS_WORD, NEXT3_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(THIS_WORD, NEXT2_WORD));
	}
	
	@Test
	public void shouldCreateRule() {
		RuleFactoryBehaviorUtils.createAndTest(WDAND2AFTRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleFactoryBehaviorUtils.createAndTestObjectSemantics(WDAND2AFTRule.FACTORY);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		RuleFactoryBehaviorUtils.createAndTestBrillText(WDAND2AFTRule.FACTORY, 
				THIS_TAG + " " + TO_TAG + " WDAND2AFT " + THIS_WORD + " " + NEXT2_WORD);
	}
}
