package org.toxsoft.skf.refbooks.lib.impl;

import org.toxsoft.core.tslib.av.*;
import org.toxsoft.core.tslib.coll.helpers.*;

/**
 * Refbooks service implementation interbal constants.
 *
 * @author hazard157
 */
interface ISkRefbookInternalConstants {

  // ------------------------------------------------------------------------------------
  // Sibling messages

  String MSGARGID_RBCRUD_OP  = "crudOp";    //$NON-NLS-1$
  String MSGARGID_REFBOOK_ID = "refbookId"; //$NON-NLS-1$

  /**
   * Message for siblings: refbook CRUD operation happened.
   * <p>
   * Notes: this message does <b>not</b> happens on any editing of the refbook items.
   * <p>
   * Arguments:
   * <ul>
   * <li>{@link #MSGARGID_RBCRUD_OP} - CRUD operation, {@link EAtomicType#VALOBJ}, contains {@link ECrudOp};</li>
   * <li>{@link #MSGARGID_REFBOOK_ID} - refbook ID, {@link EAtomicType#STRING}.</li>
   * </ul>
   * ID argument may be absent or be an empty string for CRUD operation {@link ECrudOp#LIST}.
   */
  String MSGID_REFBOOK_CRUD = "refbookCrud"; //$NON-NLS-1$

  // /**
  // * Message for siblings: refbook item CRUD operation happened.
  // * <p>
  // * Arguments:
  // * <ul>
  // * <li>{@link #MSGARGID_REFBOOK_ID} - refbook ID, {@link EAtomicType#STRING}.</li>
  // * <li>{@link #MSGARGID_REFBOOK_ID} - refbook ID, {@link EAtomicType#STRING}.</li>
  // * <li>all IDs of event parameters listed in ISkRSHC.</li>
  // * </ul>
  // * ID argument may be absent or be an empty string for CRUD operation {@link ECrudOp#LIST}.<br>
  // * SKID argument may be absent or be {@link Skid#NONE} for CRUD operation {@link ECrudOp#LIST}.<br>
  // */
  // String MSGID_ITEM_CRUD = "itemCrud"; //$NON-NLS-1$

}
