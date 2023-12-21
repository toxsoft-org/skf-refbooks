package org.toxsoft.skf.refbooks.mws.e4.uiparts;

import static org.toxsoft.core.tsgui.m5.gui.mpc.IMultiPaneComponentConstants.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;

import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.core.tsgui.widgets.*;
import org.toxsoft.skf.refbooks.gui.glib.*;
import org.toxsoft.skf.refbooks.mws.e4.services.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.gui.e4.uiparts.*;

/**
 * Left part in perspective - a list view of refbooks.
 *
 * @author hazard157
 */
public class UipartRefbooksList
    extends SkMwsAbstractPart {

  private IWsRefbooksManagementService rbms;
  private RefbooksListPanel            refbooksListPanel;

  // private IM5CollectionPanel<ISkRefbook> panel;

  @Override
  protected void doCreateContent( TsComposite aParent ) {
    rbms = tsContext().get( IWsRefbooksManagementService.class );
    ITsGuiContext ctx = new TsGuiContext( tsContext() );
    OPDEF_DBLCLICK_ACTION_ID.setValue( ctx.params(), AV_STR_EMPTY );
    refbooksListPanel = new RefbooksListPanel( aParent, ctx, ISkConnectionSupplier.DEF_CONN_ID, false );
    refbooksListPanel.addTsDoubleClickListener( ( src, sel ) -> rbms.showRefbookUipart( sel ) );

    // IM5Model<ISkRefbook> model = m5().getModel( MID_KM5RB_REFBOOK, ISkRefbook.class );
    // IM5LifecycleManager<ISkRefbook> lm = model.getLifecycleManager( skConn() );
    // ITsGuiContext ctx = new TsGuiContext( tsContext() );
    // panel = model.panelCreator().createCollEditPanel( ctx, lm.itemsProvider(), lm );
    // panel.createControl( aParent );
    // panel.addTsDoubleClickListener( ( src, sel ) -> rbms.showRefbookUipart( sel ) );
  }

}
