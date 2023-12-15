package org.toxsoft.skf.refbooks.mws.e4.services;

import static org.toxsoft.skf.refbooks.mws.IWsRefooksConstants.*;

import org.eclipse.e4.ui.model.application.ui.basic.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.mws.e4.helpers.partman.*;
import org.toxsoft.core.tslib.bricks.strid.impl.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.mws.*;
import org.toxsoft.skf.refbooks.mws.e4.uiparts.*;
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
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  private static String makeUipartIdFromGwpObjId( String aRefbookId ) {
    return StridUtils.makeIdPath( UIPART_PID_PREFIX, aRefbookId );
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
    String uipartId = makeUipartIdFromGwpObjId( aRefbook.id() );
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
