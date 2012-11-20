package ru.nsu.ccfit.resync.storage.disk;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.Test;

public class DiskStorageFactoryTests {

    @Test
    public void testCanOpenFiles() throws Exception {
        // arrange
        DiskStorageFactory factory = new DiskStorageFactory();
        URL location = new URL("file:///tmp/hello.properties");

        // act
        boolean canOpen = factory.canOpen(location);

        // assert
        assertTrue(canOpen);
    }

    @Test
    public void testCantOpenHttp() throws Exception {
        // arrange
        DiskStorageFactory factory = new DiskStorageFactory();
        URL location = new URL("http://google.com/robots.txt");

        // act
        boolean canOpen = factory.canOpen(location);

        // assert
        assertFalse(canOpen);
    }

}
