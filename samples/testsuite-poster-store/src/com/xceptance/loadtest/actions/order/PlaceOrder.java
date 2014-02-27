package com.xceptance.loadtest.actions.order;

import org.junit.Assert;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xceptance.loadtest.validators.HeaderValidator;
import com.xceptance.xlt.api.actions.AbstractHtmlPageAction;
import com.xceptance.xlt.api.util.HtmlPageUtils;
import com.xceptance.xlt.api.validators.ContentLengthValidator;
import com.xceptance.xlt.api.validators.HtmlEndTagValidator;
import com.xceptance.xlt.api.validators.HttpResponseCodeValidator;

/**
 * This {@link AbstractHtmlPageAction} places the order finally.
 * 
 * @author sebastianloob
 */
public class PlaceOrder extends AbstractHtmlPageAction
{

    /**
     * Place order button.
     */
    private HtmlElement placeOrderButton;

    /**
     * Constructor.
     * 
     * @param previousAction
     * @param timerName
     */
    public PlaceOrder(AbstractHtmlPageAction previousAction, String timerName)
    {
        super(previousAction, timerName);
    }

    @Override
    public void preValidate() throws Exception
    {
        // Get the current page.
        final HtmlPage page = getPreviousAction().getHtmlPage();
        Assert.assertNotNull("Failed to get page from previous action.", page);

        // check that the place order button is available
        Assert.assertTrue("Place order button not found.", HtmlPageUtils.isElementPresent(page, "id('btnOrder')"));

        // remember the place order button
        this.placeOrderButton = HtmlPageUtils.findSingleHtmlElementByID(page, "btnOrder");
    }

    @Override
    protected void execute() throws Exception
    {
        // Click the place order button.
        loadPageByClick(placeOrderButton);
    }

    @Override
    protected void postValidate() throws Exception
    {
        // get the result of the last action
        final HtmlPage page = getHtmlPage();

        // Basic checks - see action 'Homepage' for some more details how and when to use these validators
        HttpResponseCodeValidator.getInstance().validate(page);
        ContentLengthValidator.getInstance().validate(page);
        HtmlEndTagValidator.getInstance().validate(page);

        HeaderValidator.getInstance().validate(page);

        // check that the order was successfully placed
        boolean successfulOrder = page.asXml().contains("Thank you for shopping with us!");
        Assert.assertTrue("Placing order failed.", successfulOrder);
    }
}