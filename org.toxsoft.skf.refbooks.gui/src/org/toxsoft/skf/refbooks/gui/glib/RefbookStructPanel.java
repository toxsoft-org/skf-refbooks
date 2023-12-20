package org.toxsoft.skf.refbooks.gui.glib;

import static org.toxsoft.skf.refbooks.gui.km5.IKM5RefbooksConstants.*;

import java.util.*;

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
import org.toxsoft.skf.refbooks.lib.impl.*;
import org.toxsoft.uskat.core.api.evserv.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.gui.glib.*;

/**
 * Panel to edit specified refbook structure as described by {@link IDtoRefbookInfo}.
 *
 * @author hazard157
 * @author dima
 */
public class RefbookStructPanel
    extends SkPanel
    implements ISkRefbookServiceListener {

  private final IM5EntityPanel<IDtoRefbookInfo> panel;

  /**
   * TODO listen to the refbook service and update structure when not SELF editing refbook<br>
   */

  private String refbookId = null;

  /**
   * Constructor using {@link ISkConnectionSupplier#defConn()} as a connection.
   *
   * @param aParent {@link Composite} - parent component
   * @param aContext {@link ITsGuiContext} - the context
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public RefbookStructPanel( Composite aParent, ITsGuiContext aContext ) {
    this( aParent, aContext, ISkConnectionSupplier.DEF_CONN_ID );
  }

  /**
   * Constructor.
   *
   * @param aParent {@link Composite} - parent component
   * @param aContext {@link ITsGuiContext} - the context
   * @param aUsedConnId {@link IdChain} - ID of connection to be used
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public RefbookStructPanel( Composite aParent, ITsGuiContext aContext, IdChain aUsedConnId ) {
    super( aParent, aContext, aUsedConnId );
    this.setLayout( new BorderLayout() );

    IM5Model<IDtoRefbookInfo> modelDto = m5().getModel( MID_RBED_DTO_REFBOOK_INFO, IDtoRefbookInfo.class );
    IM5LifecycleManager<IDtoRefbookInfo> lmDto = modelDto.getLifecycleManager( skConn() );
    ITsGuiContext ctxDto = new TsGuiContext( tsContext() );
    panel = modelDto.panelCreator().createEntityEditorPanel( ctxDto, lmDto );
    panel.createControl( this );
    panel.getControl().setLayoutData( BorderLayout.CENTER );
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  private ISkRefbookService refbookServ() {
    return coreApi().getService( ISkRefbookService.SERVICE_ID );
  }

  // ------------------------------------------------------------------------------------
  // API
  //

  /**
   * Returns the ID of the edited refbook.
   *
   * @return String - the edited refbook ID or <code>null</code>
   */
  public String getRefbookId() {
    return refbookId;
  }

  /**
   * Returns the edited refbook.
   *
   * @return {@link ISkRefbook} - the refbook or <code>null</code>
   */
  public ISkRefbook getRefbook() {
    if( refbookId != null ) {
      return refbookServ().findRefbook( refbookId );
    }
    return null;
  }

  /**
   * Sets the refbook to edit.
   *
   * @param aRefbookId String - the refbook ID or <code>null</code>
   * @throws TsItemNotFoundRtException no for non-<code>null</code> argument no such refbook exists
   */
  public void setRefbookId( String aRefbookId ) {
    if( !Objects.equals( refbookId, aRefbookId ) ) {
      ISkRefbook sel = refbookServ().findRefbook( aRefbookId );
      if( refbookId != null ) {
        TsItemNotFoundRtException.checkNull( sel );
      }
      refbookId = aRefbookId;
      // refresh panel
      IDtoRefbookInfo dtoRefbook = DtoRefbookInfo.of( sel );
      panel.setEntity( dtoRefbook );
    }
  }

  @Override
  public void onRefbookChanged( ECrudOp aOp, String aRefbookId ) {
    // listen to the refbook service and set clear the panel when refbook is removed
    if( aRefbookId == refbookId ) {
      switch( aOp ) {
        case REMOVE:
          panel.setEntity( null );
          break;
        case CREATE:
        case EDIT:
        case LIST:
          // refresh panel
          ISkRefbook sel = refbookServ().findRefbook( aRefbookId );
          IDtoRefbookInfo dtoRefbook = DtoRefbookInfo.of( sel );
          panel.setEntity( dtoRefbook );
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void onRefbookItemsChanged( String aRefbookId, IList<SkEvent> aEvents ) {
    // nop
  }

}
