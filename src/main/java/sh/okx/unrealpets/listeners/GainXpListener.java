package sh.okx.unrealpets.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import sh.okx.unrealpets.UnrealPets;
import sh.okx.unrealpets.pets.InventoryPet;
import sh.okx.unrealpets.pets.PetType;
import sh.okx.unrealpets.pets.data.BonusPetData;

@RequiredArgsConstructor
public class GainXpListener implements Listener {
  private final UnrealPets plugin;

  @EventHandler
  public void on(PlayerExpChangeEvent e) {
    InventoryPet pet = plugin.getPetManager().getPet(e.getPlayer(), PetType.XP);
    if (pet != null) {
      BonusPetData data = (BonusPetData) plugin.getPetData(PetType.XP);
      int level = data.getLevel(pet.getXp());
      float multiplier = data.getBonus(level);

      int amount = e.getAmount();
      if (amount > 0) {
        e.setAmount((int) (amount * multiplier));
      }
    }

  }
}
