package sh.okx.unrealpets.pets.data;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

public class RadiusPetData extends BonusPetData {
  @Getter
  private final int radius;

  public RadiusPetData(ConfigurationSection section) {
    super(section);
    this.radius = section.getInt("radius");
  }
}
