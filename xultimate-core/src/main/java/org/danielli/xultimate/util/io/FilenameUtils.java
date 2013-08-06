package org.danielli.xultimate.util.io;

import org.apache.commons.io.IOCase;

/**
 * General filename and filepath manipulation utilities.
 * <p>
 * When dealing with filenames you can hit problems when moving from a Windows
 * based development machine to a Unix based production machine.
 * This class aims to help avoid those problems.
 * <p>
 * <b>NOTE</b>: You may be able to avoid using this class entirely simply by
 * using JDK {@link java.io.File File} objects and the two argument constructor
 * {@link java.io.File#File(java.io.File, java.lang.String) File(File,String)}.
 * <p>
 * Most methods on this class are designed to work the same on both Unix and Windows.
 * Those that don't include 'System', 'Unix' or 'Windows' in their name.
 * <p>
 * Most methods recognise both separators (forward and back), and both
 * sets of prefixes. See the javadoc of each method for details.
 * <p>
 * This class defines six components within a filename
 * (example C:\dev\project\file.txt):
 * <ul>
 * <li>the prefix - C:\</li>
 * <li>the path - dev\project\</li>
 * <li>the full path - C:\dev\project\</li>
 * <li>the name - file.txt</li>
 * <li>the base name - file</li>
 * <li>the extension - txt</li>
 * </ul>
 * Note that this class works best if directory filenames end with a separator.
 * If you omit the last separator, it is impossible to determine if the filename
 * corresponds to a file or a directory. As a result, we have chosen to say
 * it corresponds to a file.
 * <p>
 * This class only supports Unix and Windows style names.
 * Prefixes are matched as follows:
 * <pre>
 * Windows:
 * a\b\c.txt           --> ""          --> relative
 * \a\b\c.txt          --> "\"         --> current drive absolute
 * C:a\b\c.txt         --> "C:"        --> drive relative
 * C:\a\b\c.txt        --> "C:\"       --> absolute
 * \\server\a\b\c.txt  --> "\\server\" --> UNC
 *
 * Unix:
 * a/b/c.txt           --> ""          --> relative
 * /a/b/c.txt          --> "/"         --> absolute
 * ~/a/b/c.txt         --> "~/"        --> current user
 * ~                   --> "~/"        --> current user (slash added)
 * ~user/a/b/c.txt     --> "~user/"    --> named user
 * ~user               --> "~user/"    --> named user (slash added)
 * </pre>
 * Both prefix styles are matched always, irrespective of the machine that you are
 * currently running on.
 * <p>
 * Origin of code: Excalibur, Alexandria, Tomcat, Commons-Utils.
 *
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.io.FilenameUtils
 */
public class FilenameUtils {
	
    /**
     * Gets the extension of a filename.
     * <p>
     * This method returns the textual part of the filename after the last dot.
     * There must be no directory separator after the dot.
     * <pre>
     * foo.txt      --> "txt"
     * a/b/c.jpg    --> "jpg"
     * a/b.txt/c    --> ""
     * a/b/c        --> ""
     * </pre>
     * <p>
     * The output will be the same irrespective of the machine that the code is running on.
     *
     * @param filename the filename to retrieve the extension of.
     * @return the extension of the file or an empty string if none exists or {@code null}
     * if the filename is {@code null}.
     */
    public static String getExtension(String filename) {
    	 return org.apache.commons.io.FilenameUtils.getExtension(filename);
    }
    
    /**
     * Checks a filename to see if it matches the specified wildcard matcher,
     * always testing case-sensitive.
     * <p>
     * The wildcard matcher uses the characters '?' and '*' to represent a
     * single or multiple (zero or more) wildcard characters.
     * This is the same as often found on Dos/Unix command lines.
     * The check is case-sensitive always.
     * <pre>
     * wildcardMatch("c.txt", "*.txt")      --> true
     * wildcardMatch("c.txt", "*.jpg")      --> false
     * wildcardMatch("a/b/c.txt", "a/b/*")  --> true
     * wildcardMatch("c.txt", "*.???")      --> true
     * wildcardMatch("c.txt", "*.????")     --> false
     * </pre>
     * N.B. the sequence "*?" does not work properly at present in match strings.
     * 
     * @param filename  the filename to match on
     * @param wildcardMatcher  the wildcard string to match against
     * @return true if the filename matches the wilcard string
     * @see IOCase#SENSITIVE
     */
    public static boolean wildcardMatch(String filename, String wildcardMatcher) {
        return org.apache.commons.io.FilenameUtils.wildcardMatch(filename, wildcardMatcher, IOCase.SENSITIVE);
    }
}
