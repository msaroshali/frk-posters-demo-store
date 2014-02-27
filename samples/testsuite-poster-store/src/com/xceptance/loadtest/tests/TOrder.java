package com.xceptance.loadtest.tests;

import org.junit.Test;

import com.xceptance.loadtest.actions.Homepage;
import com.xceptance.loadtest.actions.account.GoToRegistrationForm;
import com.xceptance.loadtest.actions.account.GoToSignIn;
import com.xceptance.loadtest.actions.account.Login;
import com.xceptance.loadtest.actions.account.Register;
import com.xceptance.loadtest.actions.order.PlaceOrder;
import com.xceptance.loadtest.flows.BrowseAndAddToCartFlow;
import com.xceptance.loadtest.flows.CheckoutFlow;
import com.xceptance.loadtest.util.Account;
import com.xceptance.xlt.api.actions.AbstractHtmlPageAction;
import com.xceptance.xlt.api.tests.AbstractTestCase;
import com.xceptance.xlt.api.util.XltProperties;

/**
 * Open the landing page, register account and browse the catalog to a random product. Configure this product and add it
 * to the cart. Finally process the checkout including the final order placement step.
 * 
 * @author sebastianloob
 */
public class TOrder extends AbstractTestCase
{

    /**
     * Create account data. 
     * This data will be used to register a new shop account.
     */
    private final Account account = new Account();
    
    
    /**
     * Main test method.
     * 
     * @throws Throwable
     */
    @Test
    public void order() throws Throwable
    {
        // Read the store URL from properties. Directly referring to the properties allows to access them by the full
        // path.
        final String url = XltProperties.getInstance().getProperty("com.xceptance.xlt.loadtest.tests.store-url",
                                                                   "http://localhost:8080/");
        
        
        // Go to poster store homepage
        Homepage homepage = new Homepage(url, "Homepage");
        homepage.run();

        // go to sign in
        GoToSignIn goToSignIn = new GoToSignIn(homepage, "GoToSignIn");
        goToSignIn.run();

        // go to registration form
        GoToRegistrationForm goToRegistrationForm = new GoToRegistrationForm(goToSignIn, "GoToRegistrationForm");
        goToRegistrationForm.run();

        // register
        Register register = new Register(goToRegistrationForm, "Register", this.account);
        register.run();

        // log in
        Login login = new Login(register, "Login", account);
        login.run();

        // Browse and add a product to cart (FLOW!!)
        // TODO Add more comments to explain what a flow is and how it works
        BrowseAndAddToCartFlow browseAndAddToCart = new BrowseAndAddToCartFlow(login);
        AbstractHtmlPageAction viewCart = browseAndAddToCart.run();

        // Checkout Flow
        CheckoutFlow checkoutFlow = new CheckoutFlow(viewCart, account);
        AbstractHtmlPageAction enterPaymentMethod = checkoutFlow.run();

        // place the order
        PlaceOrder placeOrder = new PlaceOrder(enterPaymentMethod, "PlaceOrder");
        placeOrder.run();
    }
}