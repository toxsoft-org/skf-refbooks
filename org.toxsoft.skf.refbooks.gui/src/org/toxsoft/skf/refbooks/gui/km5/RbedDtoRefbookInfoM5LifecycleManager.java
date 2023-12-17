package org.toxsoft.skf.refbooks.gui.km5;

import static org.toxsoft.core.tsgui.m5.IM5Constants.*;
import static org.toxsoft.uskat.core.gui.km5.sded.IKM5SdedConstants.*;

import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tsgui.m5.model.impl.*;
import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.impl.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.lib.impl.*;
import org.toxsoft.uskat.core.connection.*;

/**
 * LM class for {@link RbedDtoRefbookInfoM5Model}.
 * <p>
 *
 * @author dima
 */
class RbedDtoRefbookInfoM5LifecycleManager
    extends M5LifecycleManager<IDtoRefbookInfo, ISkConnection> {

  public RbedDtoRefbookInfoM5LifecycleManager( RbedDtoRefbookInfoM5Model aModel, ISkConnection aMaster ) {
    super( aModel, true, true, true, true, aMaster );
    TsNullArgumentRtException.checkNull( aMaster );
  }

  @Override
  public RbedDtoRefbookInfoM5Model model() {
    return (RbedDtoRefbookInfoM5Model)super.model();
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //
  private ISkRefbookService refbookService() {
    return master().coreApi().getService( ISkRefbookService.SERVICE_ID );
  }

  private IDtoRefbookInfo makeDtoRefbookInfo( IM5Bunch<IDtoRefbookInfo> aValues ) {
    String id = aValues.getAsAv( FID_CLASS_ID ).asString();
    IOptionSetEdit params = new OptionSet();
    if( aValues.originalEntity() != null ) {
      params.setAll( aValues.originalEntity().params() );
    }
    params.setStr( FID_NAME, aValues.getAsAv( FID_NAME ).asString() );
    params.setStr( FID_DESCRIPTION, aValues.getAsAv( FID_DESCRIPTION ).asString() );
    DtoRefbookInfo refbookInf = new DtoRefbookInfo( id, params );
    refbookInf.attrInfos().setAll( model().SELF_ATTR_INFOS.getFieldValue( aValues ) );
    refbookInf.linkInfos().setAll( model().SELF_LINK_INFOS.getFieldValue( aValues ) );
    refbookInf.rivetInfos().setAll( model().SELF_RIVET_INFOS.getFieldValue( aValues ) );
    refbookInf.clobInfos().setAll( model().SELF_CLOB_INFOS.getFieldValue( aValues ) );

    return refbookInf;
  }

  @Override
  protected ValidationResult doBeforeCreate( IM5Bunch<IDtoRefbookInfo> aValues ) {
    IDtoRefbookInfo dtoRefbookInfo = makeDtoRefbookInfo( aValues );
    return refbookService().svs().validator().canDefineRefbook( dtoRefbookInfo, null );
  }

  @Override
  protected IDtoRefbookInfo doCreate( IM5Bunch<IDtoRefbookInfo> aValues ) {
    IDtoRefbookInfo dtoRefbookInfo = makeDtoRefbookInfo( aValues );
    refbookService().defineRefbook( dtoRefbookInfo );
    return dtoRefbookInfo;
  }

  @Override
  protected ValidationResult doBeforeEdit( IM5Bunch<IDtoRefbookInfo> aValues ) {
    IDtoRefbookInfo dtoRefbookInfo = makeDtoRefbookInfo( aValues );
    ISkRefbook skRefbook = refbookService().findRefbook( dtoRefbookInfo.id() );
    return refbookService().svs().validator().canDefineRefbook( dtoRefbookInfo, skRefbook );
  }

  @Override
  protected IDtoRefbookInfo doEdit( IM5Bunch<IDtoRefbookInfo> aValues ) {
    IDtoRefbookInfo dtoRefbookInfo = makeDtoRefbookInfo( aValues );
    refbookService().defineRefbook( dtoRefbookInfo );
    return dtoRefbookInfo;
  }

  @Override
  protected ValidationResult doBeforeRemove( IDtoRefbookInfo aEntity ) {
    return refbookService().svs().validator().canRemoveRefbook( aEntity.id() );
  }

  @Override
  protected void doRemove( IDtoRefbookInfo aEntity ) {
    refbookService().removeRefbook( aEntity.id() );
  }

  @Override
  protected IList<IDtoRefbookInfo> doListEntities() {
    IStridablesList<ISkRefbook> refbooksList = refbookService().listRefbooks();
    IListEdit<IDtoRefbookInfo> dtoRefbooksList = new ElemArrayList<>( refbooksList.size() );
    for( ISkRefbook rbInf : refbooksList ) {
      IDtoRefbookInfo dtoInf = DtoRefbookInfo.of( rbInf );
      dtoRefbooksList.add( dtoInf );
    }
    return dtoRefbooksList;
  }

}
