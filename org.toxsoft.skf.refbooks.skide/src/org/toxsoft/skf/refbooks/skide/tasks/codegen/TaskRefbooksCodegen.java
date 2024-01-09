package org.toxsoft.skf.refbooks.skide.tasks.codegen;

import java.util.concurrent.*;

import org.toxsoft.core.tslib.bricks.ctx.*;
import org.toxsoft.core.tslib.bricks.gentask.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.skf.refbooks.skide.main.*;
import org.toxsoft.skide.task.codegen.main.*;

/**
 * SkIDE task {@link SkideTaskCodegenInfo} runner for {@link SkideUnitRefbooks}.
 *
 * @author hazard157
 */
public class TaskRefbooksCodegen
    extends AbstractGenericTask {

  /**
   * Constructor.
   */
  public TaskRefbooksCodegen() {
    super( SkideTaskCodegenInfo.INSTANCE );
  }

  // ------------------------------------------------------------------------------------
  // AbstractGenericTaskRunner
  //

  @Override
  protected ITsContextRo doRunSync( ITsContextRo aInput ) {
    // TODO Auto-generated method stub
    return super.doRunSync( aInput );
  }

  @Override
  protected Future<ITsContextRo> doRunAsync( ITsContextRo aInput, ITsContext aOutput ) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected ValidationResult doCanRun( ITsContextRo aInput ) {
    // TODO Auto-generated method stub
    return ValidationResult.error( "Under development" );
  }

}
