package org.toxsoft.skf.refbooks.mws.e4.addons;

import static org.toxsoft.skf.refbooks.gui.ISkRefbooksGuiConstants.*;
import static org.toxsoft.skf.refbooks.mws.IWsRefooksConstants.*;

import org.eclipse.e4.core.contexts.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.core.tsgui.bricks.quant.*;
import org.toxsoft.core.tsgui.mws.bases.*;
import org.toxsoft.skf.refbooks.gui.*;
import org.toxsoft.skf.refbooks.mws.*;
import org.toxsoft.skf.refbooks.mws.Activator;
import org.toxsoft.skf.refbooks.mws.e4.services.*;
import org.toxsoft.uskat.core.gui.utils.*;
import org.toxsoft.uskat.core.impl.*;

/**
 * The plugin addon.
 *
 * @author hazard157
 */
public class AddonWsRefbooks
    extends MwsAbstractAddon {

  /**
   * Constructor.
   */
  public AddonWsRefbooks() {
    super( Activator.PLUGIN_ID );
  }

  // ------------------------------------------------------------------------------------
  // MwsAbstractAddon
  //

  @Override
  protected void doRegisterQuants( IQuantRegistrator aQuantRegistrator ) {
    aQuantRegistrator.registerQuant( new QuantSkRefbooksGui() );
  }

  @Override
  protected void initApp( IEclipseContext aAppContext ) {
    // nop
  }

  @Override
  protected void initWin( IEclipseContext aWinContext ) {
    IWsRefooksConstants.init( aWinContext );
    //
    IWsRefbooksManagementService rbms = new WsRefbooksManagementService( new TsGuiContext( aWinContext ) );
    aWinContext.set( IWsRefbooksManagementService.class, rbms );
    // implement access rights
    GuiE4ElementsToAbilitiesBinder binder = new GuiE4ElementsToAbilitiesBinder( new TsGuiContext( aWinContext ) );
    binder.bindPerspective( ABILITYID_REFBOOKS_PERSP, E4_VISUAL_ELEM_ID_PERSP_REFBOOKS );
    binder.bindMenuElement( ABILITYID_REFBOOKS_PERSP, E4_VISUAL_ELEM_ID_MENU_ITEEM_REFBOOKS );
    binder.bindToolItem( ABILITYID_REFBOOKS_PERSP, E4_VISUAL_ELEM_ID_TOOL_ITEEM_REFBOOKS );
    SkCoreUtils.registerCoreApiHandler( binder );
  }

}
