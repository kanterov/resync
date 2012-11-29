package ru.nsu.ccfit.resync.storage.github;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import ru.nsu.ccfit.resync.storage.PreferenceStorage;
import ru.nsu.ccfit.resync.storage.PreferenceStorageException;
import ru.nsu.ccfit.resync.storage.PreferenceStorageFactory;

public class WritableGithubStorageTests extends GithubStorageFactoryTests {

	protected String OTHER_USER_PRIVATE_GIST_URL = "https://gist.github.com/35ae86c511e7b81cac7a";
	protected String OTHER_USER_PUBLIC_GIST_URL = "https://gist.github.com/4095243";
	@Override
	protected PreferenceStorageFactory createStorageFactory() throws AuthenticateException {
		return new WritableGithubStorageFactory(new Authenticator("TestResync", "test_resync_123"));
	}
	
	@Test
	public void testWriteToPublicTheSameWithLoginGist() throws Exception
	{
		pushSameValues(PUBLIC_GIST_URL);
	}
	
	@Test
	public void testWriteToPublicAnotherValuesWithLoginGist() throws Exception
	{
		pushOneRandomValue(PUBLIC_GIST_URL);
	}
	
	@Test
	public void testWriteToPrivateTheSameWithLoginGist() throws Exception
	{
		pushSameValues(PRIVATE_GIST_URL);
	}
	
	@Test
	public void testWriteToPrivateAnotherValuesWithLoginGist() throws Exception
	{
		pushOneRandomValue(PRIVATE_GIST_URL);
	}

	
	
	@Test(expected = PreferenceStorageException.class)
	public void testWriteToOtherPrivateGistShouldFail() throws Exception
	{
		pushSameValues(OTHER_USER_PRIVATE_GIST_URL);
	}

	@Test(expected = PreferenceStorageException.class)
	public void testWriteToOtherPrivateGistAnotherValuesWithLoginShouldFail() throws Exception
	{
		pushOneRandomValue(OTHER_USER_PRIVATE_GIST_URL);
	}
	
	@Test (expected = PreferenceStorageException.class)
	public void testWriteToOtherPublicGistShouldFail() throws Exception
	{
		pushSameValues(OTHER_USER_PUBLIC_GIST_URL);
	}
	
	@Test (expected = PreferenceStorageException.class)
	public void testWriteToOtherPublicGistAnotherValuesShouldFail() throws Exception
	{
		pushOneRandomValue(OTHER_USER_PUBLIC_GIST_URL);
	}
	
	private void pushOneRandomValue(String url) throws Exception, PreferenceStorageException {
		PreferenceStorage preferenceStorage = openByURLString(url);
		ArrayList<String> preferenceKeys = new ArrayList<String>(preferenceStorage.keySet());
		String someKey = preferenceKeys.get(preferenceKeys.size() - 1);
		preferenceStorage.put(someKey, new Long(new Random().nextLong()).toString());
		preferenceStorage.push();
	}
	
	private void pushSameValues(String url) throws Exception {
		PreferenceStorage preferenceStorage = openByURLString(url);
		preferenceStorage.push();
	}
}
