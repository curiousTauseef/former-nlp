package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDPREVTAGRuleBehavior {
	private boolean matches(Object prevObject, String word) {
		Context context = buildContext();
		
		Rule rule = new WDPREVTAGRule(THIS_TAG, TO_TAG, prevObject, word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV1_TAG, PREV4_WORD));
		assertFalse(matches(PREV1_TAG, PREV3_WORD));
		assertFalse(matches(PREV1_TAG, PREV2_WORD));
		assertFalse(matches(PREV1_TAG, PREV1_WORD));
		assertFalse(matches(PREV1_TAG, NEXT1_WORD));
		assertFalse(matches(PREV1_TAG, NEXT2_WORD));
		assertFalse(matches(PREV1_TAG, NEXT3_WORD));
		assertFalse(matches(PREV1_TAG, NEXT4_WORD));
		
		assertFalse(matches(PREV4_TAG, THIS_WORD));
		assertFalse(matches(PREV3_TAG, THIS_WORD));
		assertFalse(matches(PREV2_TAG, THIS_WORD));
		assertFalse(matches(THIS_TAG,  THIS_WORD));
		assertFalse(matches(NEXT1_TAG, THIS_WORD));
		assertFalse(matches(NEXT2_TAG, THIS_WORD));
		assertFalse(matches(NEXT3_TAG, THIS_WORD));
		assertFalse(matches(NEXT4_TAG, THIS_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(PREV1_TAG, THIS_WORD));
	}

	@Test
	public void shouldDependOnFromObject() {
		createAndTestBasicDependency(WDPREVTAGRule.FACTORY);
	}
	
	@Test
	public void shouldDependOnContextObject() {
		testDependency(new WDPREVTAGRule(THIS_TAG, THIS_TAG, PREV1_TAG, THIS_WORD), F, F, F, T, F, F, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(WDPREVTAGRule.FACTORY);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(WDPREVTAGRule.FACTORY);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(WDPREVTAGRule.FACTORY, 
				THIS_TAG + " " + TO_TAG + " WDPREVTAG " + PREV1_TAG + " " + THIS_WORD);
	}
}
