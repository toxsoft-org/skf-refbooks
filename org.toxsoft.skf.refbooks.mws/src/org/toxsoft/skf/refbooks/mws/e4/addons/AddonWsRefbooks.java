package org.toxsoft.skf.refbooks.mws.e4.addons;

import org.eclipse.e4.core.contexts.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.core.tsgui.bricks.quant.*;
import org.toxsoft.core.tsgui.mws.bases.*;
import org.toxsoft.skf.refbooks.gui.*;
import org.toxsoft.skf.refbooks.mws.*;
import org.toxsoft.skf.refbooks.mws.Activator;
import org.toxsoft.skf.refbooks.mws.e4.services.*;

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
  }

}
