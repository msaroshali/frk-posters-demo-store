package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.mindrot.jbcrypt.BCrypt;

import com.avaje.ebean.Ebean;

/**
 * This {@link Entity} provides a customer of the poster store. Each customer must have a unique email address and a
 * password to log in. Furthermore a customer can have one or more shipping and billing addresses, a list of credit
 * cards and a list of orders.
 * 
 * @author sebastianloob
 */
@Entity
@Table(name = "customer")
public class Customer
{

    /**
     * The {@link UUID} of the entity.
     */
    @Id
    private UUID id;

    /**
     * The email address to log in.
     */
    private String email;

    /**
     * The password to log in.
     */
    private String password;

    /**
     * The last name of the customer.
     */
    private String name;

    /**
     * The first name of the customer.
     */
    private String firstName;

    /**
     * A list of {@link ShippingAddress}es which the customer can select.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<ShippingAddress> shippingAddress;

    /**
     * A list of {@link BillingAddress}es which the customer can select.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<BillingAddress> billingAddress;

    /**
     * A list of {@link CreditCard}s which the customer can select.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<CreditCard> creditCard;

    /**
     * A list of {@link Order}s which the customer made.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> order;

    /**
     * The current {@link Cart} of the customer.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    /**
     * Constructor.
     */
    public Customer()
    {
        shippingAddress = new ArrayList<ShippingAddress>();
        billingAddress = new ArrayList<BillingAddress>();
        creditCard = new ArrayList<CreditCard>();
        order = new ArrayList<Order>();
    }

    /**
     * Returns the customer's email address.
     * 
     * @return the customer's email address
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets the customer's email address.
     * 
     * @param email
     *            the customer's email address
     */
    public void setEmail(final String email)
    {
        this.email = email;
    }

    /**
     * Returns the customer's hashed password.
     * 
     * @return the customer's hashed password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the customer's password. Only use this, if the password is hashed before.
     * 
     * @param password
     *            the customer's password
     */
    public void setPassword(final String password)
    {
        this.password = password;
    }

    /**
     * Returns the customer's last name.
     * 
     * @return the customer's last name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the customer's last name.
     * 
     * @param name
     *            the customer's last name
     */
    public void setName(final String name)
    {
        this.name = name;
    }

    /**
     * Returns the customer's first name.
     * 
     * @return the customer's first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Sets the customer's first name.
     * 
     * @param firstName
     *            the customer's first name
     */
    public void setFirstName(final String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Returns the list of the customer's {@link ShippingAddress}es.
     * 
     * @return the list of the customer's {@link ShippingAddress}es
     */
    public List<ShippingAddress> getShippingAddress()
    {
        return shippingAddress;
    }

    /**
     * Sets the list of the customer's {@link ShippingAddress}es
     * 
     * @param shippingAddress
     *            the list of the customer's {@link ShippingAddress}es
     */
    public void setShippingAddress(final List<ShippingAddress> shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }

    /**
     * Adds a {@link ShippingAddress} to the customer's shipping addresses.
     * 
     * @param shippingAddress
     *            the shipping address to add
     */
    public void addShippingAddress(final ShippingAddress shippingAddress)
    {
        this.shippingAddress.add(shippingAddress);
        update();
    }

    /**
     * Returns the list of the customer's {@link BillingAddress}es.
     * 
     * @return the list of the customer's {@link BillingAddress}es
     */
    public List<BillingAddress> getBillingAddress()
    {
        return billingAddress;
    }

    /**
     * Sets the list of the customer's {@link BillingAddress}es.
     * 
     * @param billingAddress
     *            the list of the customer's {@link BillingAddress}es
     */
    public void setBillingAddress(final List<BillingAddress> billingAddress)
    {
        this.billingAddress = billingAddress;
    }

    /**
     * Adds a {@link BillingAddress} to the customer's billing addresses.
     * 
     * @param billingAddress
     *            the billing address to add
     */
    public void addBillingAddress(final BillingAddress billingAddress)
    {
        this.billingAddress.add(billingAddress);
        update();
    }

    /**
     * Returns the {@link UUID} of the entity.
     * 
     * @return the {@link UUID} of the entity
     */
    public UUID getId()
    {
        return id;
    }

    /**
     * Sets the {@link UUID} of the entity.
     * 
     * @param customerId
     *            the {@link UUID} of the entity
     */
    public void setId(final UUID customerId)
    {
        id = customerId;
    }

    /**
     * Returns the list of customer's {@link CreditCard}s.
     * 
     * @return the list of customer's {@link CreditCard}s
     */
    public List<CreditCard> getCreditCard()
    {
        return creditCard;
    }

    /**
     * Sets the list of customer's {@link CreditCard}s.
     * 
     * @param creditCard
     *            the list of customer's {@link CreditCard}s
     */
    public void setCreditCard(final List<CreditCard> creditCard)
    {
        this.creditCard = creditCard;
    }

    /**
     * Adds a {@link CreditCard} to the customer's credit cards.
     * 
     * @param card
     *            the {@link CreditCard} to add.
     */
    public void addCreditCard(final CreditCard card)
    {
        creditCard.add(card);
        update();
    }

    /**
     * Returns the list of the customer's {@link Order}s.
     * 
     * @return the list of the customer's {@link Order}s
     */
    public List<Order> getOrder()
    {
        return order;
    }

    /**
     * Sets the list of the customer's {@link Order}s.
     * 
     * @param order
     *            the list of the customer's {@link Order}s
     */
    public void setOrder(final List<Order> order)
    {
        this.order = order;
    }

    /**
     * Returns the current cart of the customer.
     * 
     * @return the current cart of the customer
     */
    public Cart getCart()
    {
        return cart;
    }

    /**
     * Sets the current cart of the customer.
     * 
     * @param cart
     *            the current cart of the customer
     */
    public void setCart(final Cart cart)
    {
        this.cart = cart;
    }

    /**
     * Hashes the given password and sets to the current password.
     * 
     * @param password
     *            the password to hash
     */
    public void hashPasswd(final String password)
    {
        setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    }

    /**
     * Returns {@code true}, if the given password is equal to the customer's password; otherwise {@code false}.
     * 
     * @param password
     *            the password to check
     * @return {@code true} if the password matches, otherwise {@code false}
     */
    public boolean checkPasswd(final String password)
    {
        return BCrypt.checkpw(password, this.password);
    }

    /**
     * Updates the entity in the database.
     */
    public void update()
    {
        Ebean.update(this);
    }

    /**
     * Saves the entity in the database.
     */
    public void save()
    {
        Ebean.save(this);
    }

    /**
     * Deletes the entity from the database.
     */
    public void delete()
    {
        Ebean.delete(this);
    }

    /**
     * Returns {@code true}, if there is a customer with the given email address, otherwise {@code false}.
     * 
     * @param email
     *            the email address to check
     * @return {@code true} if the email address exist, otherwise {@code false}
     */
    public static boolean emailExist(final String email)
    {
        boolean exist = true;
        // get a list of customers, which have the given email address
        final List<Customer> loginExist = Ebean.find(Customer.class).where().eq("email", email).findList();
        // no customer has this email address
        if (loginExist.size() == 0)
        {
            exist = false;
        }
        // more than one customer has this email address
        else if (loginExist.size() > 1)
        {
            // FAILURE
        }
        return exist;
    }

    /**
     * Returns the {@link Customer} by the customer's email address.
     * 
     * @param email
     *            the customer's email address
     * @return the {@link Customer} with the given email address
     */
    public static Customer getCustomerByEmail(final String email)
    {
        // get customer by email address
        return Ebean.find(Customer.class).where().eq("email", email).findUnique();
    }

    /**
     * Returns the {@link Customer} by the customer's id.
     * 
     * @param customerId
     *            the {@link UUID} of the customer
     * @return the {@link Customer} with the given {@link UUID}
     */
    public static Customer getCustomerById(final UUID customerId)
    {
        // get customer by id
        return Ebean.find(Customer.class, customerId);
    }

    /**
     * Returns all {@link Order}s of the customer ordered by the date in a descending order.
     * 
     * @return all {@link Order}s of the customer
     */
    public List<Order> getAllOrders()
    {
        return Ebean.find(Order.class).where().eq("customer", this).orderBy("lastUpdate  desc").findList();
    }
}
