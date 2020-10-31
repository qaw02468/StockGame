package fluctuationpricestrategies;

import java.io.Serializable;

public interface FluctuationPriceStrategy extends Serializable{

    void fluctuatePrice();
}
