/* TODO: Create a subclass of Trader named DrivableTrader
 * This class should be identical to Trader, except that it takes
 * only Drivable objects in its inventory, wishlist, etc.
 *
 * The sellingPrice returned should also be overridden. The selling price
 * should be equal to the:
 *     Object's price + Object's max speed
 * If the object is Tradable (and Tradable.MISSING_PRICE otherwise.)
 *
 * Look at DomesticatableTrader.java for an example.
 */

import java.util.ArrayList;
import java.util.List;

public class DrivableTrader<DT> extends Trader{
  
  private final List<DT> inventory;
    private final List<DT> wishlist;
    private int money;


    /**
     * Construct a DrivableTrader, giving them the given inventory,
     * wishlist, and money.
     *
     * @param inventory Objects in this DrivableTrader's inventory
     * @param wishlist  Objects in this DrivableTrader's wishlist
     * @param money     The DrivableTrader's money
     */
    public DriableTrader(List<DT> inventory, List<DT> wishlist,
                  int money) {
        this.inventory = inventory;
        this.wishlist = wishlist;
        this.money = money;
    }

    /* TODO: Add a new constructor that takes a single argument
     *       representing the Trader's money. Give the Trader
     *       empty ArrayLists for their inventory and wishlist.
     */
    
    public DrivableTrader(int money){
        this.inventory = new ArrayList<DT>;
        this.wishlist = new ArrayList<DT>;
        this.money = money;
    }




    /* TODO: Implement the method addToWishlist that takes an
     *       object of type DT and adds it to this Trader's wishlist.
     */

    public void addToWishlist(DT obj){
        this.wishlist.append(obj);
    }



    /* TODO: Implement the method getSellingPrice that takes an
     *       object of type DT and returns the object's price
     *       (via getPrice()) if it's Tradable. If not,
     *       return Tradable.MISSING_PRICE.
     *
     *       We will call this in exchangeMoney().
     */
    
    public int getSellingPrice(DT obj){
        if (type(obj) == Tradable){
            return obj.getPrice();
        }
        else{
            return Tradable.MISSING_PRICE;
        }
    }




    /**
     * Exchange money from other to this Trader according to the price of item,
     * if other has enough money. Otherwise, returns False.
     *
     * @return True if the exchange was completed.
     */
    public boolean exchangeMoney(DrivableTrader<DT> other, DT item) {
        int selling_price = this.getSellingPrice(item);
        if (selling_price == Tradable.MISSING_PRICE) {
            return false;
        }

        if (selling_price <= other.money) {
            other.money -= selling_price;
            this.money += selling_price;
            return true;
        }
        return false;
    }

    /**
     * Attempt to sell all items that are in both this Trader's inventory
     * and in other's wishlist.
     *
     * @return True iff at least one item was sold from this Trader to other
     */
    public boolean sellTo(DrivableTrader<DT> other) {
        boolean sold_at_least_one = false;
        List<DT> temp = new ArrayList<>();

        for (DT item : this.inventory) {
            if (other.wishlist.contains(item) && exchangeMoney(other, item)) {
                temp.add(item);
                sold_at_least_one = true;
            }
        }

        this.inventory.removeAll(temp);
        other.inventory.addAll(temp);
        other.wishlist.removeAll(temp);

        return sold_at_least_one;
    }

    /**
     * Buy items from other.
     *
     * @return True iff at least one item was bought from other.
     */
    public boolean buyFrom(Trader<DT> other) {
        return other.sellTo(this);
    }


    @Override
    public String toString() {
        StringBuilder details = new StringBuilder("-- Inventory --\n");

        for (DT item : this.inventory) {
            details.append(item).append("\n");
        }

        details.append("-- Wishlist --\n");
        for (DT item : this.wishlist) {
            details.append(item).append("\n");
        }

        return details.toString();
    }

    public List<DT> getWishlist(){
        return this.wishlist;
    }

    public List<DT> getInventory(){
        return this.inventory;
    }
    

}
