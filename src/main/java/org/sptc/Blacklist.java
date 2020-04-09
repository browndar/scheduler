package org.sptc;

import java.util.ArrayList;

import org.javatuples.Pair;

public class Blacklist {
    private ArrayList<Pair<String, String>> blacklist;

    public Blacklist(ArrayList<Pair<String, String>> blacklist) {
        this.blacklist = blacklist;
    }

    public boolean blacklisted(String from, String to) {
        for (Pair<String, String> black: blacklist) {
            if (black.getValue0().contains(from) && (black.getValue1().contains(to))) {
                return true;
            }
            if (black.getValue0().contains(to) && black.getValue1().contains(from)) {
                return true;
            }
        }
        return false;
    }
}
