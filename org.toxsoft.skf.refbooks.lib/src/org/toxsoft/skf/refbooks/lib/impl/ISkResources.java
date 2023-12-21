package org.toxsoft.skf.refbooks.lib.impl;

/**
 * Localizable resources.
 *
 * @author hazard157
 */
interface ISkResources {

  /**
   * {@link SkExtServiceRefbooks}
   */
  String FMT_ERR_CLASS_IS_REFBOOK_OWNED     = Messages.getString( "FMT_ERR_CLASS_IS_REFBOOK_OWNED" );     //$NON-NLS-1$
  String FMT_ERR_OBJ_CLASS_IS_REFBOOK_OWNED = Messages.getString( "FMT_ERR_OBJ_CLASS_IS_REFBOOK_OWNED" ); //$NON-NLS-1$
  String STR_N_ATTR_ITEM_CLASS_ID           = Messages.getString( "STR_N_ATTR_ITEM_CLASS_ID" );           //$NON-NLS-1$
  String STR_D_ATTR_ITEM_CLASS_ID           = Messages.getString( "STR_D_ATTR_ITEM_CLASS_ID" );           //$NON-NLS-1$
  String FMT_ERR_REFBOOK_ALREADY_EXISTS     = Messages.getString( "FMT_ERR_REFBOOK_ALREADY_EXISTS" );     //$NON-NLS-1$
  String FMT_WARN_RB_NAME_ALREADY_EXISTS    = Messages.getString( "FMT_WARN_RB_NAME_ALREADY_EXISTS" );    //$NON-NLS-1$
  String FMT_ERR_REFBOOK_NOT_EXISTS         = Messages.getString( "FMT_ERR_REFBOOK_NOT_EXISTS" );         //$NON-NLS-1$
  String FMT_ERR_ITEM_ALREADY_EXISTS        = Messages.getString( "FMT_ERR_ITEM_ALREADY_EXISTS" );        //$NON-NLS-1$
  String FMT_WARN_ITEM_NAME_ALREADY_EXISTS  = Messages.getString( "FMT_WARN_ITEM_NAME_ALREADY_EXISTS" );  //$NON-NLS-1$
  String FMT_ERR_ITEM_NOT_EXISTS            = Messages.getString( "FMT_ERR_ITEM_NOT_EXISTS" );            //$NON-NLS-1$
  String FMT_ERR_NO_SUCH_LINK               = Messages.getString( "FMT_ERR_NO_SUCH_LINK" );               //$NON-NLS-1$
  String FMT_ERR_INV_LINK                   = Messages.getString( "FMT_ERR_INV_LINK" );                   //$NON-NLS-1$

  // ------------------------------------------------------------------------------------
  // USkat entities are defined only in English, l10n done via USkat localization service

  String STR_REFBOOK_CLASS   = "Refbook";                                                                      //$NON-NLS-1$
  String STR_REFBOOK_CLASS_D = "The refbook is collection of items with the same, refbook-specific structure"; //$NON-NLS-1$

}
