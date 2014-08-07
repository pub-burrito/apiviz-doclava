/*
 * JBoss, Home of Professional Open Source
 *
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * by the @author tags. See the COPYRIGHT.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.apiviz;

import static org.jboss.apiviz.Constant.TAG_CATEGORY;
import static org.jboss.apiviz.Constant.TAG_COMPOSED_OF;
import static org.jboss.apiviz.Constant.TAG_EXCLUDE;
import static org.jboss.apiviz.Constant.TAG_EXCLUDE_SUBTYPES;
import static org.jboss.apiviz.Constant.TAG_HAS;
import static org.jboss.apiviz.Constant.TAG_HIDDEN;
import static org.jboss.apiviz.Constant.TAG_INHERIT;
import static org.jboss.apiviz.Constant.TAG_LANDMARK;
import static org.jboss.apiviz.Constant.TAG_OWNS;
import static org.jboss.apiviz.Constant.TAG_STEREOTYPE;
import static org.jboss.apiviz.Constant.TAG_USES;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.SeeTag;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;

/**
 * @author The APIviz Project (apiviz-dev@lists.jboss.org)
 * @author Trustin Lee (tlee@redhat.com)
 *
 * @version $Rev$, $Date$
 *
 */
public class APIvizRootDoc implements RootDoc {

	private static final String TAGS_RESOURCE = "/org/jboss/apiviz/tags.txt";
	private static final File TAGS_FILE;
	
	static
	{
		URL resource = APIvizRootDoc.class.getResource( TAGS_RESOURCE );
		
		try
		{
			//persist resource to disk so we can refer to it via path
			InputStream tagsStream = resource.openStream();
			Path tagsPath = Files.createTempFile( TAGS_RESOURCE.replace( "/", "." ), null );
			
			TAGS_FILE = tagsPath.toFile();
			
			if ( TAGS_FILE.exists() )
			{
				TAGS_FILE.delete();
			}
			
			Files.copy( tagsStream, tagsPath );
		}
		catch ( IOException e )
		{
			throw new RuntimeException( "Could not read/write tags file at: " + TAGS_RESOURCE + " (" + resource + ")", e );
		}
	}
	
	private final RootDoc root;

    public APIvizRootDoc(RootDoc root) {
        this.root = root;
        
        root.printNotice( "Reading APIviz tags from: " + TAGS_FILE );
    }

    private static boolean isAboutApiVizTag(String msg) {
        return msg.indexOf(TAG_COMPOSED_OF + ' ') >= 0 ||
               msg.indexOf(TAG_HAS + ' ') >= 0 ||
               msg.indexOf(TAG_HIDDEN + ' ') >= 0 ||
               msg.indexOf(TAG_LANDMARK + ' ') >= 0 ||
               msg.indexOf(TAG_OWNS + ' ') >= 0 ||
               msg.indexOf(TAG_STEREOTYPE + ' ') >= 0 ||
               msg.indexOf(TAG_USES + ' ') >= 0 ||
               msg.indexOf(TAG_INHERIT + ' ') >= 0 ||
               msg.indexOf(TAG_EXCLUDE + ' ') >= 0 ||
               msg.indexOf(TAG_EXCLUDE_SUBTYPES + ' ') >= 0 ||
               msg.indexOf(TAG_CATEGORY + ' ') >= 0;
    }

    public void printWarning(SourcePosition arg0, String arg1) {
        if (!isAboutApiVizTag(arg1)) {
            root.printWarning(arg0, arg1);
        }
    }

    public void printWarning(String arg0) {
        if (!isAboutApiVizTag(arg0)) {
            root.printWarning(arg0);
        }
    }

    public ClassDoc[] classes() {
        return root.classes();
    }

    public ClassDoc classNamed(String arg0) {
        return root.classNamed(arg0);
    }

    public String commentText() {
        return root.commentText();
    }

    public int compareTo(Object arg0) {
        return root.compareTo(arg0);
    }

    public Tag[] firstSentenceTags() {
        return root.firstSentenceTags();
    }

    public String getRawCommentText() {
        return root.getRawCommentText();
    }

    public Tag[] inlineTags() {
        return root.inlineTags();
    }

    public boolean isAnnotationType() {
        return root.isAnnotationType();
    }

    public boolean isAnnotationTypeElement() {
        return root.isAnnotationTypeElement();
    }

    public boolean isClass() {
        return root.isClass();
    }

    public boolean isConstructor() {
        return root.isConstructor();
    }

    public boolean isEnum() {
        return root.isEnum();
    }

    public boolean isEnumConstant() {
        return root.isEnumConstant();
    }

    public boolean isError() {
        return root.isError();
    }

    public boolean isException() {
        return root.isException();
    }

    public boolean isField() {
        return root.isField();
    }

    public boolean isIncluded() {
        return root.isIncluded();
    }

    public boolean isInterface() {
        return root.isInterface();
    }

    public boolean isMethod() {
        return root.isMethod();
    }

    public boolean isOrdinaryClass() {
        return root.isOrdinaryClass();
    }

    public String name() {
        return root.name();
    }

    public String[][] options() {
    	int last = root.options().length;
    	
		String[][] options = Arrays.copyOf( root.options(), last + 1 );
		
		
		options[last] = new String[] { "-knowntags", TAGS_FILE.getAbsolutePath() };
    	
        return options;
    }

    public PackageDoc packageNamed(String arg0) {
        return root.packageNamed(arg0);
    }

    public SourcePosition position() {
        return root.position();
    }

    public void printError(SourcePosition arg0, String arg1) {
        root.printError(arg0, arg1);
    }

    public void printError(String arg0) {
        root.printError(arg0);
    }

    public void printNotice(SourcePosition arg0, String arg1) {
        root.printNotice(arg0, arg1);
    }

    public void printNotice(String arg0) {
        root.printNotice(arg0);
    }

    public SeeTag[] seeTags() {
        return root.seeTags();
    }

    public void setRawCommentText(String arg0) {
        root.setRawCommentText(arg0);
    }

    public ClassDoc[] specifiedClasses() {
        return root.specifiedClasses();
    }

    public PackageDoc[] specifiedPackages() {
        return root.specifiedPackages();
    }

    public Tag[] tags() {
        return root.tags();
    }

    public Tag[] tags(String arg0) {
        return root.tags(arg0);
    }
}
