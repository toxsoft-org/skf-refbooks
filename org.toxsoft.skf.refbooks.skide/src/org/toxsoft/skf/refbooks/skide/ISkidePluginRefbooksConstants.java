package org.toxsoft.skf.refbooks.skide;

import org.eclipse.e4.core.contexts.*;
import org.toxsoft.core.tsgui.graphics.icons.*;

/**
 * Application common constants.
 *
 * @author hazard157
 */
@SuppressWarnings( "javadoc" )
public interface ISkidePluginRefbooksConstants {

  // ------------------------------------------------------------------------------------
  // Icons

  String PREFIX_OF_ICON_FIELD_NAME    = "ICONID_";             //$NON-NLS-1$
  String ICONID_SKIDE_PLUGIN_REFBOOKS = "app-refbooks";        //$NON-NLS-1$
  String ICONID_SKIDE_UNIT_REFBOOKS   = "skide-unit-refbooks"; //$NON-NLS-1$

  /**
   * Constants registration.
   *
   * @param aWinContext {@link IEclipseContext} - windows level context
   */
  static void init( IEclipseContext aWinContext ) {
    ITsIconManager iconManager = aWinContext.get( ITsIconManager.class );
    iconManager.registerStdIconByIds( Activator.PLUGIN_ID, ISkidePluginRefbooksConstants.class,
        PREFIX_OF_ICON_FIELD_NAME );
    //
  }

}
