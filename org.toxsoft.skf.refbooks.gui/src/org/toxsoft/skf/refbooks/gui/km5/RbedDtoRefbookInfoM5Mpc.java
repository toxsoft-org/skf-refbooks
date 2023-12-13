package org.toxsoft.skf.refbooks.gui.km5;

import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tsgui.m5.gui.mpc.impl.*;
import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.skf.refbooks.lib.*;

/**
 * {@link MultiPaneComponentModown} implementation to be used in collection panels of {@link RbedDtoRefbookInfoM5Model}.
 *
 * @author dima
 */
public class RbedDtoRefbookInfoM5Mpc
    extends MultiPaneComponentModown<IDtoRefbookInfo> {

  /**
   * Constructor.
   *
   * @param aContext {@link ITsGuiContext} - the context
   * @param aModel {@link IM5Model} - the model
   * @param aItemsProvider {@link IM5ItemsProvider} - the items provider or <code>null</code>
   * @param aLifecycleManager {@link IM5LifecycleManager} - the lifecycle manager or <code>null</code>
   */
  public RbedDtoRefbookInfoM5Mpc( ITsGuiContext aContext, IM5Model<IDtoRefbookInfo> aModel,
      IM5ItemsProvider<IDtoRefbookInfo> aItemsProvider, IM5LifecycleManager<IDtoRefbookInfo> aLifecycleManager ) {
    super( aContext, aModel, aItemsProvider, aLifecycleManager );
  }

}
