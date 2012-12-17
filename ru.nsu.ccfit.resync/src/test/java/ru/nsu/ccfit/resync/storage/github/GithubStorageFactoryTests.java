package ru.nsu.ccfit.resync.storage.github;

import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.junit.Test;

import ru.nsu.ccfit.resync.storage.PreferenceStorage;
import ru.nsu.ccfit.resync.storage.PreferenceStorageException;
import ru.nsu.ccfit.resync.storage.PreferenceStorageFactory;

public class GithubStorageFactoryTests {

    protected static final String PUBLIC_GIST_URL = "https://gist.github.com/4170049";
    protected static final String PUBLIC_GIST_URL_WITH_NON_EXISTENT_ID = "https://gist.github.com/40952434095243409"
            + "5243409524340952434095243";
    protected static final String PRIVATE_GIST_URL = "https://gist.github.com/dd02a4fcd5c6869e1574";
    protected static final String GIST_URL_WITHOUT_ID = "https://gist.github.com/";
    protected static final String GIST_URL_WITHOUT_ID_AND_ENDING_SLASH = "https://gist.github.com";

    private PreferenceStorageFactory factory;

    protected PreferenceStorageFactory createStorageFactory() throws AuthenticateException {
        return new GithubStorageFactory();
    }

    protected PreferenceStorage openByURLString(String url) throws Exception {
        factory = createStorageFactory();
        return factory.open(new URL(url), null);
    }

    @Test
    public void testCorrectPublicId() throws Exception {
        assertNotNull("Cannot read public gist.", openByURLString(PUBLIC_GIST_URL));
    }

    @Test(expected = PreferenceStorageException.class)
    public void testNonExistentPublicIdShouldFail() throws Exception {
        openByURLString(PUBLIC_GIST_URL_WITH_NON_EXISTENT_ID);
    }

    @Test
    public void testCorrectPrivateId() throws Exception {
        assertNotNull("Cannot read private gist.", openByURLString(PRIVATE_GIST_URL));
    }

    @Test(expected = PreferenceStorageException.class)
    public void testWithoutIdShouldFail() throws Exception {
        openByURLString(GIST_URL_WITHOUT_ID);
    }

    @Test(expected = PreferenceStorageException.class)
    public void testWithoutIdAndEndingSlashShouldFail() throws Exception {
        openByURLString(GIST_URL_WITHOUT_ID_AND_ENDING_SLASH);
    }
}
