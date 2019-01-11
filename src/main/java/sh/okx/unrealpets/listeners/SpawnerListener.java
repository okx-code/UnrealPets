package sh.okx.unrealpets.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import sh.okx.unrealpets.UnrealPets;
import sh.okx.unrealpets.pets.InventoryPet;
import sh.okx.unrealpets.pets.PetType;
import sh.okx.unrealpets.pets.data.RadiusPetData;

@RequiredArgsConstructor
public class SpawnerListener implements Listener {
  private final UnrealPets plugin;

  @EventHandler
  public void on(SpawnerSpawnEvent e) {
    CreatureSpawner spawner = e.getSpawner();
    Location location = spawner.getLocation();
    RadiusPetData data = (RadiusPetData) plugin.getPetData(PetType.SPAWNER);
    int radius = data.getRadius();

    Player player = plugin.getPetManager().findPet(location, radius, PetType.SPAWNER);
    if (player == null) {
      return;
    }

    int level = data.getLevel(plugin.getPetManager().getPet(player, PetType.SPAWNER).getXp());
    float bonus = data.getBonus(level);

    Bukkit.getScheduler().runTaskLater(plugin, () -> {
      CreatureSpawner state = (CreatureSpawner) location.getBlock().getState();
      state.setDelay((int) (state.getDelay() * bonus));
      state.update();
    },  1);

    plugin.getPetManager().giveXp(player, PetType.SPAWNER, 1);
  }
}
