package org.toxsoft.skf.refbooks.gui.glib;

import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tsgui.m5.gui.panels.*;
import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tsgui.utils.layout.*;
import org.toxsoft.core.tslib.bricks.strid.more.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.api.evserv.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.gui.glib.*;

/**
 * Panel displays editable items list of the specified refbook.
 *
 * @author hazard157
 * @author dima
 */
public class RefbookItemsListPanel
    extends SkStdEventsProducerPanel<ISkRefbookItem> {

  /**
   * Refreshes this panel when corresponding event happens.
   */
  private final ISkRefbookServiceListener refbookServiceListener = new ISkRefbookServiceListener() {

    @Override
    public void onRefbookChanged( ECrudOp aOp, String aRefbookId ) {
      switch( aOp ) {
        case CREATE:
          break;
        case EDIT:
        case REMOVE:
        case LIST: {
          if( refbook != null ) {
            ISkRefbookService rbServ = coreApi().getService( ISkRefbookService.SERVICE_ID );
            if( rbServ.findRefbook( refbook.id() ) == null ) { // refbook was deleted
              refbook = null;
            }
          }
          reinitPanel();
          break;
        }
        default:
          throw new TsNotAllEnumsUsedRtException();
      }
    }

    @Override
    public void onRefbookItemsChanged( String aRefbookId, IList<SkEvent> aEvents ) {
      // nop
    }

  };

  private ISkRefbook                         refbook = null;
  private IM5CollectionPanel<ISkRefbookItem> panel   = null;

  /**
   * Constructor for use in SkIDE.
   * <p>
   * Constructor stores reference to the context, does not creates copy.
   * <p>
   * The connection ID is the key to get connection from the {@link ISkConnectionSupplier#allConns()} map.
   *
   * @param aParent {@link Composite} - parent component
   * @param aContext {@link ITsGuiContext} - the context
   * @param aUsedConnId {@link IdChain} - ID of connection to be used
   * @param aViewer boolean - <code>true</code> if
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public RefbookItemsListPanel( Composite aParent, ITsGuiContext aContext, IdChain aUsedConnId, boolean aViewer ) {
    super( aParent, aContext, aUsedConnId );
    this.setLayout( new BorderLayout() );
    ISkRefbookService rbServ = coreApi().getService( ISkRefbookService.SERVICE_ID );
    rbServ.eventer().addListener( refbookServiceListener );
  }

  /**
   * Constructor for use in MWS.
   * <p>
   * Constructor stores reference to the context, does not creates copy.
   * <p>
   * The connection ID is the key to get connection from the {@link ISkConnectionSupplier#allConns()} map.
   *
   * @param aParent {@link Composite} - parent component
   * @param aContext {@link ITsGuiContext} - the context
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public RefbookItemsListPanel( Composite aParent, ITsGuiContext aContext ) {
    this( aParent, aContext, ISkConnectionSupplier.DEF_CONN_ID, false );
  }

  @Override
  protected void doDispose() {
    ISkRefbookService rbServ = coreApi().getService( ISkRefbookService.SERVICE_ID );
    rbServ.eventer().removeListener( refbookServiceListener );
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  private void reinitPanel() {
    if( isDisposed() ) {
      return;
    }
    this.setLayoutDeferred( true );
    try {
      if( panel != null ) {
        panel.getControl().dispose();
        panel = null;
      }
      if( refbook != null ) {
        // create IM5CollectionPanel of the specified refbook
        IM5Model<ISkRefbookItem> model = m5().getModel( refbook.itemClassId(), ISkRefbookItem.class );
        // инициализация GUI
        ITsGuiContext ctx = new TsGuiContext( tsContext() );
        IM5LifecycleManager<ISkRefbookItem> lm = model.getLifecycleManager( skConn() );
        panel = model.panelCreator().createCollEditPanel( ctx, lm.itemsProvider(), lm );
        panel.createControl( this );
        panel.getControl().setLayoutData( BorderLayout.CENTER );
      }
    }
    finally {
      this.setLayoutDeferred( false );
    }
  }

  // ------------------------------------------------------------------------------------
  // SkStdEventsProducerPanel
  //

  @Override
  public ISkRefbookItem selectedItem() {
    if( panel != null ) {
      return panel.selectedItem();
    }
    return null;
  }

  @Override
  public void setSelectedItem( ISkRefbookItem aItem ) {
    if( panel != null ) {
      panel.setSelectedItem( aItem );
    }
  }

  // ------------------------------------------------------------------------------------
  // API
  //

  /**
   * Returns the refbook which items are edited.
   *
   * @return {@link ISkRefbook} - the refbook or <code>null</code>
   */
  public ISkRefbook getRefbook() {
    return refbook;
  }

  /**
   * Sets the refbook to edit it's items.
   *
   * @param aRefbook {@link ISkRefbook} - the refbook or <code>null</code>
   */
  public void setRefbook( ISkRefbook aRefbook ) {
    if( refbook != aRefbook ) {
      refbook = aRefbook;
      reinitPanel();
    }
  }

}
