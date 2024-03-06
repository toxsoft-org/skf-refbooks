package org.toxsoft.skf.refbooks.gui.km5;

import static org.toxsoft.core.tsgui.m5.IM5Constants.*;
import static org.toxsoft.skf.refbooks.gui.km5.IKM5RefbooksConstants.*;

import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.lib.impl.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.km5.*;

/**
 * LM for {@link SkRefbookM5Model}.
 * <p>
 * This LM does not supports the refbook structure (attributes, rivets, links and CLOBs) editing. Use
 * {@link RbedDtoRefbookInfoM5Model} for structural changes.
 *
 * @author hazard157
 */
public class SkRefbookM5LifecycleManager
    extends KM5LifecycleManagerBasic<ISkRefbook> {

  /**
   * Constructor.
   *
   * @param aModel {@link IM5Model}&lt;T&gt; - the model
   * @param aMaster {@link ISkConnection} - master object, the refbook service
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public SkRefbookM5LifecycleManager( IM5Model<ISkRefbook> aModel, ISkConnection aMaster ) {
    super( aModel, true, true, true, true, aMaster );
    TsNullArgumentRtException.checkNull( aMaster );
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  private ISkRefbookService refbookService() {
    return master().coreApi().getService( ISkRefbookService.SERVICE_ID );
  }

  private static DtoRefbookInfo createRefbookDto( IM5Bunch<ISkRefbook> aValues ) {
    DtoRefbookInfo rbInfo;
    if( aValues.originalEntity() != null ) {
      rbInfo = DtoRefbookInfo.of( aValues.originalEntity() );
    }
    else {
      String refbookId = aValues.getAsAv( FID_REFBOOK_ID ).asString();
      rbInfo = new DtoRefbookInfo( refbookId, IOptionSet.NULL );
    }
    rbInfo.params().setStr( FID_NAME, aValues.getAsAv( ISkHardConstants.AID_NAME ).asString() );
    rbInfo.params().setStr( FID_DESCRIPTION, aValues.getAsAv( ISkHardConstants.AID_DESCRIPTION ).asString() );
    return rbInfo;
  }

  // ------------------------------------------------------------------------------------
  // KM5LifecycleManagerBasic
  //

  @Override
  protected ValidationResult doBeforeCreate( IM5Bunch<ISkRefbook> aValues ) {
    IDtoRefbookInfo rbInfo = createRefbookDto( aValues );
    return refbookService().svs().validator().canDefineRefbook( rbInfo, null );
  }

  @Override
  protected ISkRefbook doCreate( IM5Bunch<ISkRefbook> aValues ) {
    IDtoRefbookInfo rbInfo = createRefbookDto( aValues );
    return refbookService().defineRefbook( rbInfo );
  }

  @Override
  protected ValidationResult doBeforeEdit( IM5Bunch<ISkRefbook> aValues ) {
    IDtoRefbookInfo rbInfo = createRefbookDto( aValues );
    return refbookService().svs().validator().canDefineRefbook( rbInfo, aValues.originalEntity() );
  }

  @Override
  protected ISkRefbook doEdit( IM5Bunch<ISkRefbook> aValues ) {
    IDtoRefbookInfo rbInfo = createRefbookDto( aValues );
    return refbookService().defineRefbook( rbInfo );
  }

  @Override
  protected ValidationResult doBeforeRemove( ISkRefbook aEntity ) {
    return refbookService().svs().validator().canRemoveRefbook( aEntity.id() );
  }

  @Override
  protected void doRemove( ISkRefbook aEntity ) {
    refbookService().removeRefbook( aEntity.id() );
  }

  @Override
  protected IList<ISkRefbook> doListEntities() {
    return refbookService().listRefbooks();
  }

}
