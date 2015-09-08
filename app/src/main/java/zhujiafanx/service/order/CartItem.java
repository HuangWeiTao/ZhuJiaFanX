package zhujiafanx.service.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2015/9/6.
 */
public class CartItem {
    private Cart cart;
    private UUID id;
    private UUID ownerId;
    private UUID productId;
    private String name;
    private int quantity;
    private Date created;
    private BigDecimal price;

    public CartItem(Cart cart, UUID id, Date created, String name, UUID ownerId, BigDecimal price, UUID productId, int quantity) {
        this.cart=cart;
        this.id = id;
        this.created = created;
        this.name = name;
        this.ownerId = ownerId;
        this.price = price;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Date getCreated() {
        return created;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public BigDecimal getSubTotal()
    {
        return price.multiply(new BigDecimal(quantity));
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
