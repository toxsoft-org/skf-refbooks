package org.toxsoft.skf.refbooks.gui.km5;

import static org.toxsoft.uskat.core.ISkHardConstants.*;

import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.km5.*;
import org.toxsoft.uskat.core.impl.dto.*;

/**
 * LM for {@link SkRefbookItemM5Model}.
 *
 * @author hazard157
 */
public class SkRefbookItemM5LifecycleManager
    extends KM5LifecycleManagerBasic<ISkRefbookItem, ISkConnection> {

  /**
   * Constructor.
   *
   * @param aModel {@link IM5Model}&lt;T&gt; - the model
   * @param aMaster {@link ISkConnection} - master object, the refbook service
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public SkRefbookItemM5LifecycleManager( IM5Model<ISkRefbookItem> aModel, ISkConnection aMaster ) {
    super( aModel, true, true, true, true, aMaster );
    TsNullArgumentRtException.checkNull( aMaster );
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  private ISkRefbookService refbookService() {
    return master().coreApi().getService( ISkRefbookService.SERVICE_ID );
  }

  private ISkRefbook refbook() {
    ISkRefbook rb = refbookService().findRefbookByItemClassId( model().id() );
    TsInternalErrorRtException.checkNull( rb );
    return rb;
  }

  private IDtoFullObject createItemDto( IM5Bunch<ISkRefbookItem> aValues ) {
    String classId = refbook().itemClassId();
    String strid = aValues.getAsAv( AID_STRID ).asString();
    Skid skid = new Skid( classId, strid );
    DtoFullObject dtoObj = new DtoFullObject( skid );

    // TODO fill dtoObj from aValues

    return dtoObj;
  }

  // ------------------------------------------------------------------------------------
  // KM5LifecycleManagerBasic
  //

  @Override
  protected ValidationResult doBeforeCreate( IM5Bunch<ISkRefbookItem> aValues ) {
    IDtoFullObject dtoItem = createItemDto( aValues );
    return refbookService().svs().validator().canDefineItem( refbook(), dtoItem, null );
  }

  @Override
  protected ISkRefbookItem doCreate( IM5Bunch<ISkRefbookItem> aValues ) {
    IDtoFullObject dtoItem = createItemDto( aValues );
    return refbook().defineItem( dtoItem );
  }

  @Override
  protected ValidationResult doBeforeEdit( IM5Bunch<ISkRefbookItem> aValues ) {
    IDtoFullObject dtoItem = createItemDto( aValues );
    return refbookService().svs().validator().canDefineItem( refbook(), dtoItem, aValues.originalEntity() );
  }

  @Override
  protected ISkRefbookItem doEdit( IM5Bunch<ISkRefbookItem> aValues ) {
    IDtoFullObject dtoItem = createItemDto( aValues );
    return refbook().defineItem( dtoItem );
  }

  @Override
  protected ValidationResult doBeforeRemove( ISkRefbookItem aEntity ) {
    return refbookService().svs().validator().canRemoveItem( refbook(), aEntity.id() );
  }

  @Override
  protected void doRemove( ISkRefbookItem aEntity ) {
    refbook().removeItem( aEntity.id() );
  }

  @Override
  protected IList<ISkRefbookItem> doListEntities() {
    return refbook().listItems();
  }

}
