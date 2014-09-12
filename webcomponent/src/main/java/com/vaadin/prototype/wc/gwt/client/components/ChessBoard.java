package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.user.client.EventListener;
import com.vaadin.prototype.wc.gwt.client.html.*;

@JsType(prototype = "HTMLElement", isNative = true)
public interface ChessBoard extends HTMLElement {

  Class<?>[] dependencies = new Class<?>[]{};

  /**
   * Example: Move the white queen on the a4 square
   * put("a4", "Q");
   *
   * P // ♙ white pawn
   * N // ♘ white knight
   * B // ♗ white bishop
   * R // ♖ white rook
   * Q // ♕ white queen
   * K // ♔ white king
   * 
   * p // ♟ black pawn
   * n // ♞ black knight
   * b // ♝ black bishop
   * r // ♜ black rook
   * q // ♛ black queen
   * k // ♚ black king
   *
   * @method move
   * @param {string} position
   * @param {string} piece
   */
  void move(String arg0, String arg1);

  /**
   * @method clearBoard
   */
  void clearBoard();

  /**
   * Example: Put the white queen on the a4 square
   * put("a4", "Q");
   *
   * @method put
   * @param {string} position
   * @param {string} piece
   */
  void put(String arg0, String arg1);

  /**
   * @attribute fen
   * @type string
   */
  @JsProperty ChessBoard fen(String val);
  @JsProperty String fen();

  /**
   * @attribute frame
   * @type boolean
   */
  @JsProperty ChessBoard frame(boolean val);
  @JsProperty boolean frame();
}
