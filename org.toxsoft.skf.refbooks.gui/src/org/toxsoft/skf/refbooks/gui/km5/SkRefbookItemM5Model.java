package org.toxsoft.skf.refbooks.gui.km5;

import static org.toxsoft.core.tsgui.m5.IM5Constants.*;
import static org.toxsoft.core.tsgui.m5.gui.mpc.IMultiPaneComponentConstants.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;

import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.m5.gui.mpc.impl.*;
import org.toxsoft.core.tsgui.m5.gui.panels.*;
import org.toxsoft.core.tsgui.m5.gui.panels.impl.*;
import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tsgui.m5.model.impl.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.lib.impl.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.km5.*;

/**
 * Model for {@link ISkRefbookItem} of all refbooks.
 * <p>
 * The model ID must be the the refbook item class ID {@link ISkRefbook#itemClassId()}.
 *
 * @author hazard157
 * @author dima
 */
public class SkRefbookItemM5Model
    extends KM5ModelBasic<ISkRefbookItem> {

  /**
   * Constructor.
   *
   * @param aId String - the model ID must be the refbook item class ID {@link ISkRefbook#itemClassId()}
   * @param aConn {@link ISkConnection} - Sk-connection to be used in constructor
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public SkRefbookItemM5Model( String aId, ISkConnection aConn ) {
    super( aId, ISkRefbookItem.class, aConn );
    addFieldDefs( STRID, NAME, DESCRIPTION );
    ISkRefbookService rbServ = aConn.coreApi().getService( ISkRefbookService.SERVICE_ID );
    ISkRefbook rb = rbServ.findRefbookByItemClassId( id() );
    TsInternalErrorRtException.checkNull( rb );
    IDtoRefbookInfo rbInfo = DtoRefbookInfo.of( rb );
    // attributes
    for( IDtoAttrInfo attrInfo : rbInfo.attrInfos() ) {
      KM5AttributeFieldDef<ISkRefbookItem> fd = new KM5AttributeFieldDef<>( attrInfo );
      fd.addFlags( M5FF_COLUMN );
      addFieldDefs( fd );
    }
    // TODO CLOBs
    for( IDtoClobInfo clobIinf : rbInfo.clobInfos() ) {
      // TODO create class KM5ClobFieldDef and use it here
    }
    // rivets
    for( IDtoRivetInfo rivetInfo : rbInfo.rivetInfos() ) {
      M5FieldDef<ISkObject, ?> fd;
      if( rivetInfo.count() == 1 ) {
        fd = new KM5SingleRivetFieldDef( rivetInfo );
      }
      else {
        fd = new KM5MultiRivetFieldDef( rivetInfo );
      }
      fd.setFlags( M5FF_COLUMN );
      addFieldDefs( fd );
    }
    // links
    for( IDtoLinkInfo linkInfo : rbInfo.linkInfos() ) {
      M5FieldDef<ISkObject, ?> fd;
      if( linkInfo.linkConstraint().maxCount() == 1 ) {
        fd = new KM5SingleLinkFieldDef( linkInfo );
      }
      else {
        fd = new KM5MultiLinkFieldDef( linkInfo );
      }
      fd.addFlags( M5FF_COLUMN );
      addFieldDefs( fd );
    }
    setPanelCreator( new M5DefaultPanelCreator<>() {

      @Override
      protected IM5CollectionPanel<ISkRefbookItem> doCreateCollEditPanel( ITsGuiContext aContext,
          IM5ItemsProvider<ISkRefbookItem> aItemsProvider, IM5LifecycleManager<ISkRefbookItem> aLifecycleManager ) {
        OPDEF_IS_ACTIONS_CRUD.setValue( aContext.params(), AV_TRUE );
        OPDEF_IS_FILTER_PANE.setValue( aContext.params(), AV_TRUE );
        MultiPaneComponentModown<ISkRefbookItem> mpc =
            new SkRefbookItemM5Mpc( aContext, rb, model(), aItemsProvider, aLifecycleManager );
        return new M5CollectionPanelMpcModownWrapper<>( mpc, false );
      }
    } );
  }

  @Override
  protected IM5LifecycleManager<ISkRefbookItem> doCreateLifecycleManager( Object aMaster ) {
    return new SkRefbookItemM5LifecycleManager( this, ISkConnection.class.cast( aMaster ) );
  }

  // /**
  // * Создает описание поля связи 1-1
  // *
  // * @param aPropInfo описание связи/заклепки
  // * @param isInvariant true - поле инвариант, false - редактируется на любой стадии
  // * @param aRightClassIds - список id 'правых' классов
  // * @return описание поля связи 1-1
  // */
  // private IM5FieldDef<ISkRefbookItem, ISkObject> singleLinkFieldDef( IDtoClassPropInfoBase aPropInfo,
  // IStringList aRightClassIds, boolean isInvariant ) {
  // M5SingleLookupFieldDef<ISkRefbookItem, ISkObject> retVal =
  // new M5SingleLookupFieldDef<>( aPropInfo.id(), IGwHardConstants.GW_ROOT_CLASS_ID ) {
  //
  // @Override
  // protected void doInit() {
  // setNameAndDescription( aPropInfo.nmName(), aPropInfo.description() );
  // }
  //
  // /**
  // * Допускаем возможность того, что поле не инициализировано
  // */
  // @Override
  // public boolean canUserSelectNull() {
  // return false;
  // }
  //
  // /**
  // * Отображает список объектов по данной связи
  // * <p>
  // *
  // * @param aEntity &lt;T&gt; - экземпляр моделированого объекта
  // * @return String - отображаемый текст
  // */
  // @Override
  // protected String doGetFieldValueName( ISkRefbookItem aEntity ) {
  // if( skConn() == null || skConn().state() != ESkConnState.ACTIVE ) {
  // return TsLibUtils.EMPTY_STRING;
  // }
  // try {
  // ISkLinkService linkService = skConn().coreApi().linkService();
  // ISkidList linkIds =
  // linkService.getLinkFwd( new Skid( aEntity.classId(), aEntity.id() ), aPropInfo.id() ).rightSkids();
  // ISkObjectService os = skConn().coreApi().objService();
  // IList<ISkObject> linkedObjs = os.getObjs( linkIds );
  // return linkedObjs.isEmpty() ? TsLibUtils.EMPTY_STRING : linkedObjs.first().nmName();
  // }
  // catch( Exception ex ) {
  // LoggerUtils.errorLogger().error( ex );
  // }
  // return TsLibUtils.EMPTY_STRING;
  // }
  //
  // @Override
  // protected ISkObject doGetFieldValue( ISkRefbookItem aEntity ) {
  // if( skConn() == null || skConn().state() != ESkConnState.ACTIVE ) {
  // return ISkObject.NONE;
  // }
  // try {
  // ISkLinkService linkService = skConn().coreApi().linkService();
  // ISkidList linkIds =
  // linkService.getLinkFwd( new Skid( aEntity.classId(), aEntity.id() ), aPropInfo.id() ).rightSkids();
  // ISkObjectService os = skConn().coreApi().objService();
  // IList<ISkObject> linkedObjs = os.getObjs( linkIds );
  // if( linkedObjs.isEmpty() ) {
  // return ISkObject.NONE;
  // }
  // return linkedObjs.first();
  // }
  // catch( Exception ex ) {
  // LoggerUtils.errorLogger().error( ex );
  // return ISkObject.NONE;
  // }
  // }
  //
  // @Override
  // public IM5LookupProvider<ISkObject> lookupProvider() {
  // return () -> baseListItems( aRightClassIds );
  // }
  // };
  // if( isInvariant ) {
  // retVal.setFlags( M5FF_COLUMN | M5FF_INVARIANT );
  // }
  // else {
  // retVal.setFlags( M5FF_COLUMN );
  // }
  // return retVal;
  // }
  //
  // /**
  // * Создает описание поля связи 1-много
  // *
  // * @param aPropInfo описание связи/заклепки
  // * @param isInvariant true - поле инвариант, false - редактируется на любой стадии
  // * @param aRightClassIds - список id 'правых' классов
  // * @return поле описание связи 1-много
  // */
  // private IM5FieldDef<ISkRefbookItem, ?> multyLinkField( IDtoClassPropInfoBase aPropInfo, IStringList aRightClassIds,
  // boolean isInvariant ) {
  // M5MultiLookupFieldDef<ISkRefbookItem, ISkObject> retVal =
  // new M5MultiLookupFieldDef<>( aPropInfo.id(), IGwHardConstants.GW_ROOT_CLASS_ID ) {
  //
  // @Override
  // protected void doInit() {
  // setNameAndDescription( aPropInfo.nmName(), aPropInfo.description() );
  // }
  //
  // /**
  // * Отображает список объектов по данной связи
  // * <p>
  // *
  // * @param aEntity &lt;T&gt; - экземпляр моделированого объекта
  // * @return String - отображаемый текст
  // */
  // @Override
  // protected String doGetFieldValueName( ISkRefbookItem aEntity ) {
  // if( skConn() == null || skConn().state() != ESkConnState.ACTIVE ) {
  // return TsLibUtils.EMPTY_STRING;
  // }
  // try {
  // ISkLinkService linkService = skConn().coreApi().linkService();
  // ISkidList linkIds =
  // linkService.getLinkFwd( new Skid( aEntity.classId(), aEntity.id() ), aPropInfo.id() ).rightSkids();
  // ISkObjectService os = skConn().coreApi().objService();
  // IList<ISkObject> linkedObjs = os.getObjs( linkIds );
  // StringBuilder sb = new StringBuilder();
  // for( ISkObject obj : linkedObjs ) {
  // sb.append( obj.nmName() + ", " ); //$NON-NLS-1$
  // }
  // // выкусываем финальную запятую
  // if( sb.length() > 0 ) {
  // return sb.substring( 0, sb.length() - 2 );
  // }
  // }
  // catch( Exception ex ) {
  // LoggerUtils.errorLogger().error( ex );
  // }
  // return TsLibUtils.EMPTY_STRING;
  // }
  //
  // @Override
  // protected IList<ISkObject> doGetFieldValue( ISkRefbookItem aEntity ) {
  // if( skConn() == null || skConn().state() != ESkConnState.ACTIVE ) {
  // return IList.EMPTY;
  // }
  // try {
  // ISkLinkService linkService = skConn().coreApi().linkService();
  // ISkidList linkIds =
  // linkService.getLinkFwd( new Skid( aEntity.classId(), aEntity.id() ), aPropInfo.id() ).rightSkids();
  // ISkObjectService os = skConn().coreApi().objService();
  // IList<ISkObject> linkedObjs = os.getObjs( linkIds );
  // if( linkedObjs.isEmpty() ) {
  // return IList.EMPTY;
  // }
  // return linkedObjs;
  // }
  // catch( Exception ex ) {
  // LoggerUtils.errorLogger().error( ex );
  // return IList.EMPTY;
  // }
  // }
  //
  // @Override
  // public IM5LookupProvider<ISkObject> lookupProvider() {
  // return () -> baseListItems( aRightClassIds );
  // }
  // };
  // if( isInvariant ) {
  // retVal.setFlags( M5FF_COLUMN | M5FF_INVARIANT );
  // }
  // else {
  // retVal.setFlags( M5FF_COLUMN );
  // }
  // return retVal;
  // }
  //
  // /**
  // * По описанию связи получить список связанных объектов
  // *
  // * @param aRightClassIds id допустимых по связи классов
  // * @return список связанных объектов
  // */
  // IList<ISkObject> baseListItems( IStringList aRightClassIds ) {
  // ISkObjectService os = skConn().coreApi().objService();
  // IListEdit<ISkObject> lookupObjs = new ElemLinkedBundleList<>();
  // for( String classId : aRightClassIds ) {
  // lookupObjs.addAll( os.listObjs( classId, true ) );
  // }
  // return lookupObjs;
  // }

}
