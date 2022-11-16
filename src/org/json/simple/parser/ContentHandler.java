package org.json.simple.parser;

import java.io.IOException;


/**
 * Interface for ContentHandler.
 */
public interface ContentHandler {
  /**
   * Receive notification of the beginning of JSON processing.
   * The parser will invoke this method only once.
   *
   * @throws ParseException - JSONParser will stop and throw the same exception to the caller.
   */
  void startJSON() throws /**/ParseException, IOException;

  /**
   * Receive notification of the end of JSON processing.
   *
   * @throws ParseException if parsing goes wrong
   */
  void endJSON() throws ParseException, IOException;

  /**
   * Receive notification of the beginning of a JSON object.
   *
   * @return false if the handler wants to stop parsing after return.
   * @see #endJSON
   */
  boolean startObject() throws ParseException, IOException;

  /**
   * Receive notification of the end of a JSON object.
   *
   * @return false if the handler wants to stop parsing after return.
   * @throws ParseException
   *
   * @see #startObject
   */
  boolean endObject() throws ParseException, IOException;

  /**
   * Receive notification of the beginning of a JSON object entry.
   *
   * @param key - Key of a JSON object entry.
   *
   * @return false if the handler wants to stop parsing after return.
   * @throws ParseException
   *
   * @see #endObjectEntry
   */
  boolean startObjectEntry(String key) throws ParseException, IOException;

  /**
   * Receive notification of the end of the value of previous object entry.
   *
   * @return false if the handler wants to stop parsing after return.
   * @throws ParseException
   *
   * @see #startObjectEntry
   */
  boolean endObjectEntry() throws ParseException, IOException;

  /**
   * Receive notification of the beginning of a JSON array.
   *
   * @return false if the handler wants to stop parsing after return.
   * @throws ParseException
   *
   * @see #endArray
   */
  boolean startArray() throws ParseException, IOException;

  /**
   * Receive notification of the end of a JSON array.
   *
   * @return false if the handler wants to stop parsing after return.
   * @throws ParseException
   *
   * @see #startArray
   */
  boolean endArray() throws ParseException, IOException;

  /**
   * Receive notification of the JSON primitive values:
   * java.lang.String,
   * java.lang.Number,
   * java.lang.Boolean,
   * null.
   *
   * @return false if the handler wants to stop parsing after return.
   * @throws ParseException if invalid value is given.
   */
  boolean primitive(Object value) throws ParseException, IOException;

}
