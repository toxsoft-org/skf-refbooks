package org.toxsoft.skf.refbooks.gui.glib;

import static org.toxsoft.core.tsgui.bricks.actions.ITsStdActionDefs.*;
import static org.toxsoft.core.tsgui.m5.gui.mpc.IMultiPaneComponentConstants.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.skf.refbooks.gui.km5.IKM5RefbooksConstants.*;

import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.actions.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.core.tsgui.graphics.icons.*;
import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tsgui.m5.gui.mpc.impl.*;
import org.toxsoft.core.tsgui.m5.gui.panels.*;
import org.toxsoft.core.tsgui.m5.gui.panels.impl.*;
import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tsgui.panels.toolbar.*;
import org.toxsoft.core.tsgui.utils.layout.*;
import org.toxsoft.core.tslib.bricks.strid.more.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.gui.km5.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.gui.glib.*;

/**
 * Panel displays editable list of the refbooks.
 * <p>
 * This panel may be created in administrator or user modes, determined by <code>aAdminMode</code> argument of the
 * constructor. in administrator mode create/edit/remove of refbooks are allowed. In user mode only refbook editing is
 * allowed.
 * <p>
 * The panel is using {@link SkRefbookM5Model} so it does not supports the refbook structure (attributes, rivets, links
 * and CLOBs) editing.
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
   * @param aAdminMode boolean - <code>true</code> to allow refbook creation/removal
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public RefbooksListPanel( Composite aParent, ITsGuiContext aContext, IdChain aUsedConnId, boolean aAdminMode ) {
    super( aParent, aContext, aUsedConnId );
    this.setLayout( new BorderLayout() );
    IM5Model<ISkRefbook> model = m5().getModel( MID_KM5RB_REFBOOK, ISkRefbook.class );
    IM5LifecycleManager<ISkRefbook> lm = model.getLifecycleManager( skConn() );
    // setup refbooks list UI depending on administrator/user mode
    ITsGuiContext ctx = new TsGuiContext( tsContext() );
    OPDEF_IS_ACTIONS_CRUD.setValue( ctx.params(), AV_TRUE );
    OPDEF_IS_ACTIONS_HIDE_PANES.setValue( ctx.params(), avBool( aAdminMode ) );
    OPDEF_IS_SUMMARY_PANE.setValue( ctx.params(), avBool( aAdminMode ) );
    OPDEF_IS_DETAILS_PANE.setValue( ctx.params(), AV_TRUE );
    MultiPaneComponentModown<ISkRefbook> mpc = new MultiPaneComponentModown<>( ctx, model, lm.itemsProvider(), lm ) {

      protected ITsToolbar doCreateToolbar( ITsGuiContext aContext1, String aName, EIconSize aIconSize,
          IListEdit<ITsActionDef> aActs ) {
        if( !aAdminMode ) {
          aActs.remove( ACDEF_ADD );
          aActs.remove( ACDEF_REMOVE );
        }
        return super.doCreateToolbar( aContext1, aName, aIconSize, aActs );
      }
    };
    panel = new M5CollectionPanelMpcModownWrapper<>( mpc, false );
    // create panel
    panel.createControl( this );
    panel.getControl().setLayoutData( BorderLayout.CENTER );
    panel.addTsSelectionListener( selectionChangeEventHelper );
    panel.addTsDoubleClickListener( doubleClickEventHelper );
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

  // ------------------------------------------------------------------------------------
  // API
  //

  /**
   * refresh panel content
   */
  public void refresh() {
    panel.refresh();
  }

}
