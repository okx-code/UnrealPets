package sh.okx.unrealpets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import sh.okx.unrealpets.pets.InventoryPet;
import sh.okx.unrealpets.pets.PetType;
import sh.okx.unrealpets.pets.data.LuckyPetData;

public class LuckyTimer extends BukkitRunnable {
  private final UnrealPets plugin;
  private final LuckyPetData data;
  private int seconds = 0;

  public LuckyTimer(UnrealPets plugin) {
    this.plugin = plugin;
    this.data = (LuckyPetData) plugin.getPetData(PetType.LUCKY);
  }

  @Override
  public void run() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      InventoryPet pet = plugin.getPetManager().getPet(player, PetType.LUCKY);
      if (pet != null) {
        int interval = data.getInterval(data.getLevel(pet.getXp()));
        if (seconds % interval == 0) {
          Bukkit.dispatchCommand(Bukkit.getConsoleSender(), data.getReward()
              .replace("%player%", player.getName()));
          player.sendMessage(data.getMessage());
          plugin.getPetManager().giveXp(player, PetType.LUCKY, 1);
        }
      }
    }

    seconds++;
  }
}
