package org.toxsoft.skf.refbooks.lib;

import static org.toxsoft.core.tslib.av.EAtomicType.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.skf.refbooks.lib.ISkRefbookService.*;
import static org.toxsoft.skf.refbooks.lib.ISkResources.*;
import static org.toxsoft.uskat.core.ISkHardConstants.*;

import org.toxsoft.core.tslib.av.*;
import org.toxsoft.core.tslib.av.impl.*;
import org.toxsoft.core.tslib.av.metainfo.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.bricks.strid.coll.impl.*;
import org.toxsoft.core.tslib.bricks.strid.impl.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.gw.gwid.*;
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.core.tslib.utils.txtmatch.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;
import org.toxsoft.uskat.core.api.users.ability.*;
import org.toxsoft.uskat.core.impl.dto.*;

/**
 * Unchangeable constants of the refbooks service.
 *
 * @author hazard157
 */
@SuppressWarnings( "nls" )
public interface ISkRefbookServiceHardConstants {

  /**
   * Identifier prefix of all classes owned by this service.
   */
  String CLSID_PREFIX = SK_ID + ".refbooks.class";

  /**
   * {@link ISkRefbook} class identifier.
   */
  String CLSID_REFBOOK = CLSID_PREFIX + ".Refbook";

  /**
   * Refbook item classes identifier starting IDpath.
   */
  String CLSID_PREFIX_REFBOOK_ITEM = CLSID_PREFIX + ".Item";

  /**
   * {@link TextMatcher} accepts class IDs of the refbook items {@link ISkRefbook#itemClassId()}.
   */
  TextMatcher REFBOOK_ITEM_CLASS_ID_MATCHER = new TextMatcher( ETextMatchMode.STARTS, CLSID_PREFIX_REFBOOK_ITEM, true );

  /**
   * ID of attribute {@link #ATRINF_ITEM_CLASS_ID}.
   */
  String ATRID_ITEM_CLASS_ID = "ItemClassId";

  /**
   * Attribute {@link ISkRefbook#itemClassId()}.
   */
  IDtoAttrInfo ATRINF_ITEM_CLASS_ID = DtoAttrInfo.create2( ATRID_ITEM_CLASS_ID, DDEF_IDPATH, //
      TSID_NAME, STR_ITEM_CLASS_ID, //
      TSID_DESCRIPTION, STR_ITEM_CLASS_ID_D, //
      TSID_DEFAULT_VALUE, AvUtils.AV_STR_EMPTY //
  );

  // ------------------------------------------------------------------------------------
  // Refbook items change event
  //

  /**
   * Refbook item change event ID.
   */
  String EVID_REFBOOK_ITEM_CHANGE = "RefbookItemChange"; //$NON-NLS-1$

  /**
   * Event param: the SKID of the user who changed refbook.
   */
  String EVPRMID_CHANGE_AUTHOR = "changeAuthor";

  /**
   * Event param: kind of change of type {@link ECrudOp}.
   * <p>
   * Note: Parameter have {@link ECrudOp#CREATE}, {@link ECrudOp#EDIT} and {@link ECrudOp#REMOVE} values.
   * {@link ECrudOp#LIST} never is generated.
   */
  String EVPRMID_CRUD_OP = "crudOp";

  /**
   * Event param: the SKID of the affected item.
   */
  String EVPRMID_ITEM_SKID = "itemSkid";

  /**
   * Event param: old (before item change) values of the attributes.
   * <p>
   * For {@link ECrudOp#CREATE} this parameter does not no present or if present, has {@link IAtomicValue#NULL} value.
   */
  String EVPRMID_OLD_ATTRS = "oldAttrs";

  /**
   * Event param: new (after item change) values of the attributes.
   * <p>
   * For {@link ECrudOp#REMOVE} this parameter does not no present or if present, has {@link IAtomicValue#NULL} value.
   */
  String EVPRMID_NEW_ATTRS = "newAttrs";

  /**
   * Event param: old (before item change) values of the item links.
   * <p>
   * For {@link ECrudOp#LIST} this parameter does not no present or if present, has {@link IAtomicValue#NULL} value.
   */
  String EVPRMID_OLD_LINKS = "oldLinks";

  /**
   * Event param: new (after item change) values of the item links.
   * <p>
   * For {@link ECrudOp#REMOVE} this parameter does not no present or if present, has {@link IAtomicValue#NULL} value.
   */
  String EVPRMID_NEW_LINKS = "newLinks";

  /**
   * Event param: old (before item change) values of the item rivets.
   * <p>
   * For {@link ECrudOp#LIST} this parameter does not no present or if present, has {@link IAtomicValue#NULL} value.
   */
  String EVPRMID_OLD_RIVETS = "oldRivets";

  /**
   * Event param: new (after item change) values of the item rivets.
   * <p>
   * For {@link ECrudOp#REMOVE} this parameter does not no present or if present, has {@link IAtomicValue#NULL} value.
   */
  String EVPRMID_NEW_RIVETS = "newRivets";

  /**
   * {@link #EVPRMID_CHANGE_AUTHOR} parameter definition.
   */
  IDataDef EVPRMDEF_CHANGE_AUTHOR = DataDef.create( EVPRMID_CHANGE_AUTHOR, VALOBJ, //
      TSID_NAME, STR_CHANGE_AUTHOR, //
      TSID_DESCRIPTION, STR_CHANGE_AUTHOR_D, //
      TSID_IS_MANDATORY, AV_TRUE, //
      TSID_KEEPER_ID, Skid.KEEPER_ID, //
      TSID_IS_NULL_ALLOWED, AV_FALSE //
  );

  /**
   * {@link #EVPRMID_CRUD_OP} parameter definition.
   */
  IDataDef EVPRMDEF_CRUD_OP = DataDef.create( EVPRMID_CRUD_OP, VALOBJ, //
      TSID_NAME, STR_CRUD_OP, //
      TSID_DESCRIPTION, STR_CRUD_OP_D, //
      TSID_IS_MANDATORY, AV_TRUE, //
      TSID_KEEPER_ID, ECrudOp.KEEPER_ID, //
      TSID_IS_NULL_ALLOWED, AV_FALSE //
  );

  /**
   * {@link #EVPRMID_ITEM_SKID} parameter definition.
   */
  IDataDef EVPRMDEF_ITEM_SKID = DataDef.create( EVPRMID_ITEM_SKID, VALOBJ, //
      TSID_NAME, STR_ITEM_SKID, //
      TSID_DESCRIPTION, STR_ITEM_SKID_D, //
      TSID_IS_MANDATORY, AV_FALSE, //
      TSID_KEEPER_ID, Skid.KEEPER_ID, //
      TSID_IS_NULL_ALLOWED, AV_FALSE //
  );

  /**
   * {@link #EVPRMID_OLD_ATTRS} parameter definition.
   */
  IDataDef EVPRMDEF_OLD_ATTRS = DataDef.create( EVPRMID_OLD_ATTRS, VALOBJ, //
      TSID_NAME, STR_OLD_ATTRS, //
      TSID_DESCRIPTION, STR_OLD_ATTRS_D, //
      TSID_IS_MANDATORY, AV_TRUE, //
      TSID_KEEPER_ID, OptionSetKeeper.KEEPER_ID, //
      TSID_IS_NULL_ALLOWED, AV_FALSE //
  );

  /**
   * {@link #EVPRMID_NEW_ATTRS} parameter definition.
   */
  IDataDef EVPRMDEF_NEW_ATTRS = DataDef.create( EVPRMID_NEW_ATTRS, VALOBJ, //
      TSID_NAME, STR_NEW_ATTRS, //
      TSID_DESCRIPTION, STR_NEW_ATTRS_D, //
      TSID_IS_MANDATORY, AV_FALSE, //
      TSID_KEEPER_ID, OptionSetKeeper.KEEPER_ID, //
      TSID_IS_NULL_ALLOWED, AV_TRUE, //
      TSID_DEFAULT_VALUE, IAtomicValue.NULL //
  );

  /**
   * {@link #EVPRMID_OLD_LINKS} parameter definition.
   */
  IDataDef EVPRMDEF_OLD_LINKS = DataDef.create( EVPRMID_OLD_LINKS, VALOBJ, //
      TSID_NAME, STR_OLD_LINKS, //
      TSID_DESCRIPTION, STR_OLD_LINKS_D, //
      TSID_IS_MANDATORY, AV_FALSE, //
      TSID_KEEPER_ID, MappedSkids.KEEPER_ID, //
      TSID_IS_NULL_ALLOWED, AV_TRUE, //
      TSID_DEFAULT_VALUE, IAtomicValue.NULL //
  );

  /**
   * {@link #EVPRMID_NEW_LINKS} parameter definition.
   */
  IDataDef EVPRMDEF_NEW_LINKS = DataDef.create( EVPRMID_NEW_LINKS, VALOBJ, //
      TSID_NAME, STR_NEW_LINKS, //
      TSID_DESCRIPTION, STR_NEW_LINKS_D, //
      TSID_IS_MANDATORY, AV_FALSE, //
      TSID_KEEPER_ID, MappedSkids.KEEPER_ID, //
      TSID_IS_NULL_ALLOWED, AV_TRUE, //
      TSID_DEFAULT_VALUE, IAtomicValue.NULL );

  /**
   * {@link #EVPRMID_OLD_RIVETS} parameter definition.
   */
  IDataDef EVPRMDEF_OLD_RIVETS = DataDef.create( EVPRMID_OLD_RIVETS, VALOBJ, //
      TSID_NAME, STR_OLD_RIVETS, //
      TSID_DESCRIPTION, STR_OLD_RIVETS_D, //
      TSID_IS_MANDATORY, AV_FALSE, //
      TSID_KEEPER_ID, MappedSkids.KEEPER_ID, //
      TSID_IS_NULL_ALLOWED, AV_TRUE, //
      TSID_DEFAULT_VALUE, IAtomicValue.NULL //
  );

  /**
   * {@link #EVPRMID_NEW_RIVETS} parameter definition.
   */
  IDataDef EVPRMDEF_NEW_RIVETS = DataDef.create( EVPRMID_NEW_RIVETS, VALOBJ, //
      TSID_NAME, STR_NEW_RIVETS, //
      TSID_DESCRIPTION, STR_NEW_RIVETS_D, //
      TSID_IS_MANDATORY, AV_FALSE, //
      TSID_KEEPER_ID, MappedSkids.KEEPER_ID, //
      TSID_IS_NULL_ALLOWED, AV_TRUE, //
      TSID_DEFAULT_VALUE, IAtomicValue.NULL );

  /**
   * Abstract GWID of the refbook change event.
   */
  Gwid EVGDIW_REFBOOK_EDIT = Gwid.createEvent( CLSID_REFBOOK, EVID_REFBOOK_ITEM_CHANGE );

  /**
   * Description of the refbook change event.
   * <p>
   * Note: CLOB values are not stored in the event.
   */
  IDtoEventInfo EVDTO_REFBOOK_EDIT = DtoEventInfo.create1( EVID_REFBOOK_ITEM_CHANGE, true, //
      new StridablesList<>( //
          EVPRMDEF_CHANGE_AUTHOR, //
          EVPRMDEF_CRUD_OP, //
          EVPRMDEF_ITEM_SKID, //
          EVPRMDEF_OLD_ATTRS, //
          EVPRMDEF_NEW_ATTRS, //
          EVPRMDEF_OLD_LINKS, //
          EVPRMDEF_NEW_LINKS, //
          EVPRMDEF_OLD_RIVETS, //
          EVPRMDEF_NEW_RIVETS //
      ), //
      OptionSetUtils.createOpSet( //
          TSID_NAME, STR_REFBOOK_ITEM_CHANGE, //
          TSID_DESCRIPTION, STR_REFBOOK_ITEM_CHANGE_D //
      ) //
  );

  // ------------------------------------------------------------------------------------
  // static API
  //
  /**
   * Determines if argument may be refbook item class ID.
   * <p>
   * Performs only syntactic check of ID.
   *
   * @param aClassId String - ID to be checked
   * @return boolean <code>true</code> - if argument may be refbook item ID
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  static boolean isProbableRefbookItemClassId( String aClassId ) {
    if( StridUtils.isValidIdPath( aClassId ) ) {
      return StridUtils.startsWithIdPath( aClassId, CLSID_PREFIX_REFBOOK_ITEM );
    }
    return false;
  }

  /**
   * Converts the refbook ID {@link ISkRefbook#id()} to the item class identifier ID {@link ISkRefbook#itemClassId()}.
   *
   * @param aRefbookId String - the refbook ID {@link ISkRefbook#id()}
   * @return String - refbook items class ID
   */
  static String makeItemClassIdFromRefbookId( String aRefbookId ) {
    return StridUtils.makeIdPath( CLSID_PREFIX_REFBOOK_ITEM, aRefbookId );
  }

  /**
   * Converts the item class identifier ID {@link ISkRefbook#itemClassId()} to the refbook ID {@link ISkRefbook#id()}.
   *
   * @param aItemClassId String - refbook items class ID
   * @return the refbook ID {@link ISkRefbook#id()}
   */
  static String makeRefbookIdFromItemClassId( String aItemClassId ) {
    return StridUtils.removeStartingIdNames( aItemClassId,
        StridUtils.getComponents( CLSID_PREFIX_REFBOOK_ITEM ).size() );
  }

  /**
   * Constructs the refbook object {@link Skid}.
   *
   * @param aRefbookId String - refbook ID
   * @return {@link Skid} - the refbook object {@link Skid}
   */
  static Skid makeRefbookObjSkid( String aRefbookId ) {
    return new Skid( CLSID_REFBOOK, aRefbookId );
  }

  /**
   * id тип возможности «Редактор справочников»
   */
  String ABKINDID_REFBOOKS = SERVICE_ID + ".abkind.refbooks";

  /**
   * id возможности редактирования значений
   */
  String ABILITYID_EDIT_REFBOOK_VALUES = SERVICE_ID + ".ability.edit_refbook_values"; //

  /**
   * id возможности редактирования структуры
   */
  String ABILITYID_EDIT_REFBOOK_SRUCTS = SERVICE_ID + ".ability.edit_refbook_structs"; //

  /**
   * создание «своего» типа
   */
  IDtoSkAbilityKind ABKIND_REFBOOKS =
      DtoSkAbilityKind.create( ABKINDID_REFBOOKS, STR_ABKIND_REFBOOKS, STR_ABKIND_REFBOOKS_D );

  /**
   * создание возможности редактирования значений
   */
  IDtoSkAbility ABILITY_EDIT_REFBOOK_VALUES = DtoSkAbility.create( ABILITYID_EDIT_REFBOOK_VALUES, ABKINDID_REFBOOKS,
      STR_ABILITY_EDIT_REFBOOK_VALUES, STR_ABILITY_EDIT_REFBOOKS_VALUE_D );

  /**
   * создание возможности редактирования структуры
   */
  IDtoSkAbility ABILITY_EDIT_REFBOOK_STRUCTS = DtoSkAbility.create( ABILITYID_EDIT_REFBOOK_SRUCTS, ABKINDID_REFBOOKS,
      STR_ABILITY_EDIT_REFBOOK_STRUCTS, STR_ABILITY_EDIT_REFBOOK_STRUCTS_D );
}
