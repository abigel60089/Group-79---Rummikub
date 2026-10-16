import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LinkedChainTest
{
    @Test
    public void addAndAddToEndHaveExpectedOrder()
    {
        LinkedChain<String> chain = new LinkedChain<String>();
        chain.addToEnd("first");
        chain.addToEnd("last");
        chain.add("new front");

        assertEquals(3, chain.size());
        assertEquals("new front", chain.get(0));
        assertEquals("first", chain.get(1));
        assertEquals("last", chain.get(2));
    }


    @Test
    public void removesFirstAndMatchingEntries()
    {
        LinkedChain<String> chain = new LinkedChain<String>();
        chain.addToEnd("one");
        chain.addToEnd("two");
        chain.addToEnd("three");

        assertTrue(chain.remove("two"));
        assertFalse(chain.remove("missing"));
        assertEquals(2, chain.size());
        assertEquals("one", chain.remove());
        assertEquals("three", chain.remove());
        assertNull(chain.remove());
        assertTrue(chain.isEmpty());
    }


    @Test
    public void countsEntriesAndCopiesArrayInOrder()
    {
        LinkedChain<String> chain = new LinkedChain<String>();
        chain.addToEnd("tile");
        chain.addToEnd("tile");
        chain.addToEnd("other");

        assertEquals(2, chain.count("tile"));
        assertTrue(chain.contains("other"));
        assertFalse(chain.contains("missing"));
        assertEquals("tile", chain.toArray(new String[chain.size()])[0]);
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void rejectsOutOfRangeIndex()
    {
        new LinkedChain<String>().get(0);
    }


    @Test
    public void clearEmptiesChain()
    {
        LinkedChain<Object> chain = new LinkedChain<Object>();
        Object value = new Object();
        chain.add(value);
        assertSame(value, chain.get(0));

        chain.clear();

        assertTrue(chain.isEmpty());
        assertEquals(0, chain.size());
    }
}
