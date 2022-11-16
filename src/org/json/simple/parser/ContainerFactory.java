package org.json.simple.parser;

import java.util.List;
import java.util.Map;

/**
 * Container Factory Interface.
 */
public interface ContainerFactory {
  /**
   * Create Object Container.
   * @return A Map instance to store JSON object.
   */
  Map createObjectContainer();

  /**
   * Create Array Container.
   * @return A List instance to store JSON array.
   */
  List creatArrayContainer();
}
