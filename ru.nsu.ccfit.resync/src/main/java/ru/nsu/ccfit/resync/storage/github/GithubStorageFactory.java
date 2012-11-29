package ru.nsu.ccfit.resync.storage.github;

import java.net.URL;
import java.util.Map;
import java.util.regex.Pattern;

import ru.nsu.ccfit.resync.storage.PreferenceStorage;
import ru.nsu.ccfit.resync.storage.PreferenceStorageException;
import ru.nsu.ccfit.resync.storage.PreferenceStorageFactory;

public class GithubStorageFactory implements PreferenceStorageFactory {

    private static final String SLASH_PLUS_HEX_DIGITS_REGEXP = "^/[\\da-fA-F]+";
    private static final String PROTOCOL = "https";
    private static final String HOST = "gist.github.com";

    @Override
    public boolean canOpen(URL location) {
        try {
            return PROTOCOL.equalsIgnoreCase(location.getProtocol()) && HOST.equalsIgnoreCase(location.getHost())
                    && Pattern.matches(SLASH_PLUS_HEX_DIGITS_REGEXP, location.getPath());
        } catch (NullPointerException e) {
            // weird!!!
            return false;
        }
    }

    @Override
    public PreferenceStorage open(URL location, Map<String, Object> options) throws PreferenceStorageException {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        if (!canOpen(location)) {
            throw new PreferenceStorageException("url is not supported");
        }
        GithubStorage githubStorage = new GithubStorage(location);
        githubStorage.pull();
        return githubStorage;
    }

    /**
     * for testing purposes only... Prints contents of the gist by url
     * 
     * @param args
     */
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("More parameters!");
        }
        GithubStorageFactory githubStorageFactory = new GithubStorageFactory();
        PreferenceStorage preferenceStorage = githubStorageFactory.open(new URL(args[0]), null);
        System.out.println("Parsed as PROPERTIES gist looks like:");
        for (String key : preferenceStorage.keySet()) {
            System.out.println(key + " => " + preferenceStorage.get(key, "DEFAULT_VALUE"));
        }
    }

}
