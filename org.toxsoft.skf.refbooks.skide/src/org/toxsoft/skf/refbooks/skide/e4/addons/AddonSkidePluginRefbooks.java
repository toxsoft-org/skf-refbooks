package org.toxsoft.skf.refbooks.skide.e4.addons;

import org.eclipse.e4.core.contexts.*;
import org.toxsoft.core.tsgui.bricks.quant.*;
import org.toxsoft.core.tsgui.mws.bases.*;
import org.toxsoft.skf.refbooks.gui.*;
import org.toxsoft.skf.refbooks.skide.*;
import org.toxsoft.skf.refbooks.skide.Activator;
import org.toxsoft.skf.refbooks.skide.main.*;
import org.toxsoft.skide.core.api.*;

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
  }

}
