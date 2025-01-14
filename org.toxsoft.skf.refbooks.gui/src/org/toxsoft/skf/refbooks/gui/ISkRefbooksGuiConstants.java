package org.toxsoft.skf.refbooks.gui;

import static org.toxsoft.skf.refbooks.lib.ISkRefbookService.*;
import static org.toxsoft.skf.refbooks.lib.ISkRefbookServiceHardConstants.*;
import static org.toxsoft.skf.refbooks.gui.km5.ISkResources.*;

import org.eclipse.e4.core.contexts.*;
import org.toxsoft.core.tsgui.graphics.icons.*;
import org.toxsoft.uskat.core.api.users.ability.*;
import org.toxsoft.uskat.core.impl.dto.*;

/**
 * Plugin constants.
 *
 * @author hazard157
 */
@SuppressWarnings( "javadoc" )
public interface ISkRefbooksGuiConstants {

  // ------------------------------------------------------------------------------------
  // Icons

  String PREFIX_OF_ICON_FIELD_NAME = "ICONID_";       //$NON-NLS-1$
  String ICONID_REFBOOK            = "refbook";       //$NON-NLS-1$
  String ICONID_REFBOOKS_LIST      = "refbooks-list"; //$NON-NLS-1$
  String ICONID_REFBOOK_EDIT       = "refbook-edit";  //$NON-NLS-1$
  String ICONID_REFBOOK_ITEM       = "rbitem";        //$NON-NLS-1$
  String ICONID_REFBOOK_ITEMS_LIST = "rbitems-list";  //$NON-NLS-1$

  /**
   * Create id ability to access refbook editor
   */
  String ABILITYID_REFBOOKS_PERSP = SERVICE_ID + ".ability.refbook.editor"; //$NON-NLS-1$

  /**
   * Create ability to access refbook editor
   */
  IDtoSkAbility ABILITY_ACCESS_REFBOOK_EDITOR = DtoSkAbility.create( ABILITYID_REFBOOKS_PERSP, ABKINDID_REFBOOKS,
      STR_ABILITY_ACCESS_REFBOOK_EDITOR, STR_ABILITY_ACCESS_REFBOOK_EDITOR_D );

  /**
   * Constants registration.
   *
   * @param aWinContext {@link IEclipseContext} - windows level context
   */
  static void init( IEclipseContext aWinContext ) {
    ITsIconManager iconManager = aWinContext.get( ITsIconManager.class );
    iconManager.registerStdIconByIds( Activator.PLUGIN_ID, ISkRefbooksGuiConstants.class, PREFIX_OF_ICON_FIELD_NAME );
  }

}
