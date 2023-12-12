package org.toxsoft.skf.refbooks.gui.glib;

import java.util.*;

import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tslib.bricks.strid.more.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.gui.glib.*;

/**
 * Panel to edit specified refbook structure as described by {@link IDtoRefbookInfo}.
 *
 * @author hazard157
 */
public class RefbookStructPanel
    extends SkPanel {

  /**
   * TODO listen to the refbook service and update struture when not SELF editing refbook<br>
   * TODO listen to the refbook service and set clear the panel when refbook is removed
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

    // TODO PanelRefbookStruct.PanelRefbookStruct()
    // use M5-model of IDtoRefbookInfo

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
      if( refbookId != null ) {
        TsItemNotFoundRtException.checkNull( refbookServ().findRefbook( aRefbookId ) );
      }
      refbookId = aRefbookId;
      // TODO refresh panel
    }
  }

}
