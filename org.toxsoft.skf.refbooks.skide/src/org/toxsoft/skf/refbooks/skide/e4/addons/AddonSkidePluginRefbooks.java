package org.toxsoft.skf.refbooks.skide.e4.addons;

import org.eclipse.e4.core.contexts.*;
import org.toxsoft.core.tsgui.bricks.quant.*;
import org.toxsoft.core.tsgui.mws.bases.*;
import org.toxsoft.skf.refbooks.gui.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.lib.impl.*;
import org.toxsoft.skf.refbooks.skide.*;
import org.toxsoft.skf.refbooks.skide.Activator;
import org.toxsoft.skf.refbooks.skide.main.*;
import org.toxsoft.skide.core.api.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.gui.conn.*;

/**
 * Plugin addon.
 *
 * @author hazard157
 */
public class AddonSkidePluginRefbooks
    extends MwsAbstractAddon {

  /**
   * Constructor.
   */
  public AddonSkidePluginRefbooks() {
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
    ISkideEnvironment skEnv = aAppContext.get( ISkideEnvironment.class );
    skEnv.pluginsRegistrator().registerPlugin( SkidePluginRefbooks.INSTANCE );
  }

  @Override
  protected void initWin( IEclipseContext aWinContext ) {
    ISkidePluginRefbooksConstants.init( aWinContext );
    ISkCoreApi coreApi = aWinContext.get( ISkConnectionSupplier.class ).defConn().coreApi();
    if( !coreApi.services().hasKey( ISkRefbookService.SERVICE_ID ) ) {
      coreApi.addService( SkExtServiceRefbooks.CREATOR );
    }

  }

}
