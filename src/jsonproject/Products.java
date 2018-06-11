
package jsonproject;

/**
 *
 * @author Halim
 */
public class Products {
    
    private String id;
    private String name;
    private String price;
    private String currency;
    private String bestBefore;

     public Products() {
       
    }
    public Products(String id, String name, String price, String currency, String bestBefore) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.bestBefore = bestBefore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(String bestBefore) {
        this.bestBefore = bestBefore;
    }

   /* @Override
    public String toString() {
        return "products{" + "id=" + id + ", name=" + name + ", price=" + price + ", currency=" + currency + ", bestBefore=" + bestBefore + '}';
    }
    */
    
    
}
