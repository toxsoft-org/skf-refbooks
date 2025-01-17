package org.toxsoft.skf.refbooks.mws;

import static org.toxsoft.core.tslib.ITsHardConstants.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.skf.refbooks.mws.ISkResources.*;

import org.eclipse.e4.core.contexts.*;
import org.toxsoft.core.tsgui.bricks.actions.*;
import org.toxsoft.core.tsgui.graphics.icons.*;
import org.toxsoft.core.tslib.av.impl.*;
import org.toxsoft.core.tslib.av.metainfo.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.bricks.apprefs.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.strid.coll.impl.*;

/**
 * Application common constants.
 *
 * @author hazard157
 */
@SuppressWarnings( "javadoc" )
public interface IWsRefooksConstants {

  // visual elements ids
  String E4_VISUAL_ELEM_ID_PERSP_REFBOOKS      = "org.toxsoft.ws.refbooks.persp.main";                          //$NON-NLS-1$
  String E4_VISUAL_ELEM_ID_MENU_ITEEM_REFBOOKS = "org.toxsoft.skf.refbooks.mws.handledmenuitem.persp_refbooks"; //$NON-NLS-1$
  String E4_VISUAL_ELEM_ID_TOOL_ITEEM_REFBOOKS = "org.toxsoft.skf.refbooks.mws.handledtoolitem.persp_refbooks"; //$NON-NLS-1$
  // ------------------------------------------------------------------------------------
  // WS_RB

  String WS_RB_ID      = "wsrb";                //$NON-NLS-1$
  String WS_RB_FULL_ID = TS_FULL_ID + ".myapp"; //$NON-NLS-1$
  String WS_RB_M5_ID   = WS_RB_ID + ".m5";      //$NON-NLS-1$
  String WS_RB_ACT_ID  = WS_RB_ID + ".act";     //$NON-NLS-1$

  // ------------------------------------------------------------------------------------
  // E4

  String PERSPID_WS_RB_MAIN        = "org.toxsoft.ws.refbooks.persp.main";         //$NON-NLS-1$
  String PARTSTACKID_REFBOOKS_MAIN = "org.toxsoft.ws.refbooks.partstack.main";     //$NON-NLS-1$
  String PARTID_REFBOOKS_LIST      = "org.toxsoft.ws.refbooks.part.refbooks_list"; //$NON-NLS-1$

  // ------------------------------------------------------------------------------------
  // Icons

  String PREFIX_OF_ICON_FIELD_NAME = "ICONID_"; //$NON-NLS-1$
  String ICONID_HZ_LOGO            = "hz-logo"; //$NON-NLS-1$

  // ------------------------------------------------------------------------------------
  // Actions

  String ACTID_DO_IT = WS_RB_ACT_ID + ".do_it"; //$NON-NLS-1$

  ITsActionDef ACDEF_DO_IT = TsActionDef.ofPush2( ACTID_DO_IT, //
      STR_DO_IT, STR_DO_IT_D, ICONID_HZ_LOGO );

  // ------------------------------------------------------------------------------------
  // Application preferences

  String PBID_WS_RB_MAIN = PERSPID_WS_RB_MAIN;

  IDataDef APPREF_CONFIRM_ACTIONS = DataDef.create3( WS_RB_ID + "SampleConfirmActions", DDEF_TS_BOOL, //$NON-NLS-1$
      TSID_DEFAULT_VALUE, AV_TRUE, //
      TSID_NAME, STR_CONFIRM_ACTIONS, //
      TSID_DESCRIPTION, STR_CONFIRM_ACTIONS_D //
  );

  IStridablesList<IDataDef> ALL_APREFS = new StridablesList<>( //
      APPREF_CONFIRM_ACTIONS //
  );

  /**
   * Constants registration.
   *
   * @param aWinContext {@link IEclipseContext} - windows level context
   */
  static void init( IEclipseContext aWinContext ) {
    ITsIconManager iconManager = aWinContext.get( ITsIconManager.class );
    iconManager.registerStdIconByIds( Activator.PLUGIN_ID, IWsRefooksConstants.class, PREFIX_OF_ICON_FIELD_NAME );
    //
    IAppPreferences aprefs = aWinContext.get( IAppPreferences.class );
    IPrefBundle pb = aprefs.defineBundle( PBID_WS_RB_MAIN, OptionSetUtils.createOpSet( //
        TSID_NAME, STR_PB_WS_RB_MAIN, //
        TSID_DESCRIPTION, STR_PB_WS_RB_MAIN_D, //
        TSID_ICON_ID, ICONID_HZ_LOGO//
    ) );
    for( IDataDef dd : ALL_APREFS ) {
      pb.defineOption( dd );
    }
  }

}
