package sh.okx.unrealpets;

import net.brcdev.shopgui.api.event.ShopPreTransactionEvent;
import net.brcdev.shopgui.shop.ShopManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopListener implements Listener {
  @EventHandler
  public void on(ShopPreTransactionEvent e) {
    if (e.getShopAction() == ShopManager.ShopAction.BUY) {
      // buy
    } else {
      // sell
    }
  }
}
