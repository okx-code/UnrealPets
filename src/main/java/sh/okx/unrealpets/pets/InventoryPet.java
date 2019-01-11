package sh.okx.unrealpets.pets;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryPet {
  private PetType type;
  private int xp;
}
