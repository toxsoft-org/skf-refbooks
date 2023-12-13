package org.toxsoft.skf.refbooks.gui.km5;

import static org.toxsoft.core.tsgui.m5.IM5Constants.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.skf.refbooks.gui.km5.IKM5RefbooksConstants.*;
import static org.toxsoft.skf.refbooks.gui.km5.ISkResources.*;
import static org.toxsoft.uskat.core.api.sysdescr.ESkClassPropKind.*;
import static org.toxsoft.uskat.core.gui.ISkCoreGuiConstants.*;
import static org.toxsoft.uskat.core.gui.km5.sded.IKM5SdedConstants.*;
import static org.toxsoft.uskat.core.gui.km5.sded.ISkSdedKm5SharedResources.*;

import java.util.function.*;

import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tsgui.m5.model.impl.*;
import org.toxsoft.core.tslib.av.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.strid.coll.impl.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.impl.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.api.sysdescr.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.km5.*;

/**
 * M5-model of the {@link IDtoRefbookInfo}.
 *
 * @author dima
 */
public class RbedDtoRefbookInfoM5Model
    extends KM5ConnectedModelBase<IDtoRefbookInfo> {

  /**
   * Attribute {@link IDtoRefbookInfo#id()}.
   */
  public final IM5AttributeFieldDef<IDtoRefbookInfo> CLASS_ID = new M5AttributeFieldDef<>( FID_CLASS_ID, DDEF_IDPATH ) {

    @Override
    protected void doInit() {
      setNameAndDescription( STR_N_REFBOOK_ID, STR_D_REFBOOK_ID );
      setFlags( M5FF_INVARIANT | M5FF_COLUMN );
    }

    protected IAtomicValue doGetFieldValue( IDtoRefbookInfo aEntity ) {
      return avStr( aEntity.id() );
    }

  };

  /**
   * Attribute {@link IDtoRefbookInfo#nmName()}.
   */
  public final IM5AttributeFieldDef<IDtoRefbookInfo> NAME = new M5AttributeFieldDef<>( FID_NAME, DDEF_NAME ) {

    @Override
    protected void doInit() {
      setNameAndDescription( STR_N_REFBOOK_NAME, STR_D_REFBOOK_NAME );
      setFlags( M5FF_COLUMN );
    }

    protected IAtomicValue doGetFieldValue( IDtoRefbookInfo aEntity ) {
      return avStr( aEntity.nmName() );
    }

  };

  /**
   * Attribute {@link IDtoRefbookInfo#description()}.
   */
  public final IM5AttributeFieldDef<IDtoRefbookInfo> DESCRIPTION =
      new M5AttributeFieldDef<>( FID_DESCRIPTION, DDEF_DESCRIPTION ) {

        @Override
        protected void doInit() {
          setNameAndDescription( STR_N_CLASS_DESCRIPTION, STR_D_CLASS_DESCRIPTION );
          setFlags( M5FF_COLUMN );
        }

        protected IAtomicValue doGetFieldValue( IDtoRefbookInfo aEntity ) {
          return avStr( aEntity.description() );
        }

      };

  /**
   * Field contains of list attrs {@link IDtoRefbookInfo#attrInfos()}.
   */
  public final IM5MultiModownFieldDef<IDtoRefbookInfo, IDtoAttrInfo> SELF_ATTR_INFOS =
      new M5MultiModownFieldDef<>( FID_SELF_ATTR_INFOS, MID_SDED_ATTR_INFO ) {

        @Override
        protected void doInit() {
          setNameAndDescription( ATTR.nmName(), ATTR.description() );
          params().setStr( TSID_ICON_ID, ICONID_SDED_CLASS_ATTR );
          setFlags( 0 );
        }

        protected IList<IDtoAttrInfo> doGetFieldValue( IDtoRefbookInfo aEntity ) {
          ISkClassInfo clsInfo = skSysdescr().findClassInfo( aEntity.id() );
          if( clsInfo != null ) {
            return getPropsFieldValue( aEntity, clsInfo::attrs, aEntity::attrInfos );
          }
          return aEntity.attrInfos();
        }
      };

  /**
   * Field contains of list linkInfos {@link IDtoRefbookInfo#linkInfos()}.
   */
  public final IM5MultiModownFieldDef<IDtoRefbookInfo, IDtoLinkInfo> SELF_LINK_INFOS =
      new M5MultiModownFieldDef<>( FID_SELF_LINK_INFOS, MID_SDED_LINK_INFO ) {

        @Override
        protected void doInit() {
          setNameAndDescription( LINK.nmName(), LINK.description() );
          params().setStr( TSID_ICON_ID, ICONID_SDED_CLASS_LINK );
          setFlags( 0 );
        }

        protected IList<IDtoLinkInfo> doGetFieldValue( IDtoRefbookInfo aEntity ) {
          ISkClassInfo clsInfo = skSysdescr().findClassInfo( aEntity.id() );
          if( clsInfo != null ) {
            return getPropsFieldValue( aEntity, clsInfo::links, aEntity::linkInfos );
          }
          return aEntity.linkInfos();
        }

      };

  /**
   * Field contains of list rivetInfos {@link IDtoRefbookInfo#rivetInfos()}.
   */
  public final IM5MultiModownFieldDef<IDtoRefbookInfo, IDtoRivetInfo> SELF_RIVET_INFOS =
      new M5MultiModownFieldDef<>( FID_SELF_RIVET_INFOS, MID_SDED_RIVET_INFO ) {

        @Override
        protected void doInit() {
          setNameAndDescription( RIVET.nmName(), RIVET.description() );
          params().setStr( TSID_ICON_ID, ICONID_SDED_CLASS_RIVET );
          setFlags( 0 );
        }

        protected IList<IDtoRivetInfo> doGetFieldValue( IDtoRefbookInfo aEntity ) {
          ISkClassInfo clsInfo = skSysdescr().findClassInfo( aEntity.id() );
          if( clsInfo != null ) {
            return getPropsFieldValue( aEntity, clsInfo::rivets, aEntity::rivetInfos );
          }
          return aEntity.rivetInfos();

        }

      };

  /**
   * Field contains of list clobInfos {@link IDtoRefbookInfo#clobInfos()}.
   */
  public final IM5MultiModownFieldDef<IDtoRefbookInfo, IDtoClobInfo> SELF_CLOB_INFOS =
      new M5MultiModownFieldDef<>( FID_SELF_CLOB_INFOS, MID_SDED_CLOB_INFO ) {

        @Override
        protected void doInit() {
          setNameAndDescription( CLOB.nmName(), CLOB.description() );
          params().setStr( TSID_ICON_ID, ICONID_SDED_CLASS_CLOB );
          setFlags( 0 );
        }

        protected IList<IDtoClobInfo> doGetFieldValue( IDtoRefbookInfo aEntity ) {
          ISkClassInfo clsInfo = skSysdescr().findClassInfo( aEntity.id() );
          if( clsInfo != null ) {
            return getPropsFieldValue( aEntity, clsInfo::clobs, aEntity::clobInfos );
          }
          return aEntity.clobInfos();
        }

      };

  /**
   * Constructor.
   *
   * @param aConn {@link ISkConnection} - Sk-connection to be used in constructor
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public RbedDtoRefbookInfoM5Model( ISkConnection aConn ) {
    super( MID_RBED_DTO_REFBOOK_INFO, IDtoRefbookInfo.class, aConn );
    setNameAndDescription( STR_N_M5M_CLASS, STR_D_M5M_CLASS );
    addFieldDefs( CLASS_ID, NAME, DESCRIPTION, //
        SELF_ATTR_INFOS, //
        SELF_LINK_INFOS, //
        SELF_RIVET_INFOS, //
        SELF_CLOB_INFOS //
    );
    setPanelCreator( new RbedDtoRefbookInfoM5PanelCreator() );
  }

  // ------------------------------------------------------------------------------------
  // M5Model
  //

  @Override
  protected IM5LifecycleManager<IDtoRefbookInfo> doCreateDefaultLifecycleManager() {
    return new RbedDtoRefbookInfoM5LifecycleManager( this, skConn() );
  }

  @Override
  protected IM5LifecycleManager<IDtoRefbookInfo> doCreateLifecycleManager( Object aMaster ) {
    return new RbedDtoRefbookInfoM5LifecycleManager( this, ISkConnection.class.cast( aMaster ) );
  }

  /**
   * Код с использованием Java 8 features устраняет boilerplate code выделения свойств собственно класса без свойств его
   * предков
   *
   * @param <T> шаблон свойства класса
   * @param aEntity - описание класса
   * @param aRefbookPropsSupplier функция поставщик описания конкретного свойства справочника
   * @param aListPropsSupplier функция поставщик списка значений свойств
   * @return список значений собственных свойств
   */
  protected <T extends IDtoClassPropInfoBase> IListEdit<T> getPropsFieldValue( IDtoRefbookInfo aEntity,
      Supplier<ISkClassProps<T>> aRefbookPropsSupplier, Supplier<IStridablesList<T>> aListPropsSupplier ) {
    StridablesList<T> haired = new StridablesList<>();
    // тут выделяем только те которые принадлежат непосредственно этому классу
    ISkClassInfo clsInfo = skSysdescr().findClassInfo( aEntity.id() );
    if( clsInfo != null ) {
      ISkClassProps<T> props = aRefbookPropsSupplier.get();
      IStridablesList<T> listSelf = props.listSelf();
      IStridablesList<T> listAll = props.list();
      // теперь оставляем только не свои
      for( T propInfo : listAll ) {
        if( !listSelf.hasKey( propInfo.id() ) ) {
          haired.add( propInfo );
        }
      }
    }
    // теперь оставляем только те которые свои и вновь добавленные
    IListEdit<T> retVal = new ElemArrayList<>();
    for( T entityPropInfo : aListPropsSupplier.get() ) {
      if( !haired.hasKey( entityPropInfo.id() ) ) {
        retVal.add( entityPropInfo );
      }
    }
    return retVal;
  }

}
