package org.toxsoft.skf.refbooks.skide.main;

import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.skf.refbooks.gui.glib.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.lib.impl.*;
import org.toxsoft.skide.core.api.*;
import org.toxsoft.skide.core.api.impl.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.gui.conn.*;

/**
 * {@link AbstractSkideUnitPanel} implementation.
 *
 * @author hazard157
 * @author dima
 */
class SkideUnitRefbooksPanel
    extends AbstractSkideUnitPanel {

  public SkideUnitRefbooksPanel( ITsGuiContext aContext, ISkideUnit aUnit ) {
    super( aContext, aUnit );
  }

  @Override
  protected Control doCreateControl( Composite aParent ) {
    ISkCoreApi coreApi = tsContext().get( ISkConnectionSupplier.class ).defConn().coreApi();
    if( !coreApi.services().hasKey( ISkRefbookService.SERVICE_ID ) ) {
      coreApi.addService( SkExtServiceRefbooks.CREATOR );
    }

    ITsGuiContext ctx = new TsGuiContext( tsContext() );
    PanelRefbookItemsEditor panel = new PanelRefbookItemsEditor( aParent, ctx, ISkConnectionSupplier.DEF_CONN_ID );
    return panel;
  }

}
