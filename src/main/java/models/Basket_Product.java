package models;

import java.sql.Timestamp;
import java.text.DecimalFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.avaje.ebean.Ebean;

@Entity
@Table(name = "basket_product")
public class Basket_Product
{
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    private int countProduct;

    /**
     * The finish of the poster, e.g. matte or gloss.
     */
    private String finish;

    /**
     * The size of the poster.
     */
    @ManyToOne
    @JoinColumn(name = "posterSize_id")
    private PosterSize size;

    /**
     * The price of the product in the selected finish and size.
     */
    private double price;

    @Version
    private Timestamp lastUpdate;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Basket getBasket()
    {
        return basket;
    }

    public void setBasket(Basket basket)
    {
        this.basket = basket;
    }

    public int getCountProduct()
    {
        return countProduct;
    }

    public void setCountProduct(int countProduct)
    {
        this.countProduct = countProduct;
    }

    public String getFinish()
    {
        return finish;
    }

    public void setFinish(String finish)
    {
        this.finish = finish;
    }

    public PosterSize getSize()
    {
        return size;
    }

    public void setSize(PosterSize size)
    {
        this.size = size;
    }

    public double getPrice()
    {
        return price;
    }

    public String getPriceAsString()
    {
        DecimalFormat f = new DecimalFormat("#0.00");
        double temp = price;
        temp = temp * 100;
        temp = Math.round(temp);
        temp = temp / 100;
        return f.format(temp).replace(',', '.');
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public Timestamp getLastUpdate()
    {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate)
    {
        this.lastUpdate = lastUpdate;
    }

    public void incCountProduct()
    {
        this.setCountProduct(this.getCountProduct() + 1);
    }

    public void update()
    {
        Ebean.update(this);
    }

    public void save()
    {
        Ebean.save(this);
    }

    public void decrementProductCount()
    {
        this.setCountProduct(this.getCountProduct() - 1);
    }
}
