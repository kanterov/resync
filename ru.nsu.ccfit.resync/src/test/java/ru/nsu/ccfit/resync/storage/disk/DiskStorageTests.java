package ru.nsu.ccfit.resync.storage.disk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DiskStorageTests {

    @Rule
    public static final TemporaryFolder FOLDER = new TemporaryFolder();

    public static final String EXAMPLE = "/ru/nsu/ccfit/resync/storage/disk/example.properties";

    private static final String TEMP_FILE_TO_PUSH_NAME = "push_here.txt";
    private static final int NUMBER_OF_TESTED_VALUES = 10;

    private static File exampleFile;

    @BeforeClass
    public static void setup() throws Exception {
        String content = new Scanner(DiskStorageTests.class.getResourceAsStream(EXAMPLE)).useDelimiter("\\A").next();
        exampleFile = FOLDER.newFile("example.properties");
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

    @Test
    public void testWritingProperties() throws Exception {
        Map<String, String> testedValues = new HashMap<String, String>();
        for (int i = 0; i < NUMBER_OF_TESTED_VALUES; ++i) {
            testedValues.put(String.valueOf(i), String.valueOf(i + 1));
        }
        File tempFile = FOLDER.newFile(TEMP_FILE_TO_PUSH_NAME);
        DiskStorage diskStorage = new DiskStorage(tempFile.toURI());

        for (Map.Entry<String, String> pair : testedValues.entrySet()) {
            diskStorage.put(pair.getKey(), pair.getValue());
        }

        diskStorage.push();

        diskStorage.clear();
        diskStorage.pull();
        for (String key : testedValues.keySet()) {
            String shouldBeValue = testedValues.get(key);
            String currentValue = diskStorage.get(key, null);
            assertEquals("Values for key " + key + " are not equal.", shouldBeValue, currentValue);
        }

    }
}
