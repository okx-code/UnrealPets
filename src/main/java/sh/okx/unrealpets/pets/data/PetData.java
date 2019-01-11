package sh.okx.unrealpets.pets.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import sh.okx.unrealpets.pets.InventoryPet;

import java.util.ArrayList;
import java.util.List;

public class PetData {
  @Getter
  protected final String head;
  protected final String name;
  protected final List<String> lore;
  @Getter
  protected final List<Integer> levelUp = new ArrayList<>();

  public PetData(ConfigurationSection section) {
    this.head = section.getString("head");
    this.name = section.getString("name");
    this.lore = section.getStringList("lore");
    for (String level : section.getStringList("level-up")) {
      levelUp.add(Integer.parseInt(level));
    }
  }

  public void setItemMeta(int xp, ItemMeta meta) {
    meta.setDisplayName(replace(xp, name));
    List<String> lore = new ArrayList<>();
    for (String line : this.lore) {
      lore.add(replace(xp, line));
    }
    meta.setLore(lore);
  }

  private String replace(int xp, String s) {
    int level = getLevel(xp);
    boolean max = level == getLevels();
    s = s.replace("%level%", String.valueOf(level))
        .replace("%maxlevel%", String.valueOf(getLevels()))
        .replace("%xp%", String.valueOf(max ? getXpForLevel(level) : getCurrentXp(xp)))
        .replace("%levelxp%", String.valueOf(getXpForLevel(max ? level : level + 1)));
    return ChatColor.translateAlternateColorCodes('&', s);
  }

  public int getLevels() {
    return levelUp.size() + 1;
  }

  public int getLevel(int xp) {
    int totalXp = 0;
    for (int i = 0; i < levelUp.size(); i++) {
      totalXp += levelUp.get(i);
      if (totalXp > xp) {
        return i + 1;
      }
    }
    return getLevels();
  }

  public int getXpForLevel(int level) {
    if (level == 1) {
      return 0;
    }
    return levelUp.get(level - 2);
  }

  public int getCurrentXp(int xp) {
    for (int i = 0; i < levelUp.size(); i++) {
      if (xp - levelUp.get(i) < 0) {
        return xp;
      }
      xp -= levelUp.get(i);
    }
    return xp;
  }
}
