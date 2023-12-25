package org.toxsoft.skf.refbooks.skide.main;

import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.skf.refbooks.gui.glib.*;
import org.toxsoft.skide.core.api.*;
import org.toxsoft.skide.core.api.impl.*;
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
    ITsGuiContext ctx = new TsGuiContext( tsContext() );
    PanelRefbooksEditor panel = new PanelRefbooksEditor( aParent, ctx, ISkConnectionSupplier.DEF_CONN_ID );
    return panel;
  }

}
