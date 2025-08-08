package org.toxsoft.skf.refbooks.lib.impl;

import static org.toxsoft.core.tslib.utils.TsLibUtils.*;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.strid.coll.impl.*;
import org.toxsoft.core.tslib.bricks.strid.impl.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.api.sysdescr.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;
import org.toxsoft.uskat.core.impl.dto.*;

/**
 * {@link IDtoRefbookInfo} implementation.
 *
 * @author hazard157
 */
public class DtoRefbookInfo
    extends StridableParameterized
    implements IDtoRefbookInfo {

  private final IStridablesListEdit<IDtoAttrInfo>  attrInfos  = new StridablesList<>();
  private final IStridablesListEdit<IDtoClobInfo>  clobInfos  = new StridablesList<>();
  private final IStridablesListEdit<IDtoRivetInfo> rivetInfos = new StridablesList<>();
  private final IStridablesListEdit<IDtoLinkInfo>  linkInfos  = new StridablesList<>();

  /**
   * Constructor.
   *
   * @param aId String - the refbook ID (IDpath)
   * @param aParams {@link IOptionSet} - {@link #params()} initial values
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsIllegalArgumentRtException any ID is not an IDpath
   */
  public DtoRefbookInfo( String aId, IOptionSet aParams ) {
    super( aId, aParams );
  }

  /**
   * Static copy constructor.
   *
   * @param aSource {@link IDtoRefbookInfo} - the source
   * @return {@link DtoRefbookInfo} - deep copy of source
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsIllegalArgumentRtException invalid source
   */
  public static DtoRefbookInfo createDeepCopy( IDtoRefbookInfo aSource ) {
    TsNullArgumentRtException.checkNull( aSource );
    DtoRefbookInfo dto = new DtoRefbookInfo( aSource.id(), aSource.params() );
    dto.attrInfos().setAll( aSource.attrInfos() );
    dto.clobInfos().setAll( aSource.clobInfos() );
    dto.rivetInfos().setAll( aSource.rivetInfos() );
    dto.linkInfos().setAll( aSource.linkInfos() );
    return dto;
  }

  /**
   * Creates the DTO of the specified refbook.
   * <p>
   * Created DTO has only refbook specific properties, without superclass properties.
   *
   * @param aRefbook {@link ISkRefbook} - the refbook to create DTO for
   * @return {@link DtoRefbookInfo} - an editable instance of the refbook describing DTO
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public static DtoRefbookInfo of( ISkRefbook aRefbook ) {
    TsNullArgumentRtException.checkNull( aRefbook );
    ISkSysdescr sysdescr = aRefbook.coreApi().sysdescr();
    ISkClassInfo clsInfo = sysdescr.getClassInfo( aRefbook.itemClassId() );
    DtoRefbookInfo info = new DtoRefbookInfo( aRefbook.id(), clsInfo.params() );
    info.setNameAndDescription( aRefbook.nmName(), aRefbook.description() );
    info.attrInfos().addAll( clsInfo.attrs().listSelf() );
    info.clobInfos().addAll( clsInfo.clobs().listSelf() );
    info.rivetInfos().addAll( clsInfo.rivets().listSelf() );
    info.linkInfos().addAll( clsInfo.links().listSelf() );
    return info;
  }

  /**
   * Creates refbook description.
   *
   * @param aRefbookId String - the refbook ID (IDpath)
   * @param aParams {@link IOptionSet} - {@link #params()} initial values
   * @param aAttrInfos {@link IStridablesList}&lt; {@link IDtoAttrInfo}&lt; - attributes info list
   * @param aClobInfos {@link IStridablesList}&lt; {@link IDtoClobInfo}&lt; - CLOBs info list
   * @param aRivetInfos {@link IStridablesList}&lt; {@link IDtoRivetInfo}&lt; - rivets info list
   * @param aLinkInfos {@link IStridablesList}&lt; {@link IDtoLinkInfo}&lt; - links info list
   * @return {@link DtoRefbookInfo} - created instance
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsIllegalArgumentRtException invalid source
   */
  public static DtoRefbookInfo create( String aRefbookId, IOptionSet aParams, ///
      IStridablesList<IDtoAttrInfo> aAttrInfos, IStridablesList<IDtoClobInfo> aClobInfos,
      IStridablesList<IDtoRivetInfo> aRivetInfos, IStridablesList<IDtoLinkInfo> aLinkInfos ) {
    TsNullArgumentRtException.checkNulls( aRefbookId, aParams );
    DtoRefbookInfo dtoRefbook = new DtoRefbookInfo( aRefbookId, aParams );
    dtoRefbook.attrInfos().addAll( aAttrInfos );
    dtoRefbook.clobInfos().addAll( aClobInfos );
    dtoRefbook.rivetInfos().addAll( aRivetInfos );
    dtoRefbook.linkInfos().addAll( aLinkInfos );
    return dtoRefbook;
  }

  /**
   * Creates refbook definition DTO.
   *
   * @param aRefbookId String - the refbook ID (IDpath)
   * @param aParams {@link IOptionSet} - {@link #params()} initial values
   * @param aPropInfos - {@link DtoAbstractClassPropInfoBase}[] - property definitions array
   * @return {@link DtoRefbookInfo} - created instance
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsIllegalArgumentRtException any ID is not an IDpath
   * @throws TsIllegalArgumentRtException disallowed property kind encountered
   */
  public static DtoRefbookInfo create2( String aRefbookId, IOptionSet aParams, IDtoClassPropInfoBase... aPropInfos ) {
    TsNullArgumentRtException.checkNulls( aRefbookId, aParams );
    TsErrorUtils.checkArrayArg( aPropInfos );
    DtoRefbookInfo dto = new DtoRefbookInfo( aRefbookId, aParams );
    for( IDtoClassPropInfoBase propInf : aPropInfos ) {
      switch( propInf.kind() ) {
        case ATTR: {
          dto.attrInfos.add( (DtoAttrInfo)propInf );
          break;
        }
        case CLOB: {
          dto.clobInfos.add( (DtoClobInfo)propInf );
          break;
        }
        case LINK: {
          dto.linkInfos.add( (DtoLinkInfo)propInf );
          break;
        }
        case RIVET: {
          dto.rivetInfos.add( (DtoRivetInfo)propInf );
          break;
        }
        case CMD:
        case EVENT:
        case RTDATA:
          throw new TsIllegalArgumentRtException( propInf.kind().id() );
        default:
          break;
      }
    }
    return dto;
  }

  // ------------------------------------------------------------------------------------
  // IDtoRefbookInfo
  //

  @Override
  public IStridablesListEdit<IDtoAttrInfo> attrInfos() {
    return attrInfos;
  }

  @Override
  public IStridablesListEdit<IDtoRivetInfo> rivetInfos() {
    return rivetInfos;
  }

  @Override
  public IStridablesListEdit<IDtoLinkInfo> linkInfos() {
    return linkInfos;
  }

  @Override
  public IStridablesListEdit<IDtoClobInfo> clobInfos() {
    return clobInfos;
  }

  @SuppressWarnings( { "rawtypes", "unchecked" } )
  @Override
  public <T extends IDtoClassPropInfoBase> IStridablesList<T> propInfos( ESkClassPropKind aKind ) {
    TsNullArgumentRtException.checkNull( aKind );
    return switch( aKind ) {
      case ATTR -> (IStridablesList)attrInfos;
      case RIVET -> (IStridablesList)rivetInfos;
      case CLOB -> (IStridablesList)clobInfos;
      case RTDATA -> IStridablesList.EMPTY;
      case LINK -> (IStridablesList)linkInfos;
      case CMD -> IStridablesList.EMPTY;
      case EVENT -> IStridablesList.EMPTY;
    };
  }

  // ------------------------------------------------------------------------------------
  // Object
  //

  @SuppressWarnings( "boxing" )
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder( super.toString() );
    for( ESkClassPropKind k : ESkClassPropKind.asList() ) {
      IStridablesList<?> cp = propInfos( k );
      sb.append( String.format( ", %s[%d]", k.id(), cp.size() ) ); //$NON-NLS-1$
    }
    return sb.toString();
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = PRIME * result + attrInfos.hashCode();
    result = PRIME * result + rivetInfos.hashCode();
    result = PRIME * result + clobInfos.hashCode();
    result = PRIME * result + linkInfos.hashCode();
    return result;
  }

  @Override
  public boolean equals( Object aThat ) {
    if( super.equals( aThat ) ) {
      if( aThat instanceof DtoRefbookInfo that ) {
        return this.attrInfos.equals( that.attrInfos ) && //
            this.rivetInfos.equals( that.rivetInfos ) && //
            this.clobInfos.equals( that.clobInfos ) && //
            this.linkInfos.equals( that.linkInfos );
      }
    }
    return false;
  }

}
