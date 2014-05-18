package org.sptc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Groups {
    List<List<String>> groups = new ArrayList<>();
    List<String> currentGroup = new ArrayList<>();

    public Groups(List<String> participants) {
        Collections.shuffle(participants);
        Groups groups = new Groups();
        for (String string: participants) {
            groups.add(string);
        }
        groups.rejigger();
    }

    public Groups() {
    }

    public void add(String person) {
        currentGroup.add(person);
        if (currentGroup.size() == 4) {
            groups.add(currentGroup);
            currentGroup = new ArrayList<>();
        }
    }

    public void rejigger() {

        if (currentGroup.size() <= 2) {
            List<String> lastGroup = groups.get(groups.size() - 1);
            String last = lastGroup.remove(lastGroup.size() - 1);
            currentGroup.add(last);
        }

        if (currentGroup.size() <= 2) {
            List<String> secondToLastGroup = groups.get(groups.size() - 2);
            String last = secondToLastGroup.remove(secondToLastGroup.size() -1);
            currentGroup.add(last);
        }

        if ((currentGroup.size() == 3) ||
                (currentGroup.size() == 4)) {
            groups.add(currentGroup);
        }
    }

    public boolean check(PairSet blacklist) {
        for (List<String> group:groups) {
            if (!check(group, blacklist)) {
                return false;
            }
        }

        return true;
    }

    private boolean check(List<String> group, PairSet blacklist) {
        for(String first:group) {
            for(String second: group) {
                if (blacklist.contains(new PairSet.UnorderedPair(first, second))) {
                    return false;
                }
            }
        }
        return true;
    }
}
