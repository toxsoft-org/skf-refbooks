package org.toxsoft.skf.refbooks.gui.glib;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.utils.layout.*;
import org.toxsoft.core.tslib.bricks.strid.more.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.gui.glib.*;

/**
 * Panel to edit refbook items.
 * <p>
 * Contains the {@link SashForm} with two parts:
 * <ul>
 * <li>left side - {@link RefbooksListPanel} to select refbook to edit it's items;</li>
 * <li>right side - {@link RefbookItemsListPanel} edits list of the refbook selected in left list.</li>
 * </ul>
 *
 * @author hazard157
 */
public class PanelRefbookItemsEditor
    extends SkPanel {

  private final RefbooksListPanel refbooksPanel;
  // private final RefbookItemsListPanel itemsPanel;

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
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public PanelRefbookItemsEditor( Composite aParent, ITsGuiContext aContext, IdChain aUsedConnId ) {
    super( aParent, aContext, aUsedConnId );
    this.setLayout( new BorderLayout() );
    SashForm sfMain = new SashForm( this, SWT.HORIZONTAL );
    refbooksPanel = new RefbooksListPanel( sfMain, tsContext(), getUsedConnectionId(), false );
    // itemsPanel = new RefbookItemsListPanel()

    // TODO Auto-generated constructor stub

    // sfMain.setWeights( 3000, 7000 );
  }

}
