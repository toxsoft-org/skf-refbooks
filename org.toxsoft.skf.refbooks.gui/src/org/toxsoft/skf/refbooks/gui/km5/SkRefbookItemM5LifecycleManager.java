package org.toxsoft.skf.refbooks.gui.km5;

import static org.toxsoft.skf.refbooks.gui.km5.ISkResources.*;
import static org.toxsoft.uskat.core.ISkHardConstants.*;

import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.bricks.strid.impl.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.primtypes.*;
import org.toxsoft.core.tslib.coll.primtypes.impl.*;
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.api.sysdescr.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.km5.*;
import org.toxsoft.uskat.core.impl.dto.*;

/**
 * LM for {@link SkRefbookItemM5Model}.
 *
 * @author hazard157
 * @author dima
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
    return coreApi().getService( ISkRefbookService.SERVICE_ID );
  }

  private ISkRefbook refbook() {
    ISkRefbook rb = refbookService().findRefbookByItemClassId( model().id() );
    TsInternalErrorRtException.checkNull( rb );
    return rb;
  }

  @SuppressWarnings( "unchecked" )
  private IStringMapEdit<ISkidList> linksFromBunch( IM5Bunch<ISkRefbookItem> aValues ) {
    ISkRefbook rb = refbookService().findRefbookByItemClassId( model().id() );
    ISkClassInfo cInfo = coreApi().sysdescr().getClassInfo( rb.itemClassId() );
    // занесем значения связей
    IStringMapEdit<ISkidList> linksMap = new StringMap<>();
    for( IDtoLinkInfo linkInfo : cInfo.links().list() ) {
      // связи множественные и одиночные обрабатываем по разному
      if( linkInfo.linkConstraint().maxCount() == 1 ) {
        ISkObject linkedObj = aValues.getAs( linkInfo.id(), ISkObject.class );
        if( linkedObj != null ) {
          linksMap.put( linkInfo.id(), new SkidList( linkedObj.skid() ) );
        }
        else {
          linksMap.put( linkInfo.id(), ISkidList.EMPTY );
        }
      }
      else {
        IList<ISkObject> linkedObs = (IList<ISkObject>)aValues.get( linkInfo.id() );
        SkidList linkedSkids = new SkidList();
        for( ISkObject linkedObject : linkedObs ) {
          linkedSkids.add( linkedObject.skid() );
        }
        linksMap.put( linkInfo.id(), linkedSkids );
      }
    }
    return linksMap;
  }

  @SuppressWarnings( "unchecked" )
  private IStringMapEdit<ISkidList> rivetsFromBunch( IM5Bunch<ISkRefbookItem> aValues ) {
    ISkRefbook rb = refbookService().findRefbookByItemClassId( model().id() );
    ISkClassInfo cInfo = coreApi().sysdescr().getClassInfo( rb.itemClassId() );
    // занесем значения связей
    IStringMapEdit<ISkidList> rivetsMap = new StringMap<>();
    for( IDtoRivetInfo rivetInfo : cInfo.rivets().list() ) {
      // заклепки множественные и одиночные обрабатываем по разному
      if( rivetInfo.count() == 1 ) {
        ISkObject linkedObj = aValues.getAs( rivetInfo.id(), ISkObject.class );
        if( linkedObj != null ) {
          rivetsMap.put( rivetInfo.id(), new SkidList( linkedObj.skid() ) );
        }
        else {
          rivetsMap.put( rivetInfo.id(), ISkidList.EMPTY );
        }
      }
      else {
        IList<ISkObject> linkedObjs = (IList<ISkObject>)aValues.get( rivetInfo.id() );
        SkidList linkedSkids = new SkidList();
        for( ISkObject linkedObject : linkedObjs ) {
          linkedSkids.add( linkedObject.skid() );
        }
        rivetsMap.put( rivetInfo.id(), linkedSkids );
      }
    }
    return rivetsMap;
  }

  private static ValidationResult canMakeDtoFromBunch( IM5Bunch<ISkRefbookItem> aValues ) {
    String itemId = aValues.getAsAv( AID_STRID ).asString();
    ValidationResult vr = StridUtils.validateIdPath( itemId );
    return vr;
  }

  private IDtoFullObject createItemDto( IM5Bunch<ISkRefbookItem> aValues ) {
    String classId = refbook().itemClassId();
    String strid = aValues.getAsAv( AID_STRID ).asString();
    Skid skid = new Skid( classId, strid );

    ISkClassInfo cInfo = coreApi().sysdescr().getClassInfo( classId );
    IOptionSetEdit attrs = new OptionSet();
    // занесем значения атрибутов
    for( IDtoAttrInfo attrInfo : cInfo.attrs().list() ) {
      if( !ISkHardConstants.isSkSysAttr( attrInfo ) ) {
        attrs.setValue( attrInfo.id(), aValues.getAsAv( attrInfo.id() ) );
      }
    }
    IDtoObject dtoObj = new DtoObject( skid, attrs, IStringMap.EMPTY );
    // создаем карту связей
    IStringMap<ISkidList> links = linksFromBunch( aValues );
    // TODO implements CLOB support
    DtoFullObject retVal = new DtoFullObject( dtoObj, IStringMap.EMPTY, links );
    // создаем карту заклепок
    IStringMap<ISkidList> rivets = rivetsFromBunch( aValues );
    retVal.rivets().map().putAll( rivets );
    return retVal;
  }

  // ------------------------------------------------------------------------------------
  // KM5LifecycleManagerBasic
  //

  @Override
  protected ValidationResult doBeforeCreate( IM5Bunch<ISkRefbookItem> aValues ) {
    ValidationResult vr = canMakeDtoFromBunch( aValues );
    if( vr.isError() ) {
      return vr;
    }
    ISkRefbook rb = refbookService().findRefbookByItemClassId( model().id() );
    IDtoFullObject dtoItem = createItemDto( aValues );
    String newId = dtoItem.strid();
    if( rb.findItem( newId ) != null ) {
      return ValidationResult.error( FMT_ERR_ITEM_ALREADY_EXISTS, newId );
    }

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
