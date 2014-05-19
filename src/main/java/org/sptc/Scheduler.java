package org.sptc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Hello world!
 *
 */
public class Scheduler
{
    static final String DEFAULT_IGNORE_LINES = "ignorelines.txt";

    public File participantsFile;
    public File blackListFile;
    public File ignoreLinesFile;

    List<String> ignoreLines;
    List<String> participants;
    private PairSet blacklist;


    public static void main( String[] args ) throws IOException {
        System.out.println( "Reading particpants and blacklist" );
        File participantsFile = new File(args[0]);
        File blacklistFile = new File(args[1]);

        URL url = Scheduler.class.getResource("/ignorelines.txt");
        File ignorelinesFile = new File(url.getFile());

        Scheduler scheduler = new Scheduler(participantsFile, blacklistFile, ignorelinesFile);
        scheduler.initialize();
        Groups groups = scheduler.buildGroups();
        System.out.println("printing groups");
        System.out.println(groups.toString());
    }

    public Scheduler(File participants, File blackList, File ignoreLines) {
        this.participantsFile = participants;
        this.blackListFile = blackList;
        this.ignoreLinesFile = ignoreLines;
    }

    public Scheduler(File participants, File blackList) {
        this(participants, blackList, new File(DEFAULT_IGNORE_LINES));
    }

    public void initialize() throws IOException {
        this.ignoreLines = readIgnoreList();
        this.participants = readParticipants();
        this.blacklist = readBlackList();
    }

    public List<String> readParticipants() throws IOException {
        List<String> lines = FileUtils.readLines(participantsFile);
        for (Iterator<String> lineIter = lines.iterator(); lineIter.hasNext();) {
            String line = lineIter.next();
            line = line.trim();
            if (line.isEmpty() || checkIgnore(line)) {
                lineIter.remove();
            }
        }

        return lines;
    }

    public List<String> readIgnoreList() throws IOException {
        List<String> lines = FileUtils.readLines(ignoreLinesFile);
        return lines;
    }

    public PairSet readBlackList() throws IOException {
        PairSet pairSet = new PairSet();
        List<String> lines = FileUtils.readLines(blackListFile);
        for (Iterator<String> lineIter = lines.iterator(); lineIter.hasNext();) {
            String line = lineIter.next();
            if (line.contains(",")) {
                String[] pair = line.split(",");
                pairSet.add(new PairSet.UnorderedPair(pair[0], pair[1]));
            }
        }

        return pairSet;
    }

    private boolean checkIgnore(String line) {
        for (String ignoreLine: ignoreLines) {
            if (line.contains(ignoreLine)) {
                return true;
            }
        }
        return false;
    }

    public Groups buildGroups() {
        while(true) {
            Groups groups = new Groups(participants);

            if (groups.check(blacklist)) {
                return groups;
            }
        }
    }
}