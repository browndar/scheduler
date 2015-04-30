package org.sptc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    public File membersFile;

    List<String> ignoreLines;
    List<String> participants;
    List<String> members;
    private PairSet blacklist;


    public static void main( String[] args ) throws IOException {
        System.out.println( "Reading particpants and blacklist" );
        File participantsFile = new File(args[0]);

        File blacklistFile = new File(args[1]);

        File members = new File(args[2]);

        URL url = Scheduler.class.getResource("/ignorelines.txt");
        File ignorelinesFile = new File(url.getFile());



        Scheduler scheduler = new Scheduler(participantsFile, blacklistFile, ignorelinesFile, members);
        scheduler.initialize();
        Groups groups = scheduler.buildGroups();
        System.out.println("printing groups");
        System.out.println(groups.toString());
    }

    public Scheduler(File participants, File blackList, File ignoreLines, File members) {
        this.participantsFile = participants;
        this.blackListFile = blackList;
        this.ignoreLinesFile = ignoreLines;
        this.membersFile = members;
    }

    private Scheduler(File participants, File blackList, File members) {
        this(participants, blackList, new File(DEFAULT_IGNORE_LINES), members);
    }

    public void initialize() throws IOException {
        this.ignoreLines = readIgnoreList();
        this.participants = readParticipants();
        this.members = readMembers();
        this.blacklist = readBlackList();

        System.out.println("validating memberst");
        if (!this.validateMembers()) {
            System.exit(1);
        }

        System.out.println("validating blacklist");
        if (!this.validateBlacklist()) {
            System.exit(1);
        }

    }


    static ArrayList<String> notMembers = new ArrayList<String>();
    static {
        notMembers.add("Laura Edelstein");
        notMembers.add("Anna Armstrong");
        notMembers.add("David Teachout");
        notMembers.add("Eri Kardos");
        notMembers.add("Abigail Blickle Webber");
        notMembers.add("Missa Laneous");
        notMembers.add("Remy Michaels");



    }

    private boolean validateMembers() {
        boolean validated = true;
        for (String member: members) {
            if (notMembers.contains(member)) {
                System.out.println("Member and not member: " + member);
                validated = false;
            }
        }
        return validated;
    }

    private boolean validateBlacklist() {
        boolean validated = true;
        for (PairSet.UnorderedPair pair:blacklist.pairs) {
            if (!members.contains(pair.a) && !notMembers.contains(pair.a)) {
                System.out.println("WARN: This person not a member: " + pair.a);
                validated = false;
            }


            if (!members.contains(pair.b) && !notMembers.contains(pair.b)) {
                System.out.println("WARN: This person not a member: " + pair.b);
                validated = false;
            }
        }

        return validated;
    }

    public List<String> readParticipants() throws IOException {
        List<String> lines = FileUtils.readLines(participantsFile);
        for (Iterator<String> lineIter = lines.iterator(); lineIter.hasNext();) {
            String line = lineIter.next();
            line = line.trim();
            line = line.replaceAll("\"", "");
            if (line.isEmpty() || checkIgnore(line)) {
                lineIter.remove();
            }
        }

        return lines;
    }

    public List<String> readMembers() throws IOException {
        List<String> lines = FileUtils.readLines(membersFile);
        for (Iterator<String> lineIter = lines.iterator(); lineIter.hasNext();) {

            String line = lineIter.next();
            line = line.trim();
            line = line.replaceAll("\"", "");
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
            line = line.trim();
            line = line.replaceAll("\"", "");
            if (line.contains(",")) {
                String[] pair = line.split(",");
                pair[0] = pair[0].trim();
                pair[1] = pair[1].trim();
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
            if (line.length() == 0) {
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