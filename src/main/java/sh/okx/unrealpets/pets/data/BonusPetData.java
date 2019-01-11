package sh.okx.unrealpets.pets.data;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class BonusPetData extends PetData {
  private final List<Float> bonus = new ArrayList<>();

  public BonusPetData(ConfigurationSection section) {
    super(section);
    for (String multiplier : section.getStringList("bonus")) {
      bonus.add(Float.parseFloat(multiplier));
    }
  }

  public float getBonus(int level) {
    return bonus.get(level - 1);
  }
}
