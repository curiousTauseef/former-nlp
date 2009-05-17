package br.eti.rslemos.brill.rules;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class PREVBIGRAMRuleBehavior {
	private boolean matches(String prev2Word, String prev1Word) {
		Context context = RuleContextMother.buildContext();
		
		Rule rule = new PREVBIGRAMRule(RuleContextMother.THIS_TAG, RuleContextMother.TO_TAG, prev2Word, prev1Word);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(RuleContextMother.PREV3_WORD, RuleContextMother.PREV1_WORD));
		assertFalse(matches(RuleContextMother.PREV1_WORD, RuleContextMother.PREV1_WORD));
		assertFalse(matches(RuleContextMother.THIS_WORD,  RuleContextMother.PREV1_WORD));
		assertFalse(matches(RuleContextMother.NEXT1_WORD, RuleContextMother.PREV1_WORD));
		assertFalse(matches(RuleContextMother.NEXT2_WORD, RuleContextMother.PREV1_WORD));
		assertFalse(matches(RuleContextMother.NEXT3_WORD, RuleContextMother.PREV1_WORD));
		
		assertFalse(matches(RuleContextMother.PREV2_WORD, RuleContextMother.PREV3_WORD));
		assertFalse(matches(RuleContextMother.PREV2_WORD, RuleContextMother.PREV2_WORD));
		assertFalse(matches(RuleContextMother.PREV2_WORD, RuleContextMother.THIS_WORD));
		assertFalse(matches(RuleContextMother.PREV2_WORD, RuleContextMother.NEXT1_WORD));
		assertFalse(matches(RuleContextMother.PREV2_WORD, RuleContextMother.NEXT2_WORD));
		assertFalse(matches(RuleContextMother.PREV2_WORD, RuleContextMother.NEXT3_WORD));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(RuleContextMother.PREV2_WORD, RuleContextMother.PREV1_WORD));
	}
}