package ru.nsu.ccfit.resync.storage.pref.jdt;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ru.nsu.ccfit.resync.pref.Preference;
import ru.nsu.ccfit.resync.pref.PreferenceProvider;
import ru.nsu.ccfit.resync.pref.jdt.SpCleanup;
import ru.nsu.ccfit.resync.storage.PreferenceStorageStub;

public class SpCleanupTests {

    @Test
    public void testGetPreferences() throws Exception {
        // arrange
        Map<String, String> original = new HashMap<String, String>();
        original.put("org.eclipse.jdt.ui/sp_cleanup.remove_trailing_whitespaces_ignore_empty", "true");

        PreferenceStorageStub storage = new PreferenceStorageStub(original);
        storage.pull();

        PreferenceProvider provider = new SpCleanup();

        // act
        Collection<Preference> preferences = provider.getPreferences(storage);

        // assert
        assertEquals(1, preferences.size());

        Preference first = preferences.iterator().next();

        assertEquals("org.eclipse.jdt.ui", first.getBundle());
        assertEquals("sp_cleanup.remove_trailing_whitespaces_ignore_empty", first.getKey());
        assertEquals("true", first.getValue());
    }
}
