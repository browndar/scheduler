package org.sptc;


import java.util.HashSet;
import java.util.Set;

public class PairSet {

    Set<UnorderedPair> pairs;

    public PairSet() {
        this.pairs = new HashSet<UnorderedPair>();
    }

    public void add (UnorderedPair pair) {
        this.pairs.add(pair);
    }

    public boolean contains(UnorderedPair pair) {
        return pairs.contains(pair);
    }

    public static class UnorderedPair {
        String a;
        String b;

        public UnorderedPair(String a, String b) {
            this.a = a;
            this.b = b;
        }

        boolean contains(String o) {
            if (a.equals(o) || b.equals(o)) {
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UnorderedPair that = (UnorderedPair) o;

            return this.equalsOrdered(that) || this.equalsOrdered(new UnorderedPair(that.b, that.a));
        }

        public boolean equalsOrdered(UnorderedPair that) {
            if (a != null ? !a.equals(that.a) : that.a != null) return false;
            if (b != null ? !b.equals(that.b) : that.b != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int resulta = a != null ? a.hashCode() : 0;
            int resultb = b != null ? b.hashCode() : 0;
            int result = 31 * resulta + resultb;
            int resultReverse = 31 * resultb + resulta;
            return result + resultReverse;
        }
    }


}