package ru.nsu.ccfit.resync.storage.github;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import ru.nsu.ccfit.resync.storage.PreferenceStorage;
import ru.nsu.ccfit.resync.storage.PreferenceStorageException;
import ru.nsu.ccfit.resync.storage.PreferenceStorageFactory;

public class WritableGithubStorageTests extends GithubStorageFactoryTests {

    private static final String DEFAULT_NO_VALUE = "<NO_VALUE>";
    private static final String OTHER_USER_PRIVATE_GIST_URL = "https://gist.github.com/35ae86c511e7b81cac7a";
    private static final String OTHER_USER_PUBLIC_GIST_URL = "https://gist.github.com/4095243";

    @Override
    protected PreferenceStorageFactory createStorageFactory() throws AuthenticateException {
        return new WritableGithubStorageFactory(new Authenticator("TestResync", "test_resync_123"));
    }

    @Test
    public void testWriteToPublicTheSameWithLoginGist() throws Exception {
        pushSameValues(PUBLIC_GIST_URL);
    }

    @Test
    public void testWriteToPublicAnotherValuesWithLoginGist() throws Exception {
        pushOneRandomValue(PUBLIC_GIST_URL);
    }

    @Test
    public void testWriteToPrivateTheSameWithLoginGist() throws Exception {
        pushSameValues(PRIVATE_GIST_URL);
    }

    @Test
    public void testWriteToPrivateAnotherValuesWithLoginGist() throws Exception {
        pushOneRandomValue(PRIVATE_GIST_URL);
    }

    @Test(expected = PreferenceStorageException.class)
    public void testWriteToOtherPrivateGistShouldFail() throws Exception {
        pushSameValues(OTHER_USER_PRIVATE_GIST_URL);
    }

    @Test(expected = PreferenceStorageException.class)
    public void testWriteToOtherPrivateGistAnotherValuesWithLoginShouldFail() throws Exception {
        pushOneRandomValue(OTHER_USER_PRIVATE_GIST_URL);
    }

    @Test(expected = PreferenceStorageException.class)
    public void testWriteToOtherPublicGistShouldFail() throws Exception {
        pushSameValues(OTHER_USER_PUBLIC_GIST_URL);
    }

    @Test(expected = PreferenceStorageException.class)
    public void testWriteToOtherPublicGistAnotherValuesShouldFail() throws Exception {
        pushOneRandomValue(OTHER_USER_PUBLIC_GIST_URL);
    }

    private void pushOneRandomValue(String url) throws Exception {
        PreferenceStorage preferenceStorage = openByURLString(url);
        ArrayList<String> preferenceKeys = new ArrayList<String>(preferenceStorage.keySet());
        String someKey = preferenceKeys.get(preferenceKeys.size() - 1);
        String expectedValue = new Long(new Random().nextLong()).toString();
        preferenceStorage.put(someKey, expectedValue.toString());

        preferenceStorage.push();
        preferenceStorage.pull();

        String actualValue = preferenceStorage.get(someKey, DEFAULT_NO_VALUE);

        assertEquals(expectedValue, actualValue);
    }

    private void pushSameValues(String url) throws Exception {
        PreferenceStorage preferenceStorage = openByURLString(url);
        Map<String, String> currentValues = new HashMap<String, String>();
        for (String key : preferenceStorage.keySet()) {
            currentValues.put(key, preferenceStorage.get(key, DEFAULT_NO_VALUE));
        }

        preferenceStorage.push();
        preferenceStorage.pull();

        for (String key : preferenceStorage.keySet()) {
            String expected = currentValues.get(key);
            String actual = preferenceStorage.get(key, DEFAULT_NO_VALUE);
            assertEquals(expected, actual);
        }
    }
}
