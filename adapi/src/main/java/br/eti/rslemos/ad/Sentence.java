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

import java.util.Iterator;

public class Sentence implements Iterable<Analysis>, Skippable {

	private final String id;
	private final String ref;
	private final String source;
	private final String text;

	private final  Iterator<Analysis> analyses;

	Sentence(final ADCorpus corpus) {
		corpus.assertLineStartsWith("<s ");

		// <s id="1" ref="CF1000-1" source="CETENFolha id=1000 cad=Esporte sec=des sem=94a">
		// <s id="63954" ref="1001.porto-poesia=removeme=-2 a poesia toma porto-alegre=removeme=-1" source="SELVA 1001.porto-poesia=removeme=-2 a poesia toma porto-alegre=removeme=">
		
		String line = corpus.line.substring("<s ".length()).trim();

		String[] parts;
		
		parts = line.split("=", 2);
		corpus.assertBoolean(parts[0].equals("id"));
		corpus.assertBoolean(parts[1].startsWith("\""));
		parts = parts[1].split("\"", 3);
		id = parts[1];
		line = parts[2].trim();

		parts = line.split("=", 2);
		corpus.assertBoolean(parts[0].equals("ref"));
		corpus.assertBoolean(parts[1].startsWith("\""));
		parts = parts[1].split("\"", 3);
		ref = parts[1];
		line = parts[2].trim();

		parts = line.split("=", 2);
		corpus.assertBoolean(parts[0].equals("source"));
		corpus.assertBoolean(parts[1].startsWith("\""));
		parts = parts[1].split("\"", 3);
		source = parts[1];
		line = parts[2].trim();

		corpus.assertBoolean(line.equals(">"));
		
		corpus.readNextLine();
		
		// SOURCE: ref="CF1000-1" source="CETENFolha id=1000 cad=Esporte sec=des sem=94a"
		if (corpus.line.equals("SOURCE: ref=\"" + ref + "\" source=\"" + source + "\"")) {
			corpus.readNextLine();
			
			// CF1000-1 Consolação
			text = corpus.line.split(" ", 2)[1].trim();
			
			corpus.readNextLine();
		} else {
			text = null;
		}
		
		analyses = new AnalysisIterator(corpus);
	}

	public String getId() {
		return id;
	}

	public String getRef() {
		return ref;
	}

	public String getSource() {
		return source;
	}

	public String getText() {
		return text;
	}

	public Iterator<Analysis> analyses() {
		return analyses;
	}

	public Iterator<Analysis> iterator() {
		return analyses();
	}

	public void skip() {
		while(analyses.hasNext())
			analyses.next().skip();
	}

	private static final class AnalysisIterator extends BaseIterator<Analysis> {
		private AnalysisIterator(ADCorpus corpus) {
			super(corpus);
		}

		@Override
		protected void tail() {
			if (corpus.line.equals(""))
				corpus.readNextLine();
			
			corpus.assertLineEquals("</s>");
			corpus.readNextLine();
		}

		@Override
		protected boolean testForNext() {
			return corpus.line.startsWith("A");
		}

		@Override
		protected Analysis buildNext() {
			return new Analysis(corpus);
		}
	}

}
