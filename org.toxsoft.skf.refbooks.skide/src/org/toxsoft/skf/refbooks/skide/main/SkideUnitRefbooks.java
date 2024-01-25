package org.toxsoft.skf.refbooks.skide.main;

import static org.toxsoft.core.tsgui.bricks.actions.ITsStdActionDefs.*;
import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.skf.refbooks.skide.ISkidePluginRefbooksConstants.*;
import static org.toxsoft.skf.refbooks.skide.ISkidePluginRefbooksSharedResources.*;
import static org.toxsoft.skide.core.ISkideCoreConstants.*;

import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.coll.primtypes.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.skide.tasks.codegen.*;
import org.toxsoft.skide.core.api.*;
import org.toxsoft.skide.core.api.impl.*;
import org.toxsoft.skide.core.api.tasks.*;

/**
 * SkiDE unit: view and edit {@link ISkRefbookService} content.
 *
 * @author hazard157
 */
public class SkideUnitRefbooks
    extends AbstractSkideUnit {

  /**
   * The plugin ID.
   */
  public static final String UNIT_ID = SKIDE_FULL_ID + ".unit.refbooks"; //$NON-NLS-1$

  SkideUnitRefbooks( ITsGuiContext aContext, AbstractSkidePlugin aCreator ) {
    super( UNIT_ID, OptionSetUtils.createOpSet( //
        TSID_NAME, STR_SKIDE_REFBOOKS, //
        TSID_DESCRIPTION, STR_SKIDE_REFBOOKS_D, //
        TSID_ICON_ID, ICONID_SKIDE_PLUGIN_REFBOOKS //
    ), aContext, aCreator );
    unitActions().add( ACDEF_ABOUT );
  }

  @Override
  protected AbstractSkideUnitPanel doCreateUnitPanel( ITsGuiContext aContext ) {
    return new SkideUnitRefbooksPanel( aContext, this );
  }

  @Override
  protected void doFillTasks( IStringMapEdit<AbstractSkideUnitTask> aTaskRunnersMap ) {
    AbstractSkideUnitTask task = new TaskRefbooksCodegen( this );
    aTaskRunnersMap.put( task.taskInfo().id(), task );
  }

}
