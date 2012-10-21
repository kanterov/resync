package ru.nsu.ccfit.resync.storage.disk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DiskStorageTests {

    @Rule
    public final static TemporaryFolder folder = new TemporaryFolder();

    public final static String EXAMPLE = "/ru/nsu/ccfit/resync/storage/disk/example.properties";

    private static File exampleFile;

    @BeforeClass
    public static void setup() throws Exception {
        String content = new Scanner(DiskStorageTests.class.getResourceAsStream(EXAMPLE)).useDelimiter("\\A").next();
        exampleFile = folder.newFile("example.properties");
        FileWriter writer = new FileWriter(exampleFile);

        writer.write(content);
        writer.close();
    }

    @Test
    public void testEmptyBeforePull() throws Exception {
        // arrange
        DiskStorage storage = new DiskStorage(exampleFile.toURI());

        // act
        Set<String> keySet = storage.keySet();

        // assert
        assertTrue(keySet.isEmpty());
    }

    @Test
    public void testGettingValues() throws Exception {
        // arrange
        DiskStorage storage = new DiskStorage(exampleFile.toURI());
        storage.pull();

        // act
        String pub = storage.get("ru.ccfit.demo/public", null);
        String answer = storage.get("ru.ccfit.example/answer", null);
        String defaultValue = storage.get("ru.ccfit.example/dontHaveThisKey", "defaultValue");

        // assert
        assertEquals(pub, "true");
        assertEquals(answer, "42");
        assertEquals(defaultValue, "defaultValue");
    }

}
