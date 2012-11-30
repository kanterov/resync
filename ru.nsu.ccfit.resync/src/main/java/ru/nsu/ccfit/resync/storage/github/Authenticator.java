package ru.nsu.ccfit.resync.storage.github;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.egit.github.core.Authorization;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OAuthService;

public class Authenticator
{
    private static final String GIST_SCOPE = "gist";
    private String oAuthToken;

    public Authenticator(String userName, String password) throws AuthenticateException
    {
    	if (userName == null || userName.length() == 0)
    	{
    		throw new AuthenticateException("Username cannot be null");
    	}
    	if (password == null || password.length() == 0)
    	{
    		throw new AuthenticateException("Password cannot be null");
    	}
    	
        GitHubClient client = new GitHubClient();
        client.setCredentials(userName, password);
        OAuthService oAuthService = new OAuthService(client);

        try
        {
            Authorization authorization = new Authorization();
            authorization.setScopes(Arrays.asList(GIST_SCOPE));
            Authorization createdAuthorization = oAuthService.createAuthorization(authorization);
            this.oAuthToken = createdAuthorization.getToken();
        } catch (IOException exception)
        {
            throw new AuthenticateException(exception.getMessage());
        }
    }

    public String getOAuthToken()
    {
        return this.oAuthToken;
    }

    public void setOAuthToken(String oAuthToken)
    {
    	this.oAuthToken = oAuthToken;
    }
}
