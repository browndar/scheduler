package org.sptc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.junit.Test;

public class FormDownloaderTest {
    String march = "1gplLj4vpJhPN_MppbT1_j1-ZZujPR2UQcfNpP2mTT9Q";


    @Test
    public void testMarch() throws IOException, GeneralSecurityException {
        Pair<ArrayList<String>, ArrayList<Pair<String, String>>> participantsAndBlacklist = FormDownloader.getParticipants(
                march);

        ArrayList<String> participants = participantsAndBlacklist.getValue0();
        assertThat(participants.size(), equalTo(11));

        assertThat(participants.get(0), equalTo("Darren Brown"));
        ArrayList<Pair<String, String>> blackList = participantsAndBlacklist.getValue1();
        Blacklist bl = new Blacklist(blackList);
        assertThat(bl.blacklisted("Darren Brown", "Bilbo Baggins"), is(true));
        assertThat(bl.blacklisted("Bilbo Baggins", "Darren Brown"), is(true));
        assertThat(bl.blacklisted("El Guapo", "Juan Churro"), is(true));
        assertThat(bl.blacklisted("Clovis van Brown", "El Guapo"), is(true));
        assertThat(bl.blacklisted("Darren Brown", "Clovis van Brown"), is(false));

    }
}
