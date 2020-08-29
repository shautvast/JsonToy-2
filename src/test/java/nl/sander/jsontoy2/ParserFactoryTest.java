package nl.sander.jsontoy2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserFactoryTest {
    Parser parser1;
    Parser parser2;

    @Test
    public void differentThreadsHaveTheirOwnParser() throws InterruptedException {
        Thread t1 = new Thread(() -> parser1 = ParserFactory.getParser(""));
        Thread t2 = new Thread(() -> parser2 = ParserFactory.getParser(""));
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertNotSame(parser1, parser2);
    }

    @Test
    public void sameThreadCanReuseParser() {
        Parser parser1 = ParserFactory.getParser("1");
        Integer value1 = parser1.parseInteger();
        Parser parser2 = ParserFactory.getParser("2");
        Integer value2 = parser2.parseInteger();

        assertSame(parser1, parser2);
        assertEquals(1, value1);
        assertEquals(2, value2);
    }
}