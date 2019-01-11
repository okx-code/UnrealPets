package sh.okx.unrealpets.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import sh.okx.unrealpets.pets.PetManager;

@RequiredArgsConstructor
public class PetPlaceListener implements Listener {
  private final PetManager pets;

  @EventHandler
  public void on(BlockPlaceEvent e) {
    ItemStack item = e.getItemInHand();
    if (pets.getPet(item) != null) {
      e.setCancelled(true);
    }
  }
}
