package org.toxsoft.skf.refbooks.gui.glib;

import static org.toxsoft.core.tsgui.m5.gui.mpc.IMultiPaneComponentConstants.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.skf.refbooks.gui.km5.IKM5RefbooksConstants.*;

import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.core.tsgui.bricks.stdevents.*;
import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tsgui.m5.gui.panels.*;
import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tsgui.utils.layout.*;
import org.toxsoft.core.tslib.bricks.strid.more.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.gui.glib.*;

/**
 * Panel displays editable list of the refbooks.
 *
 * @author hazard157
 */
public class RefbooksListPanel
    extends SkStdEventsProducerPanel<ISkRefbook> {

  private final IM5CollectionPanel<ISkRefbook> panel;

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
   * @param aViewer boolean - <code>true</code> to create panel as viewer, without editing abilities
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public RefbooksListPanel( Composite aParent, ITsGuiContext aContext, IdChain aUsedConnId, boolean aViewer ) {
    super( aParent, aContext, aUsedConnId );
    this.setLayout( new BorderLayout() );
    IM5Model<ISkRefbook> model = m5().getModel( MID_KM5RB_REFBOOK, ISkRefbook.class );
    IM5LifecycleManager<ISkRefbook> lm = model.getLifecycleManager( skConn() );
    ITsGuiContext ctx = new TsGuiContext( tsContext() );
    if( aViewer ) {
      OPDEF_IS_ACTIONS_CRUD.setValue( ctx.params(), AV_FALSE );
      panel = model.panelCreator().createCollViewerPanel( aContext, lm.itemsProvider() );
    }
    else {
      panel = model.panelCreator().createCollEditPanel( ctx, lm.itemsProvider(), lm );
    }
    panel.createControl( this );
    panel.getControl().setLayoutData( BorderLayout.CENTER );
  }

  // ------------------------------------------------------------------------------------
  // SkStdEventsProducerPanel
  //

  @Override
  public ISkRefbook selectedItem() {
    return panel.selectedItem();
  }

  @Override
  public void setSelectedItem( ISkRefbook aItem ) {
    panel.setSelectedItem( aItem );
  }

  /**
   * refresh panel content
   */
  public void refresh() {
    panel.refresh();
  }

  public void addSelectionListener( ITsSelectionChangeListener<ISkRefbook> aListener ) {
    panel.addTsSelectionListener( aListener );
  }

}
