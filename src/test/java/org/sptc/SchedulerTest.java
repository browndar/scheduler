package org.sptc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple Scheduler.
 */
public class SchedulerTest {


    @Test
    public void testBuildGroups() throws IOException, GeneralSecurityException {
        String march = "1gplLj4vpJhPN_MppbT1_j1-ZZujPR2UQcfNpP2mTT9Q";


       Pair<ArrayList<String>, ArrayList<Pair<String, String>>> participantsAndBlacklist = FormDownloader.getParticipants(
                march);

        ArrayList<String> participants = participantsAndBlacklist.getValue0();
        Blacklist blacklist = new Blacklist(participantsAndBlacklist.getValue1());

       Scheduler scheduler = new Scheduler(participants, blacklist);
        System.out.println(scheduler.buildGroups());

        /*for (int i=0; i < 100000; i++) {
            Groups groups = scheduler.buildGroups();
            assertTrue(groups.check(blacklist));
            assertTrue(groups.groups.size() > 5);
            for (List<String> group:groups.groups) {
                assertTrue(group.size() == 3 || group.size() == 4 || group.size() == 5);
            }

        }*/
    }
}
