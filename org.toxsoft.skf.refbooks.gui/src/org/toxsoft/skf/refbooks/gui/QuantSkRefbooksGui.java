package org.toxsoft.skf.refbooks.gui;

import org.eclipse.e4.core.contexts.*;
import org.toxsoft.core.tsgui.bricks.quant.*;
import org.toxsoft.skf.refbooks.gui.km5.*;
import org.toxsoft.skf.refbooks.lib.impl.*;
import org.toxsoft.uskat.core.api.*;
import org.toxsoft.uskat.core.devapi.*;
import org.toxsoft.uskat.core.gui.km5.*;
import org.toxsoft.uskat.core.impl.*;

/**
 * The library quant.
 *
 * @author hazard157
 */
public class QuantSkRefbooksGui
    extends AbstractQuant
    implements ISkCoreExternalHandler {

  /**
   * Constructor.
   */
  public QuantSkRefbooksGui() {
    super( QuantSkRefbooksGui.class.getSimpleName() );
    SkCoreUtils.registerSkServiceCreator( SkExtServiceRefbooks.CREATOR );
    KM5Utils.registerContributorCreator( KM5RefbooksContributor.CREATOR );
    SkCoreUtils.registerCoreApiHandler( this );
  }

  // ------------------------------------------------------------------------------------
  // AbstractQuant
  //

  @Override
  protected void doInitApp( IEclipseContext aAppContext ) {
    // nop
  }

  @Override
  protected void doInitWin( IEclipseContext aWinContext ) {
    ISkRefbooksGuiConstants.init( aWinContext );
  }

  @Override
  public void processSkCoreInitialization( IDevCoreApi aCoreApi ) {
    // register abilities
    aCoreApi.userService().abilityManager().defineAbility( ISkRefbooksGuiConstants.ABILITY_ACCESS_REFBOOK_EDITOR );
  }
}
