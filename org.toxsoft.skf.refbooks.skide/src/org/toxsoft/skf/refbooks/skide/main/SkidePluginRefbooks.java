package org.toxsoft.skf.refbooks.skide.main;

import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.skf.refbooks.skide.ISkidePluginRefbooksConstants.*;
import static org.toxsoft.skf.refbooks.skide.ISkidePluginRefbooksSharedResources.*;
import static org.toxsoft.skide.core.ISkideCoreConstants.*;

import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skide.core.api.*;

/**
 * SkIDE plugin: view and edit {@link ISkRefbookService} content.
 *
 * @author hazard157
 */
public class SkidePluginRefbooks
    extends AbstractSkidePlugin {

  /**
   * The plugin ID.
   */
  public static final String SKIDE_PLUGIN_ID = SKIDE_FULL_ID + ".plugin.refbooks"; //$NON-NLS-1$

  /**
   * The singleton instance.
   */
  public static final AbstractSkidePlugin INSTANCE = new SkidePluginRefbooks();

  SkidePluginRefbooks() {
    super( SKIDE_PLUGIN_ID, OptionSetUtils.createOpSet( //
        TSID_NAME, STR_SKIDE_PLUGIN_REFBOOKS, //
        TSID_DESCRIPTION, STR_SKIDE_PLUGIN_REFBOOKS_D, //
        TSID_ICON_ID, ICONID_SKIDE_PLUGIN_REFBOOKS //
    ) );
  }

  @Override
  protected void doCreateUnits( ITsGuiContext aContext, IStridablesListEdit<ISkideUnit> aUnitsList ) {
    aUnitsList.add( new SkideUnitRefbooks( aContext, this ) );
  }

}
