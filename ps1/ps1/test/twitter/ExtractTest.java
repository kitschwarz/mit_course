/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here. See the
     * ic03-testing exercise for examples of what a testing strategy comment
     * looks like. Make sure you have partitions.
     * 
     * Partitions for timespan:
     * later date first
     * later date after
     * single tweet
     * two tweets
     * multiple tweets
     * contains two tweets with the same date
     * 
     * Partitions for getusername:
     * no tweets have username
     * all tweets have username
     * some tweets have username
     * 
     * within this space:
     * username at start
     * username at end
     * username in middle
     * multiple usernames
     * one username
     * no username
     * two usernames in a row
     * same usernames in one tweet
     * same usernames in multiple tweets
     * 
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "goobergoo", "@firstmention is a real dunce", d3);
    private static final Tweet tweet4 = new Tweet(4, "ginallygoo", "you know a dunce @ parties? @lastmention", d3);
    private static final Tweet tweet5 = new Tweet(5, "goofergoob", "@lastmention, @lastmention, where art thou", d3);
    private static final Tweet tweet6 = new Tweet(6, "goo-boo", "@donald_j @trump @good-bye", d3);
    private static final Tweet tweet7 = new Tweet(7, "goober_grub", "@donnie!! @trump @getout#now", d3);

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // TESTS FOR TIMESPAN
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));

        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanOneTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));

        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanSameTimestamps() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));

        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanReversedDates() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet1));

        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    
    
    // TESTS FOR MENTIONED USERS
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersOnlyMentions() {
        
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6, tweet7));
                
        assertTrue("expected this set", mentionedUsers.containsAll(
                Arrays.asList("donald_j", "trump", "good-bye", "donnie", "getout")));
        
        assertTrue("expected this length", mentionedUsers.size() == 5);
    }
    
    @Test
    public void testGetMentionedUsersAllNoMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2));

        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersFirstMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
                
        assertTrue("expected this set", mentionedUsers.containsAll(
                Arrays.asList("firstmention")));
    }
    
    @Test
    public void testGetMentionedUsersLastMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4));

        assertTrue("expected this set", mentionedUsers.containsAll(
                Arrays.asList("lastmention")));
    }
    
    @Test
    public void testGetMentionedUsersSameUserSameTweet() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));

        assertTrue("expected this set", mentionedUsers.containsAll(
                Arrays.asList("lastmention")));
        
        assertTrue("expected this length", mentionedUsers.size() == 1);
    }
    
    @Test
    public void testGetMentionedUsersSameUserDiffTweet() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4, tweet5));

        System.out.println(mentionedUsers);
        
        assertTrue("expected this set", mentionedUsers.containsAll(
                Arrays.asList("lastmention")));
        
        assertTrue("expected this length", mentionedUsers.size() == 1);
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version. DO NOT
     * strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */
    

}
