package org.sptc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple Scheduler.
 */
public class SchedulerTest {

    File participantsFile;
    File blacklistFile;
    File ignorelinesFile;
    File membersFile;

    @Before
    public void setup() {
        URL url = this.getClass().getResource("/participants.txt");
        participantsFile = new File(url.getFile());
        url = this.getClass().getResource("/blacklist.txt");
        blacklistFile = new File(url.getFile());
        url = this.getClass().getResource("/ignorelines.txt");
        ignorelinesFile = new File(url.getFile());
        url = this.getClass().getResource("/members.txt");
        membersFile = new File(url.getFile());
    }

    @Test
    public void testReadParticipants() throws IOException {
        Scheduler scheduler = new Scheduler(participantsFile, blacklistFile, ignorelinesFile, membersFile);
        scheduler.initialize();
        List<String> participants = scheduler.readParticipants();
        assertEquals(34, participants.size());
    }

    @Test
    public void testReadBlackList() throws IOException {
        Scheduler scheduler = new Scheduler(participantsFile, blacklistFile, ignorelinesFile, membersFile);
        scheduler.initialize();
        PairSet pairSet = scheduler.readBlackList();
        assertTrue(pairSet.contains(new PairSet.UnorderedPair("Melkor", "Elrond")));
        assertTrue(pairSet.contains(new PairSet.UnorderedPair("Elrond", "Melkor")));
    }

    @Test
    public void testBuildGroups() throws IOException {
        Scheduler scheduler = new Scheduler(participantsFile, blacklistFile, ignorelinesFile, membersFile);
        scheduler.initialize();

        List<String> participants = scheduler.readParticipants();
        PairSet blacklist = scheduler.readBlackList();
        boolean violating = false;

        while (!violating) {
            Groups groups = new Groups(participants);
            violating = groups.check(blacklist);
        }

        for (int i=0; i < 100000; i++) {
            Groups groups = scheduler.buildGroups();
            assertTrue(groups.check(blacklist));
            assertTrue(groups.groups.size() > 5);
            for (List<String> group:groups.groups) {
                assertTrue(group.size() == 3 || group.size() == 4 || group.size() == 5);
            }

        }
    }
}
