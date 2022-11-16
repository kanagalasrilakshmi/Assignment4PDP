package model.customJSON.parser;

import java.util.List;
import java.util.Map;

public interface ContainerFactory {
  /**
   * @return A Map instance to store JSON object.
   */
  Map createObjectContainer();

  /**
   * @return A List instance to store JSON array.
   */
  List creatArrayContainer();
}
