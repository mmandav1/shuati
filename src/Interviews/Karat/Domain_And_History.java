package Interviews.Karat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Domain_And_History {
    /**
     * 1. Give the number of times that the ad is clicked on each domain, and request the total number of times that the domain and all sub domains have been clicked
     *
     * Input: [
     *            ["google.com", "60"],
     *            ["yahoo.com", "50"],
     *            ["sports.yahoo.com", "80"]
     *       ]
     *
     * Output: [
     *             ["com", "190"], (60+50+80)
     *             ["google.com", "60"], 
     *             ["yahoo.com", "130"] (50+80)
     *             ["sports.yahoo.com", "80"]
     *          ]
     *
     * Original Question:
     * We have some clickstream data that we gathered on our client's website. Using cookies, we collected snippets of users' anonymized URL histories while they browsed the site. The histories are in chronological order and no URL was visited more than once per person.
     *
     * Write a function that takes two user’s browsing histories as input and returns the longest contiguous sequence of URLs that appears in both.
     *
     * Sample input:
     * user0 = [ "/start.html", "/pink.php", "/register.asp", "/orange.html", "/red.html" ]
     * user1 = [ "/red.html", "/green.html", "/blue.html", "/pink.php", "/register.asp" ]
     * user2 = [ "/start.html", "/green.html", "/blue.html", "/pink.php", "/register.asp",
     *           "/orange.html" ]
     * user3 = [ "/blue.html", "/logout.php" ]
     *
     * Sample output:
     *
     * f(user0, user2):
     *    /pink.php
     *    /register.asp
     *    /orange.html
     *
     * f(user1, user2):
     *    /green.html
     *    /blue.html
     *    /pink.php
     *    /register.asp
     *
     * f(user0, user3):
     *    (empty)
     *
     * f(user1, user3):
     *    /blue.html
     * """
     *
     * !!!
     * Edge case means that if the name of a domain is the same, but the position of the subdomain is different, it cannot be counted as one
     * So the first yahoo of yahoo.com and yahoo.yahoo.com is counted as the domain name with the second position, and the second one
     * The yahoo should be counted as three and two positions, not all of them can be counted as one, the interviewer will test this situation,
     *
     *
     * 2.
     * """
     * Sample input:
     * counts = [ "900,google.com",
     *      "60,mail.yahoo.com",
     *      "10,mobile.sports.yahoo.com",
     *      "40,sports.yahoo.com",
     *      "300,yahoo.com",
     *      "10,stackoverflow.com",
     *      "2,en.wikipedia.org",
     *      "1,es.wikipedia.org",
     *      "1,mobile.sports"]
     *
     * LE_811_Subdomain_Visit_Count
     *
     * 2. Give each user access to the history record and find out the longest continuous common history between two users
     * Input: [
     *              ["3234.html", "xys.html", "7hsaa.html"], // user1
     *              ["3234.html", ''sdhsfjdsh.html", "xys.html", "7hsaa.html"] // user2
     *        ],
     *
     * user1 and user2 (specify two users for intersect)
     *
     * 输出：["xys.html", "7hsaa.html"]
     *
     * Original Question:
     * We have some clickstream data that we gathered on our client's website. Using cookies, we collected snippets of users' anonymized URL histories while they browsed the site. The histories are in chronological order and no URL was visited more than once per person.
     * Write a function that takes two users' browsing histories as input and returns the longest contiguous sequence of URLs that appears in both.
     *
     * Sample input:
     * user0 = ["/start", "/pink", "/register", "/orange", "/red", "a"]
     * user1 = ["/start", "/green", "/blue", "/pink", "/register", "/orange", "/one/two"]
     * user2 = ["a", "/one", "/two"]
     * user3 = ["/red", "/orange", "/yellow", "/green", "/blue", "/purple", "/white", "/amber", "/HotRodPink", "/BritishRacingGreen"]
     * user4 = ["/red", "/orange", "/amber", "/random", "/green", "/blue", "/purple", "/white", "/lavender", "/HotRodPink", "/BritishRacingGreen"]
     * user5 = ["a"]
     *
     * Sample output:
     * findContiguousHistory(user0, user1)
     *    /pink
     *    /register
     *    /orange
     *
     * findContiguousHistory(user1, user2)
     *    (empty)
     *
     * findContiguousHistory(user2, user0)
     *    a
     *
     * findContiguousHistory(user5, user2)
     *    a
     *
     * findContiguousHistory(user3, user4)
     *    /green
     *    /blue
     *    /purple
     *    /white
     *
     * findContiguousHistory(user4, user3)
     *    /green
     *    /blue
     *    /purple
     *    /white
     *
     *
     * for time and space complexity analysis:
     * n: length of the first user's browsing history
     * m: length of the second user's browsing history
     *
     *
     *
     *
     * #Question3
     * The people who buy ads on our network don't have enough data about how ads are working for
     * their business. They've asked us to find out which ads produce the most purchases on their website.
     *
     * Our client provided us with a list of user IDs of customers who bought something on a landing page
     * after clicking one of their ads:
     *
     *  # Each user completed 1 purchase.
     *  completed_purchase_user_ids = [
     *    "3123122444","234111110", "8321125440", "99911063"]
     *
     * And our ops team provided us with some raw log data from our ad server showing every time a
     * user clicked on one of our ads:
     *  ad_clicks = [
     *   #"IP_Address,Time,Ad_Text",
     *   "122.121.0.1,2016-11-03 11:41:19,Buy wool coats for your pets",
     *   "96.3.199.11,2016-10-15 20:18:31,2017 Pet Mittens",
     *   "122.121.0.250,2016-11-01 06:13:13,The Best Hollywood Coats",
     *   "82.1.106.8,2016-11-12 23:05:14,Buy wool coats for your pets",
     *   "92.130.6.144,2017-01-01 03:18:55,Buy wool coats for your pets",
     *   "92.130.6.145,2017-01-01 03:18:55,2017 Pet Mittens",
     * ]
     *
     * The client also sent over the IP addresses of all their users.
     *
     * all_user_ips = [
     *   #"User_ID,IP_Address",
     *   "2339985511,122.121.0.155",
     *   "234111110,122.121.0.1",
     *   "3123122444,92.130.6.145",
     *   "39471289472,2001:0db8:ac10:fe01:0000:0000:0000:0000",
     *   "8321125440,82.1.106.8",
     *   "99911063,92.130.6.144"
     * ]
     *
     * Write a function to parse this data, determine how many times each ad was clicked,
     * then return the ad text, that ad's number of clicks, and how many of those ad clicks
     * were from users who made a purchase.
     *
     *
     *  Expected output:
     *  Bought Clicked Ad Text
     *  1 of 2  2017 Pet Mittens
     *  0 of 1  The Best Hollywood Coats
     *  3 of 3  Buy wool coats for your pets
     *
     * Use a few maps to go back and forth.
     *
     * Another one:
     * Third question:
     * This question does not seem to have been seen in the ground. Count the number of clicks on ads and the number of last purchases. Given three lists, including purchasedUser (all user id)
     * ipaddressUser (IP address and user corresponding list), history (browsing record, including IP address, time and product (advertising)). It should be split,
     * Then extract all the data and traverse the history.
     *
     * The third question is equivalent to this
     * String[] purchasedUser = ["203948535", "56856", "b86785"]
     * String[] history = ["234.64.23.123,2018.10.3,item A",
     * "234.457.45.123,2018.10.3,item A",
     * "34.62.34.3,2018.10.3,item B"]
     * String[] ipaddressUser = ["203948535,234.457.2345.123",
     * "74545,234.457.2345.123"
     * "2347,234.64.23.123"
     * ]
     * For example, item A has two click records, but the user id corresponding to the corresponding ip address is actually purchased by only one person, so the output is in this form:
     * 1 of 2 item A
     * Give two pair vectors
     * {Visitor ip and text corresponding to the visited website}
     * {User id and corresponding ip}
     * Plus the id vector of the buyer
     * Return the number of purchases and visits corresponding to the text of each website
     *
     */
    static Map<String, Integer> count(Map<String, Integer> ori) {
        Map<String, Integer> subCount = new HashMap<>();

        for(String domain: ori.keySet()){
            int num = ori.get(domain);
            subCount.put(domain, subCount.getOrDefault(domain,0) + num);
            int index = domain.indexOf('.');

            while( index >= 0) {
                domain = domain.substring(index + 1);
                subCount.put(domain, subCount.getOrDefault(domain, 0) + num);
                index = domain.indexOf('.');
            }
        }
        printMap("domain counts: ", subCount);
        return subCount;
    }

    static List<String> longestCommonHistory(String[] user1, String[] user2) {
        int len1 = user1.length, len2 = user2.length;
        int[][] dp = new int[2][len2 + 1];
        int current = 0, maxLen = 0, end = 0;
        for(int i = 0; i <= len1; i++) {
            for(int j = 0; j <= len2; j++) {
                if(i == 0 || j == 0) {
                    dp[current][j] = 0;
                } else if (user1[i - 1].equals(user2[j - 1])) {
                    dp[current][j] = dp[ 1- current][j - 1] + 1;
                    if(dp[current][j] > maxLen) {
                        maxLen = dp[current][j];
                        end = j;
                    }
                } else {
                    dp[current][j] = 0;
                }
            }
            current = 1- current;
        }
        List<String> res = new ArrayList<>();
        while(maxLen-- > 0) {
            res.add(0, user2[--end]);
        }
        printList("history: ", res);
        return res;
    }

    static void printList(String s, List<String> list) {
        System.out.println(s);
        for(String i: list){
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printMap(String s, Map<String, Integer> map) {
        System.out.println(s);
        for(String ss: map.keySet()){
            System.out.println(ss + " --> " + map.get(ss));
        }
        System.out.println();
    }

    // Driver Code
    public static void main(String args[])
    {
        Map<String, Integer> map = new HashMap<>();
        map.put("google.com", 60);
        map.put("yahoo.com", 50);
        map.put("yahoo.yahoo.com", 10);
        map.put("sports.yahoo.com", 80);

        count(map);
        String[] user11 = new String[] {"3234.html", "xys.html", "7hsaa.html"};
        String[] user22 = new String[] {"3234.html", "sdhsfjdsh.html", "xys.html", "7hsaa.html"};
        String[] user0 = new String[] {"/nine.html", "/four.html", "/six.html", "/seven.html", "/one.html"};
        String[] user1 = new String[] {"/one.html", "/two.html", "/three.html", "/four.html", "/six.html"};
        String[] user2 = new String[] {"/nine.html", "/two.html", "/three.html", "/four.html", "/six.html", "/seven.html" };
        String[] user3 = new String[] {"/three.html", "/eight.html"};
        longestCommonHistory(user11, user22);
        longestCommonHistory(user0, user2);
        longestCommonHistory(user1, user2);
        longestCommonHistory(user0, user3);
        longestCommonHistory(user1, user3);
    }

}

