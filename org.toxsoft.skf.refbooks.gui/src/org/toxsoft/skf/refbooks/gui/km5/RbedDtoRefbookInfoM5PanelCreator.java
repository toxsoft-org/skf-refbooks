package org.toxsoft.skf.refbooks.gui.km5;

import static org.toxsoft.core.tsgui.m5.gui.mpc.IMultiPaneComponentConstants.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;

import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.m5.gui.mpc.impl.*;
import org.toxsoft.core.tsgui.m5.gui.panels.*;
import org.toxsoft.core.tsgui.m5.gui.panels.impl.*;
import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tsgui.m5.model.impl.*;
import org.toxsoft.skf.refbooks.lib.*;

/**
 * Panel creator for {@link RbedDtoRefbookInfoM5Model}.
 *
 * @author dima
 */
class RbedDtoRefbookInfoM5PanelCreator
    extends M5DefaultPanelCreator<IDtoRefbookInfo> {

  /**
   * Constructor.
   */
  public RbedDtoRefbookInfoM5PanelCreator() {
    // nop
  }

  @Override
  protected IM5EntityPanel<IDtoRefbookInfo> doCreateEntityEditorPanel( ITsGuiContext aContext,
      IM5LifecycleManager<IDtoRefbookInfo> aLifecycleManager ) {
    return new RbedDtoRefbookInfoM5EntityPanel( aContext, model(), aLifecycleManager );
  }

  @Override
  protected IM5CollectionPanel<IDtoRefbookInfo> doCreateCollEditPanel( ITsGuiContext aContext,
      IM5ItemsProvider<IDtoRefbookInfo> aItemsProvider, IM5LifecycleManager<IDtoRefbookInfo> aLifecycleManager ) {
    OPDEF_IS_ACTIONS_CRUD.setValue( aContext.params(), AV_TRUE );
    OPDEF_IS_ACTIONS_HIDE_PANES.setValue( aContext.params(), AV_TRUE );
    OPDEF_IS_FILTER_PANE.setValue( aContext.params(), AV_TRUE );
    OPDEF_IS_SUPPORTS_TREE.setValue( aContext.params(), AV_TRUE );
    MultiPaneComponentModown<IDtoRefbookInfo> mpc =
        new RbedDtoRefbookInfoM5Mpc( aContext, model(), aItemsProvider, aLifecycleManager );
    return new M5CollectionPanelMpcModownWrapper<>( mpc, false );
  }

}
