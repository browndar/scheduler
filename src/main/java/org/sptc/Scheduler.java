package org.sptc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.javatuples.Pair;

/**
 * Hello world!
 *
 */
public class Scheduler
{
    ArrayList<String> participants;
    Blacklist blacklist;

    public Scheduler(ArrayList<String> participants, Blacklist blacklist) {
        this.participants = participants;
        this.blacklist = blacklist;
    }

    public Groups buildGroups() {
        System.out.println("participants: " + participants.size());
        while(true) {
            Groups groups = new Groups(participants);

            if (groups.check(blacklist)) {
                return groups;
            }
        }
    }
}