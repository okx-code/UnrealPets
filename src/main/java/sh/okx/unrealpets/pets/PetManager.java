package sh.okx.unrealpets.pets;

import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import sh.okx.unrealpets.UnrealPets;
import sh.okx.unrealpets.pets.data.PetData;

import java.util.ListIterator;

@RequiredArgsConstructor
public class PetManager {
  private final UnrealPets plugin;

  public ItemStack getPet(InventoryPet pet) {
    PetData data = plugin.getPetData(pet.getType());

    ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
    SkullMeta meta = (SkullMeta) item.getItemMeta();
    meta.setOwner(data.getHead());

    data.setItemMeta(pet.getXp(), meta);
    item.setItemMeta(meta);

    net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
    NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();

    NBTTagCompound petTag = new NBTTagCompound();
    petTag.setString("type", pet.getType().name());
    petTag.setInt("xp", pet.getXp());

    tag.set("pet", petTag);

    return CraftItemStack.asCraftMirror(nmsStack);
  }

  public InventoryPet getPet(ItemStack item) {
    if (item == null || item.getType() != Material.SKULL_ITEM || item.getDurability() != 3) {
      return null;
    }

    net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
    NBTTagCompound tag = nmsStack.getTag();
    if (!nmsStack.hasTag() || !tag.hasKey("pet")) {
      return null;
    }

    NBTTagCompound pet = tag.getCompound("pet");
    PetType type = PetType.valueOf(pet.getString("type"));
    int xp = pet.getInt("xp");

    return new InventoryPet(type, xp);
  }

  public void giveXp(Player player, PetType type, int xp) {
    ListIterator<ItemStack> it = player.getInventory().iterator();
    while (it.hasNext()) {
      InventoryPet pet = getPet(it.next());
      if (pet != null && pet.getType() == type) {
        pet.setXp(pet.getXp() + xp);
        it.set(getPet(pet));
        break;
      }
    }
  }

  public InventoryPet getPet(Player player, PetType type) {
    for (ItemStack item : player.getInventory()) {
      InventoryPet pet = getPet(item);
      if (pet != null && pet.getType() == type) {
        return pet;
      }
    }
    return null;
  }

  public Player findPet(Location location, int radius, PetType type) {
    InventoryPet pet = null;
    Player player = null;
    for (Entity nearby : location.getWorld().getNearbyEntities(location, radius, radius, radius)) {
      if (nearby instanceof Player) {
        InventoryPet pet1 = plugin.getPetManager().getPet((Player) nearby, type);
        if (pet1 != null && (pet == null || pet1.getXp() > pet.getXp())) {
          pet = pet1;
          player = (Player) nearby;
        }
      }
    }
    return player;
  }
}
