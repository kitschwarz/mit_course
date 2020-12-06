/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here. See the
     * ic03-testing exercise for examples of what a testing strategy comment
     * looks like. Make sure you have partitions.
     * 
     * followsGraph
     * 
     * someone who mentions themselves
     * someone both mentioned and author
     * someone with no mentions
     * someone with no authored tweets
     * single author
     * single mention
     * 
     * influencers
     * 
     * same number of followers for all users
     * 
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "alyssa", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "firstmention", "@firstmention is a real dunce", d3);
    private static final Tweet tweet4 = new Tweet(4, "ginallygoo", "you know a dunce @ parties? @lastmention", d3);
    private static final Tweet tweet5 = new Tweet(5, "goofergoob", "@lastmention, @lastmention, where art thou", d3);
    private static final Tweet tweet6 = new Tweet(6, "DONALD_J", "@donald_j @trump @good-bye", d3);
    private static final Tweet tweet7 = new Tweet(7, "donald_j", "@donnie!! @trump @getout#now", d3);


    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());

        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    // KIT'S TESTS
    
    @Test
    public void testGuessFollowsGraph() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7));
        
        // test no-mentions
        assertTrue("expected empty graph", followsGraph.get("alyssa").isEmpty());
        
        // test no-authored-tweets
        assertTrue("expected singleton", followsGraph.get("donnie").size() == 1);
        
        // test self-mentioned
        assertTrue("expected empty graph", followsGraph.get("firstmention").isEmpty());
        
        // test author & mentioned
        assertTrue("expected list of size", followsGraph.get("donald_j").size() == 4);
      
        // test length
        assertTrue("expected total users", followsGraph.size() == 10);

    }
    
    @Test
    public void testInfluencers() {
        
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected first", influencers.get(0).equals("donald_j"));

        assertTrue("expected second", influencers.get(1).equals("lastmention"));

        assertTrue("expected empty graph", 
                (influencers.get(9).equals("alyssa")) || (influencers.get(9).equals("firstmention")));
       
        assertTrue("expected empty graph", 
                (influencers.get(8).equals("alyssa")) || (influencers.get(8).equals("firstmention")));
       
    }



    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version. DO
     * NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
