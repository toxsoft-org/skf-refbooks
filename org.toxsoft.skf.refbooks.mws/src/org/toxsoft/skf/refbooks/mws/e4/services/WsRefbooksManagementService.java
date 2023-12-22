package org.toxsoft.skf.refbooks.mws.e4.services;

import static org.toxsoft.skf.refbooks.mws.IWsRefooksConstants.*;

import org.eclipse.e4.ui.model.application.ui.basic.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.mws.e4.helpers.partman.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.strid.impl.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.coll.primtypes.*;
import org.toxsoft.core.tslib.coll.primtypes.impl.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.mws.*;
import org.toxsoft.skf.refbooks.mws.e4.uiparts.*;
import org.toxsoft.uskat.core.api.evserv.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.utils.*;

/**
 * {@link IWsRefbooksManagementService} implementation.
 *
 * @author hazard157
 */
public class WsRefbooksManagementService
    implements IWsRefbooksManagementService, ITsGuiContextable, ISkConnected {

  private static final String UIPART_PID_PREFIX = WS_RB_FULL_ID + ".refbook_uipart"; //$NON-NLS-1$

  private final ISkRefbookServiceListener refbookServiceListener = new ISkRefbookServiceListener() {

    @Override
    public void onRefbookItemsChanged( String aRefbookId, IList<SkEvent> aEvents ) {
      // nop
    }

    @Override
    public void onRefbookChanged( ECrudOp aOp, String aRefbookId ) {
      refreshOpenUiparts();
    }
  };

  private final ITsGuiContext         tsContext;
  private final ISkConnectionSupplier skConnSupplier;
  private final ITsPartStackManager   psMan;

  /**
   * Constructor.
   *
   * @param aContext {@link ITsGuiContext} - the context
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public WsRefbooksManagementService( ITsGuiContext aContext ) {
    tsContext = TsNullArgumentRtException.checkNull( aContext );
    skConnSupplier = tsContext.get( ISkConnectionSupplier.class );
    psMan = new TsPartStackManager( tsContext.eclipseContext(), PARTSTACKID_REFBOOKS_MAIN );
    if( skConn().state().isActive() ) {
      whenConnInit();
    }
    skConn().addConnectionListener( ( aSource, aOldState ) -> {
      if( aSource.state().isActive() && !aOldState.isActive() ) {
        whenConnInit();
        return;
      }
    } );
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  private static String makeUipartIdFromRefbookId( String aRefbookId ) {
    return StridUtils.makeIdPath( UIPART_PID_PREFIX, aRefbookId );
  }

  private static String makeRefbookIdFromUipartId( String aUipartId ) {
    return StridUtils.removeStartingIdPath( aUipartId, UIPART_PID_PREFIX );
  }

  private void whenConnInit() {
    ISkRefbookService rbServ = coreApi().getService( ISkRefbookService.SERVICE_ID );
    rbServ.eventer().addListener( refbookServiceListener );
  }

  /**
   * refreshes open UIparts - close non-existing refbooks and refreshes tabs of open UIparts.
   */
  private void refreshOpenUiparts() {
    if( !skConn().state().isOpen() ) { // close all refbook's UIparts
      psMan.closeAll();
      return;
    }
    ISkRefbookService rbServ = coreApi().getService( ISkRefbookService.SERVICE_ID );
    IStridablesList<ISkRefbook> refbooksList = rbServ.listRefbooks();
    // close removed refbooks UIparts
    IStringListEdit idsToClose = new StringArrayList();
    for( String partId : psMan.listManagedParts().keys() ) {
      String refbookId = makeRefbookIdFromUipartId( partId );
      if( !refbooksList.hasKey( refbookId ) ) {
        idsToClose.add( partId );
      }
    }
    for( String partId : idsToClose ) {
      psMan.closePart( partId );
    }
    // update opened refbook UIparts headers, content is update somewhere else
    for( ISkRefbook rb : refbooksList ) {
      String refbookId = rb.id();
      String partId = makeUipartIdFromRefbookId( refbookId );
      MPart part = psMan.findPart( partId );
      if( part != null ) {
        part.setLabel( rb.nmName() );
        part.setTooltip( rb.description() );
      }
    }
  }

  // ------------------------------------------------------------------------------------
  // ISkConnected
  //

  @Override
  public ISkConnection skConn() {
    return skConnSupplier.defConn();
  }

  // ------------------------------------------------------------------------------------
  // ITsGuiContextable
  //

  @Override
  public ITsGuiContext tsContext() {
    return tsContext;
  }

  // ------------------------------------------------------------------------------------
  // IWsRefbooksManagementService
  //

  @Override
  public void showRefbookUipart( ISkRefbook aRefbook ) {
    // check argument is existing refbook
    TsNullArgumentRtException.checkNull( aRefbook );
    // it is necessary to switch to destination perspective, because parts are always created in current perspective
    e4Helper().switchToPerspective( PERSPID_WS_RB_MAIN, null );
    // activate part if already exists
    String uipartId = makeUipartIdFromRefbookId( aRefbook.id() );
    MPart foundPart = psMan.findPart( uipartId );
    if( foundPart != null ) {
      e4Helper().switchToPerspective( PERSPID_WS_RB_MAIN, uipartId );
      return;
    }
    // create UIpart part
    UIpartInfo partInfo = new UIpartInfo( uipartId );
    partInfo.setCloseable( true );
    partInfo.setTooltip( aRefbook.description() );
    partInfo.setLabel( aRefbook.readableName() );
    partInfo.setContributionUri( Activator.PLUGIN_ID, UipartRefbookItemsEditor.class );
    MPart newPart = psMan.createPart( partInfo );
    // set part to display specified refbook
    UipartRefbookItemsEditor uipartBrowser = (UipartRefbookItemsEditor)newPart.getObject();
    uipartBrowser.setRefbook( aRefbook );
  }

}
