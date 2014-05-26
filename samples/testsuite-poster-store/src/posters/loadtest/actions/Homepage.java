package posters.loadtest.actions;

import java.net.URL;
import java.util.List;

import org.junit.Assert;

import posters.loadtest.validators.HeaderValidator;
import posters.loadtest.validators.SideNavValidator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xceptance.xlt.api.actions.AbstractHtmlPageAction;
import com.xceptance.xlt.api.engine.NetworkData;
import com.xceptance.xlt.api.validators.ContentLengthValidator;
import com.xceptance.xlt.api.validators.HtmlEndTagValidator;
import com.xceptance.xlt.api.validators.HttpResponseCodeValidator;

/**
 * Loads the homepage under the specified URL. This is usually the starting point for most test cases. Here we open the
 * demo poster store application's homepage.
 */
public class Homepage extends AbstractHtmlPageAction
{
    /**
     * The url as string to fetch the data from.
     */
    private final String urlAsString;

    /**
     * The url as URL object.
     */
    private URL url;

    /**
     * Constructor. This will be called from a test case and while doing so the homepage's URL will be passed so that
     * the page can eventually be loaded.
     * 
     * @param urlAsString
     *            the URL to fetch the data from
     */
    public Homepage(final String urlAsString)
    {
        super(null);

        this.urlAsString = urlAsString;
    }

    @Override
    public void preValidate() throws Exception
    {
        // we have to check, whether or not the passed url is valid
        Assert.assertNotNull("Url must not be null", urlAsString);

        // Use the java URL class to do the final validation since it will throw
        // an exception, if this is not a valid
        // URL. We do not have to deal with the exception, the framework will do
        // it.
        url = new URL(urlAsString);
    }

    /**
     * Execute the request. Once pre-execution conditions have been meet, the execute method can be called to load the
     * page, in this case the homepage will be requested.
     */
    @Override
    protected void execute() throws Exception
    {
        // load the page simply by firing the URL
        // always make sure that loadPage* methods are used
        loadPage(url);
    }

    /**
     * Validate the correctness of the result. Once the homepage has been loaded, we can ensure that certain key element
     * are present in our previous request's responses. For example, here we are validating that the proper response
     * code was sent, the length of the page is correct, an end tag is present, there is a head line on the page. This
     * is all being done with the help of validators. Validators are used when we need to check the same thing after
     * several different actions.
     */
    @Override
    protected void postValidate() throws Exception
    {
        // get the result of the last action
        final HtmlPage page = getHtmlPage();

        // First, we check all common criteria. This code can be bundled and
        // reused if needed. For the purpose of a
        // programming example, we leave it here as detailed as possible.

        // check the response code, the singleton instance validates for 200
        HttpResponseCodeValidator.getInstance().validate(page);

        // check the content length, compare delivered content length to the
        // content length that was announced in the
        // HTTP response header
        ContentLengthValidator.getInstance().validate(page);

        // check for complete HTML
        HtmlEndTagValidator.getInstance().validate(page);

        // We can be pretty sure now, that the page fulfils the basic
        // requirements to be a valid page from our demo poster store.
        // Run more page specific tests now.

        // check for the header
        HeaderValidator.getInstance().validate(page);

        // Check the side navigation
        SideNavValidator.getInstance().validate(page);

        // Get the homepage title
        final HtmlElement blogNameElement = page.getHtmlElementById("titleIndex");
        Assert.assertNotNull("Title not found", blogNameElement);

        // get the content form the element
        final String text = blogNameElement.asText();

        // compare it
        Assert.assertEquals("Title does not match", "Check out our new panorama posters!", text);

        /*
         * This section validates the responses on the network layer using the API from #471
         */
        final List<NetworkData> networkData = getNetworkDataSet();

        // we just look at the first request/response pair
        final NetworkData n0 = networkData.get(0);
        Assert.assertEquals("/posters/", n0.getURL().getPath());
        Assert.assertEquals("text/html", n0.getContentType());
        Assert.assertEquals("GET", n0.getRequestMethod().toString());
        Assert.assertEquals(200, n0.getResponseStatusCode());

        // Host=localhost:8080
        // User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.19; Xceptance LoadTest ?.?.?/r???)
        // Gecko/2010031422 Firefox/3.0.19
        // Accept-Language=en-us
        // Accept=*/*
        Assert.assertEquals(getHtmlPage().getWebClient().getBrowserVersion().getUserAgent(),
                            n0.getAdditionalRequestHeaders().get("User-Agent"));
        Assert.assertTrue(n0.getAdditionalRequestHeaders().containsKey("Host")); // check only
        Assert.assertTrue(n0.getAdditionalRequestHeaders().containsKey("Accept-Language")); // check only
        Assert.assertTrue(n0.getAdditionalRequestHeaders().containsKey("Accept")); // check only
    }
}