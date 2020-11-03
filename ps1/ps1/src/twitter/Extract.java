/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.time.Instant;
import twitter.Timespan;
import java.lang.StringBuilder;


/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        
        if (tweets.size() < 1) {
            throw new RuntimeException("Tweet list doesn't fit spec.");
        }
        
        // set the starting min/max
        Instant min = tweets.get(0).getTimestamp();
        Instant max = tweets.get(0).getTimestamp();
        
        // replace min and max time if min-er or max-er time is found
        for (int i = 1; i < tweets.size(); i++){
            
            Instant current = tweets.get(i).getTimestamp();
            if (current.isAfter(max)) {
                max = current;
                }
            if (current.isBefore(min)) {
                min = current;
                }
        }
                
        Timespan span = new twitter.Timespan(min, max);
                        
        return span;
        
    }
    
    /**
     * Find and return the next username from position at_char in a String text.
     * 
     * @param text of a tweet (string)
     *        at_char is the index from which to search the next username
     * @return the next username, converted to lowercase String. If none, returns
     * an empty String.
     */
    public static String getUsername(String text, int at_char) {
        
        String AcceptableChars = 
                "abcdefghijklmnopqrstuvwxyz"
              + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
              + "0123456789"
              + "-_" ;
        
        char char_preceding;
        char char_succeeding;

        if(at_char == -1){
            throw new RuntimeException("@ symbol doesn't appear in tweet.");
            
        } else if(at_char >= text.length()) {
            throw new RuntimeException("character index out of range of text length.");
            
        } else if(at_char == 0){
            // if it's the first character of the tweet,
            // we add an unacceptable preceding character to avoid errors
            char_preceding = '#';
            char_succeeding = text.charAt(at_char+1);
            
        } else if (at_char == text.length()-1) {
            // if it's the last character of the tweet,
            // we add an unacceptable succeeding character to avoid errors
            char_preceding = text.charAt(at_char-1);
            char_succeeding = '#';

        } else {
            // for all other character positions,
            // we just enter the true preceding/succeeding characters
            char_preceding = text.charAt(at_char-1);
            char_succeeding = text.charAt(at_char+1);
        }
        
        // test if it's a username - preceding char is invalid,
        // and succeeding char is valid
        
        if ((AcceptableChars.indexOf(char_preceding) == -1) &&
            (AcceptableChars.indexOf(char_succeeding) != -1) ){
                            
            // get the full string of the username
            StringBuilder username = new StringBuilder();
            
            // add characters to username while characters are valid
            at_char = at_char + 1;
            while ((at_char < text.length()) && 
                    (AcceptableChars.indexOf(text.charAt(at_char)) != -1)){
                username.append(text.charAt(at_char));
                at_char = at_char + 1;
            }
            
            return username.toString().toLowerCase();
            
        } else {
            return "";
        }
        
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec). The username-mention cannot
     *         be immediately preceded or followed by any character valid in a
     *         Twitter username. For this reason, an email address like
     *         bitdiddle@mit.edu does NOT contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        

        Set<String> UserList = new HashSet<String>();
        
        for (int i = 0; i < tweets.size(); i++){
                        
            String t = tweets.get(i).getText();
            
            int at_char = 0;
            at_char = t.indexOf('@', at_char);
            
            while (at_char < t.length()) {
                
                if(at_char == -1){
                    at_char = t.length();
                    continue;
                } else {
                    String username = getUsername(t, at_char);
                    // if not in list, add lowercase username into list
                    if(!UserList.contains(username) && username != ""){
                       UserList.add(username);
                    }
                    at_char = at_char + username.length() + 1;
                    at_char = t.indexOf('@', at_char);
                    
                }

        }
        
    }
        return UserList;

}
    
}