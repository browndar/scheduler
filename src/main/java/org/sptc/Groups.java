package org.sptc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Groups {
    List<List<String>> groups = new ArrayList<>();
    List<String> currentGroup = new ArrayList<>();

    /**
     * if len(ids) < 6:
     print("Just one group this month.")

     while len(ids) % 4 != 0:
     pods.append([ids.pop(), ids.pop(), ids.pop()])

     while len(ids) != 0:
     pods.append([ids.pop(), ids.pop(), ids.pop(), ids.pop()])

     print(pods)
     * @param participants
     */



    public Groups(List<String> participants) {
        Collections.shuffle(participants);

        if (participants.size() < 6) {
            currentGroup.addAll(participants);
            groups.add(currentGroup);
            return;
        }

        while (participants.size() % 4 != 0) {
            currentGroup.add(participants.remove(0));
            currentGroup.add(participants.remove(0));
            currentGroup.add(participants.remove(0));
            groups.add(currentGroup);
            currentGroup = new ArrayList<>();
        }

        while (participants.size() > 0) {
            currentGroup.add(participants.remove(0));
            currentGroup.add(participants.remove(0));
            currentGroup.add(participants.remove(0));
            currentGroup.add(participants.remove(0));
            groups.add(currentGroup);
            currentGroup = new ArrayList<>();
        }
    }

    public Groups() {
    }

    public void add(String person) {
        currentGroup.add(person);
        if (currentGroup.size() == 5) {
            groups.add(currentGroup);
            currentGroup = new ArrayList<>();
        }
    }

    public boolean check(Blacklist blacklist) {
        for (List<String> group:groups) {
            if (!check(group, blacklist)) {
                return false;
            }
        }

        return true;
    }

    private boolean check(List<String> group, Blacklist blacklist) {
        for(String first:group) {
            for(String second: group) {
                if (blacklist.blacklisted(first, second)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<String> group:groups) {
            for (String person :group) {
                sb.append(person);
                sb.append(System.getProperty("line.separator"));
            }
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }
}
