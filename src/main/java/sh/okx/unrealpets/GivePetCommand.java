package sh.okx.unrealpets;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sh.okx.unrealpets.pets.InventoryPet;
import sh.okx.unrealpets.pets.PetManager;
import sh.okx.unrealpets.pets.PetType;

@RequiredArgsConstructor
public class GivePetCommand implements CommandExecutor {
  private final PetManager pets;

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length < 1) {
      return false;
    }

    Player give;
    if (args.length < 2) {
      if (sender instanceof Player) {
        give = (Player) sender;
      } else {
        return false;
      }
    } else {
      give = Bukkit.getPlayer(args[1]);
      if (give == null) {
        sender.sendMessage(ChatColor.RED + "Invalid player");
        return true;
      }
    }

    PetType type = PetType.valueOf(args[0].toUpperCase());

    give.getInventory().addItem(pets.getPet(new InventoryPet(type, 0)));
    sender.sendMessage(ChatColor.GREEN + "Gave " + give.getName() + " a level 1 " + type + " pet");
    return true;
  }
}
