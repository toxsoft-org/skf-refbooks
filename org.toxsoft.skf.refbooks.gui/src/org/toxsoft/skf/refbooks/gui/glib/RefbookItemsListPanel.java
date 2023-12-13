package org.toxsoft.skf.refbooks.gui.glib;

import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.m5.gui.panels.*;
import org.toxsoft.core.tslib.bricks.strid.more.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.gui.glib.*;

/**
 * Panel displays editable items list of the specified refbook.
 *
 * @author hazard157
 */
public class RefbookItemsListPanel
    extends SkStdEventsProducerPanel<ISkRefbookItem> {

  private ISkRefbook                         refbook = null;
  private IM5CollectionPanel<ISkRefbookItem> panel   = null;

  /**
   * Constructor.
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
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  private void reinitPanel() {
    this.setLayoutDeferred( true );
    try {
      if( panel != null ) {
        panel.getControl().dispose();
        panel = null;
      }
      if( refbook != null ) {
        // TODO create IM5CollectionPanel of the specified refbook
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