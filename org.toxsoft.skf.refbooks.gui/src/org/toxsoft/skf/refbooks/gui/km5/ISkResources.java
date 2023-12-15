package org.toxsoft.skf.refbooks.gui.km5;

/**
 * Localizable resources.
 *
 * @author hazard157
 */
interface ISkResources {

  String STR_REFBOOK        = Messages.getString( "STR_REFBOOK" );   //$NON-NLS-1$
  String STR_REFBOOK_D      = Messages.getString( "STR_REFBOOK_D" ); //$NON-NLS-1$
  String STR_N_REFBOOK_ID   = "Refbook id";
  String STR_D_REFBOOK_ID   = "Refbook id";
  String STR_N_REFBOOK_NAME = "Name";
  String STR_D_REFBOOK_NAME = "Name of refbook";

  /**
   * {@link SkRefbookItemM5LifecycleManager}
   */
  String FMT_ERR_ITEM_ALREADY_EXISTS = "Element with id %s already exist";

  /**
   * {@link SkRefbookItemM5Mpc}
   */
  String STR_ACT_PRINT_REFBOOK      = "Print refbook content";
  String STR_ACT_PRINT_REFBOOK_D    = "Print refbook content";
  String AUTHOR_STR                 = "Author: ";
  String DATE_STR                   = "printed: ";
  String PRINT_REFBOOK_TITLE_FORMAT = "Refbook: %s";

}
