package org.toxsoft.skf.refbooks.mws.e4.uiparts;

import static org.toxsoft.core.tsgui.m5.gui.mpc.IMultiPaneComponentConstants.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;

import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.core.tsgui.widgets.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.skf.refbooks.gui.glib.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.mws.e4.services.*;
import org.toxsoft.uskat.core.api.evserv.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.gui.e4.uiparts.*;

/**
 * Left part in perspective - a list view of refbooks.
 *
 * @author hazard157
 */
public class UipartRefbooksList
    extends SkMwsAbstractPart {

  /**
   * Refreshes {@link #refbooksListPanel} on changes in refbook service.
   */
  private final ISkRefbookServiceListener refbookServiceListener = new ISkRefbookServiceListener() {

    @Override
    public void onRefbookItemsChanged( String aRefbookId, IList<SkEvent> aEvents ) {
      // nop
    }

    @Override
    public void onRefbookChanged( ECrudOp aOp, String aRefbookId ) {
      refbooksListPanel.refresh();
    }
  };

  private IWsRefbooksManagementService rbms;
  private RefbooksListPanel            refbooksListPanel;

  @Override
  protected void doCreateContent( TsComposite aParent ) {
    rbms = tsContext().get( IWsRefbooksManagementService.class );
    ITsGuiContext ctx = new TsGuiContext( tsContext() );
    OPDEF_DBLCLICK_ACTION_ID.setValue( ctx.params(), AV_STR_EMPTY );
    refbooksListPanel = new RefbooksListPanel( aParent, ctx, ISkConnectionSupplier.DEF_CONN_ID, false );
    refbooksListPanel.addTsDoubleClickListener( ( src, sel ) -> rbms.showRefbookUipart( sel ) );
    ISkRefbookService rbServ = coreApi().getService( ISkRefbookService.SERVICE_ID );
    rbServ.eventer().addListener( refbookServiceListener );
    refbooksListPanel.addDisposeListener( aE -> rbServ.eventer().removeListener( refbookServiceListener ) );
  }

}
