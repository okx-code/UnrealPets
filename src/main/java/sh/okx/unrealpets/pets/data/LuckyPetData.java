package sh.okx.unrealpets.pets.data;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LuckyPetData extends PetData {
  private final List<Integer> intervals = new ArrayList<>();
  private final List<String> rewards;
  @Getter
  private final String message;

  public LuckyPetData(ConfigurationSection section) {
    super(section);
    for (String s : section.getStringList("time")) {
      intervals.add(Integer.parseInt(s));
    }
    rewards = section.getStringList("rewards");
    message = ChatColor.translateAlternateColorCodes('&', section.getString("message"));
  }

  public String getReward() {
    return rewards.get(ThreadLocalRandom.current().nextInt(rewards.size()));
  }

  public int getInterval(int level) {
    return intervals.get(level - 1);
  }
}
