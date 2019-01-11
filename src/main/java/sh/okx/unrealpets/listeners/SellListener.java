package sh.okx.unrealpets.listeners;

import lombok.RequiredArgsConstructor;
import net.brcdev.shopgui.api.event.ShopPreTransactionEvent;
import net.brcdev.shopgui.shop.ShopManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import sh.okx.unrealpets.UnrealPets;
import sh.okx.unrealpets.pets.InventoryPet;
import sh.okx.unrealpets.pets.PetType;
import sh.okx.unrealpets.pets.data.BonusPetData;

@RequiredArgsConstructor
public class SellListener implements Listener {
  private final UnrealPets plugin;

  @EventHandler
  public void on(ShopPreTransactionEvent e) {
    if (e.getShopAction() == ShopManager.ShopAction.BUY) {
      return;
    }

    InventoryPet pet = plugin.getPetManager().getPet(e.getPlayer(), PetType.MONEY);
    if (pet == null) {
      return;
    }

    BonusPetData data = (BonusPetData) plugin.getPetData(PetType.MONEY);
    int level = data.getLevel(pet.getXp());
    double price = e.getPrice() * data.getBonus(level);

    e.setPrice(price);

    plugin.getPetManager().giveXp(e.getPlayer(), PetType.MONEY, 1);
  }
}
