package sh.okx.unrealpets.pets.data;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PetDataTest {
  private PetData data;

  @Before
  public void setup() {
    ConfigurationSection section = new MemoryConfiguration();
    section.set("name", "test");


    data = new PetData(section);
    data.getLevelUp().add(1);
    data.getLevelUp().add(5);
  }

  @Test
  public void getLevels() {
    assertEquals(3, data.getLevels());
  }

  @Test
  public void getLevel() {
    assertEquals(1, data.getLevel(0));
    assertEquals(2, data.getLevel(1));
    assertEquals(2, data.getLevel(5));
    assertEquals(3, data.getLevel(6));
    assertEquals(3, data.getLevel(100));
  }

  @Test
  public void getXpForLevel() {
    assertEquals(1, data.getXpForLevel(2));
    assertEquals(5, data.getXpForLevel(3));
  }

  @Test
  public void getCurrentXp() {
    assertEquals(0, data.getCurrentXp(0));
    assertEquals(2, data.getCurrentXp(3));
    assertEquals(0, data.getCurrentXp(6));
    assertEquals(10, data.getCurrentXp(16));
  }
}