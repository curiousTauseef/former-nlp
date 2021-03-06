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
package br.eti.rslemos.ad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class ADCorpus implements Iterable<Extract> {

	private final BufferedReader input;
	private int lineNumber;
	String line;
	
	private Iterator<Extract> extracts;
	
	public ADCorpus(Reader input) {
		this.input = new BufferedReader(input);
		readNextLine();

		extracts = new ExtractIterator(this);
	}

	final void readNextLine() {
		try {
			do {
				line = input.readLine();
				lineNumber++;
			} while (line != null && ( 
						line.equals("<s frag>")
						|| line.startsWith("### ")
						|| (line.startsWith("<lixo") && line.endsWith(">"))
						|| line.equals("</situacao>")
						|| (line.startsWith("<li>") && line.endsWith("</li>"))
					));
		} catch (IOException e) {
			line = null;
		}
		if (line == null)
			line = "";
	}
	
	void assertLineStartsWith(String expected) {
		assert line.startsWith(expected) : "At line " + lineNumber + ": expected '" + expected + "...'; found '" + line + "'";
	}

	void assertLineEquals(String expected) {
		assert line.equals(expected) : "At line " + lineNumber + ": expected '" + expected + "'; found '" + line + "'";
	}
	
	void assertLineContains(String expected) {
		assert line.contains(expected) : "At line " + lineNumber + ": expected '..." + expected + "...'; found '" + line + "'";
	}

	void assertBoolean(boolean b) {
		assert b : "At line " + lineNumber;
	}

	public Iterator<Extract> extracts() {
		return extracts;
	}

	public Iterator<Extract> iterator() {
		return extracts();
	}

	private static final class ExtractIterator extends BaseIterator<Extract> {
		private ExtractIterator(ADCorpus corpus) {
			super(corpus);
		}
		
		@Override
		protected void tail() {
			try {
				corpus.input.close();
			} catch (IOException e) {
			}
		}

		@Override
		protected boolean testForNext() {
			return corpus.line.startsWith("<ext");
		}

		@Override
		protected Extract buildNext() {
			return new Extract(corpus);
		}
	}

}
