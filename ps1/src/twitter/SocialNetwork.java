/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even
 * exist as a key in the map; this is true even if A is followed by other people
 * in the network. Twitter usernames are not case sensitive, so "ernie" is the
 * same as "ERNie". A username should appear at most once as a key in the map or
 * in any given map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets. One kind of evidence that Ernie follows Bert is if Ernie
     * @-mentions Bert in a tweet. This must be implemented. Other kinds of
     *            evidence may be used at the implementor's discretion. All the
     *            Twitter usernames in the returned social network must be
     *            either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        
        Map<String, Set<String>> userNetwork = new HashMap<>();
        
        // get all mentioned users
        Set<String> userList = Extract.getMentionedUsers(tweets);
        
        // add authors who weren't mentioned to this list
        // to get total user list
        for (int i = 0; i < tweets.size(); i++){
            if (!userList.contains(tweets.get(i).getAuthor().toLowerCase())){
                userList.add(tweets.get(i).getAuthor().toLowerCase());
            } else {
                continue;
            }
        }
        
        // now cycle through to find tweets authored or @-mentioned
        for (String user : userList) {
            
            user = user.toLowerCase();
                     
            // set of users mentioned in own tweets
            List<Tweet> ownTweets = Filter.writtenBy(tweets, user);
            Set<String> Network = Extract.getMentionedUsers(ownTweets);
            
            // set of authors mentioning user in their tweets
            // error here: you need to get ALL tweets where they're mentioned,
            // inclduing those that are uppercase. Fix this.
            List<Tweet> mentionedTweets = Filter.containing(
                    tweets, Arrays.asList("@".concat(user)));
            
            for (int i = 0; i < mentionedTweets.size(); i++) {
                if(!Network.contains(mentionedTweets.get(i).getAuthor().toLowerCase())) {
                    Network.add(mentionedTweets.get(i).getAuthor().toLowerCase());
                    
                } else {
                    continue;
                }
            }
            
            // remove username if present
            Network.remove(user);
            
            userNetwork.put(user, Network);
            
        }
        
        return userNetwork;
        
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        
        Map<String, Integer> numberFollowers = new HashMap<>();
        
        for(String user : followsGraph.keySet()) {
            
            numberFollowers.put(user, followsGraph.get(user).size());
            
        }
        
        // sort a map by size
        // stolen from stack exchange
        Map<String, Integer> sorted = numberFollowers
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                    toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));
        
        return new ArrayList<>(sorted.keySet());

        }
    }

