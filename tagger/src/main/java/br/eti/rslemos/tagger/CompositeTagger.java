/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.tagger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ForwardingMap;

public class CompositeTagger implements Tagger, Serializable {

	private static final long serialVersionUID = -8583142580025744638L;

	private Tagger[] taggers;

	public CompositeTagger(Tagger... taggers) {
		this.taggers = taggers;
	}

	public void tag(Sentence sentence) {
		sentence = wrapSentence(sentence);

		for (Tagger tagger : taggers) {
			tagger.tag(sentence);
		}
	}

	private Sentence wrapSentence(Sentence sentence) {
		ArrayList<Token> wrappedSentence = new ArrayList<Token>(sentence.size());
		
		for (Token token : sentence) {
			wrappedSentence.add(new FilteringToken(token));
		}
		
		return new DefaultSentence(wrappedSentence);
	}
	
	public Tagger[] getTaggers() {
		return taggers;
	}

	public void setTaggers(Tagger[] taggers) {
		this.taggers = taggers;
	}

	private static final class FilteringToken extends ForwardingMap<String, Object> implements Token {
		private final Token token;
		private Set<String> alreadySet = new HashSet<String>();

		private FilteringToken(Token token) {
			this.token = token;
		}

		public Object put(String name, Object value) {
			if (!alreadySet.contains(name)) {
				alreadySet.add(name);
				// what the heck? this should be really
				// return super.put(name, value);
				// but mockito was choking on it, don't know why; only G*d knows.
				// since these are equivalent, I'll just move on.
				return token.put(name, value);
			} else
				return get(name);
		}

		@Override
		protected Map<String, Object> delegate() {
			return token;
		}
	}


}
