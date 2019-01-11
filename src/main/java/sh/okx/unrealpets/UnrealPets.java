package sh.okx.unrealpets;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import sh.okx.unrealpets.listeners.CropGrowListener;
import sh.okx.unrealpets.listeners.EntityDeathListener;
import sh.okx.unrealpets.listeners.GainXpListener;
import sh.okx.unrealpets.listeners.PetPlaceListener;
import sh.okx.unrealpets.listeners.SellListener;
import sh.okx.unrealpets.listeners.SpawnerListener;
import sh.okx.unrealpets.pets.PetManager;
import sh.okx.unrealpets.pets.PetType;
import sh.okx.unrealpets.pets.data.BonusPetData;
import sh.okx.unrealpets.pets.data.LuckyPetData;
import sh.okx.unrealpets.pets.data.PetData;
import sh.okx.unrealpets.pets.data.RadiusPetData;

import java.util.HashMap;
import java.util.Map;

public class UnrealPets extends JavaPlugin {
  private final Map<PetType, PetData> petData = new HashMap<>();
  @Getter
  private PetManager petManager;

  @Override
  public void onEnable() {
    boolean shopGuiPlus = Bukkit.getPluginManager().isPluginEnabled("ShopGUIPlus");

    saveDefaultConfig();

    ConfigurationSection pets = getConfig().getConfigurationSection("pets");
    petData.put(PetType.XP, new BonusPetData(pets.getConfigurationSection("xp")));
    if (shopGuiPlus) {
      petData.put(PetType.MONEY, new BonusPetData(pets.getConfigurationSection("money")));
    }
    petData.put(PetType.SPAWNER, new RadiusPetData(pets.getConfigurationSection("spawner")));
    petData.put(PetType.FARM, new RadiusPetData(pets.getConfigurationSection("farm")));
    petData.put(PetType.LUCKY, new LuckyPetData(pets.getConfigurationSection("lucky")));

    petManager = new PetManager(this);

    getCommand("givepet").setExecutor(new GivePetCommand(petManager));

    PluginManager pluginManager = getServer().getPluginManager();
    pluginManager.registerEvents(new PetPlaceListener(petManager), this);
    pluginManager.registerEvents(new EntityDeathListener(petManager), this);
    pluginManager.registerEvents(new GainXpListener(this), this);
    if (shopGuiPlus) {
      pluginManager.registerEvents(new SellListener(this), this);
    }
    pluginManager.registerEvents(new SpawnerListener(this), this);
    pluginManager.registerEvents(new CropGrowListener(this), this);

    new LuckyTimer(this).runTaskTimer(this, 20, 20);
  }

  public PetData getPetData(PetType type) {
    return petData.get(type);
  }
}
