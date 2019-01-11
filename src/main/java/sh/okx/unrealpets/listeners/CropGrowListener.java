package sh.okx.unrealpets.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;
import sh.okx.unrealpets.UnrealPets;
import sh.okx.unrealpets.pets.PetType;
import sh.okx.unrealpets.pets.data.RadiusPetData;

@RequiredArgsConstructor
public class CropGrowListener implements Listener {
  private final UnrealPets plugin;

  @EventHandler
  public void on(BlockGrowEvent e) {
    MaterialData materialData = e.getNewState().getData();
    if (!(materialData instanceof Crops)) {
      return;
    }
    Crops crops = (Crops) materialData;
    Location location = e.getBlock().getLocation();
    RadiusPetData data = (RadiusPetData) plugin.getPetData(PetType.FARM);
    int radius = data.getRadius();

    Player player = plugin.getPetManager().findPet(location, radius, PetType.FARM);
    if (player == null) {
      return;
    }

    int level = data.getLevel(plugin.getPetManager().getPet(player, PetType.FARM).getXp());
    if (data.getBonus(level) > Math.random()) {
      crops.setState(getNextState(crops.getState()));
    }

    plugin.getPetManager().giveXp(player, PetType.FARM, 1);
  }

  private CropState getNextState(CropState state) {
    switch (state) {
      case SEEDED:
        return CropState.GERMINATED;
      case GERMINATED:
        return CropState.VERY_SMALL;
      case VERY_SMALL:
        return CropState.SMALL;
      case SMALL:
        return CropState.MEDIUM;
      case MEDIUM:
        return CropState.TALL;
      case TALL:
        return CropState.VERY_TALL;
      default:
        return CropState.RIPE;
    }
  }
}
