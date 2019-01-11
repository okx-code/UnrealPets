package sh.okx.unrealpets.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import sh.okx.unrealpets.pets.PetManager;
import sh.okx.unrealpets.pets.PetType;

@RequiredArgsConstructor
public class EntityDeathListener implements Listener {
  private final PetManager pets;

  @EventHandler
  public void on(EntityDeathEvent e) {
    LivingEntity entity = e.getEntity();
    Player killer = entity.getKiller();
    if (killer == null) {
      return;
    }

    pets.giveXp(killer, PetType.XP, 1);
  }
}
