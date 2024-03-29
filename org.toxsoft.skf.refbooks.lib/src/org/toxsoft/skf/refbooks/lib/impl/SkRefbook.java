package org.toxsoft.skf.refbooks.lib.impl;

import static org.toxsoft.skf.refbooks.lib.ISkRefbookServiceHardConstants.*;

import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.strid.coll.impl.*;
import org.toxsoft.core.tslib.bricks.validator.impl.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.coll.impl.*;
import org.toxsoft.core.tslib.gw.gwid.*;
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.api.evserv.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.api.sysdescr.*;
import org.toxsoft.uskat.core.impl.*;
import org.toxsoft.uskat.core.impl.dto.*;
import org.toxsoft.uskat.core.utils.*;

/**
 * {@link ISkRefbook} implementation.
 *
 * @author hazard157
 */
class SkRefbook
    extends SkObject
    implements ISkRefbook {

  static final ISkObjectCreator<SkRefbook> CREATOR = SkRefbook::new;

  SkRefbook( Skid aSkid ) {
    super( aSkid );
  }

  // ------------------------------------------------------------------------------------
  // Implementation
  //

  private void fillEventWithOldValues( SkEventBuilder aEventBuilder, ISkRefbookItem aItem ) {
    if( aItem == null ) {
      return;
    }
    aEventBuilder.eventParams().setValobj( EVPRMID_OLD_ATTRS, aItem.attrs() );
    aEventBuilder.eventParams().setValobj( EVPRMID_OLD_RIVETS, aItem.rivets() );
    MappedSkids linksMap = new MappedSkids();
    ISkClassInfo itemClassInfo = coreApi().sysdescr().getClassInfo( itemClassId() );
    for( String lid : itemClassInfo.links().list().ids() ) {
      linksMap.map().put( lid, aItem.getLinkSkids( lid ) );
    }
    aEventBuilder.eventParams().setValobj( EVPRMID_OLD_LINKS, linksMap );
  }

  private void fillEventWithNewValues( SkEventBuilder aEventBuilder, ISkRefbookItem aItem ) {
    if( aItem == null ) {
      return;
    }
    aEventBuilder.eventParams().setValobj( EVPRMID_NEW_ATTRS, aItem.attrs() );
    aEventBuilder.eventParams().setValobj( EVPRMID_NEW_RIVETS, aItem.rivets() );
    MappedSkids linksMap = new MappedSkids();
    ISkClassInfo itemClassInfo = coreApi().sysdescr().getClassInfo( itemClassId() );
    for( String lid : itemClassInfo.links().list().ids() ) {
      linksMap.map().put( lid, aItem.getLinkSkids( lid ) );
    }
    aEventBuilder.eventParams().setValobj( EVPRMID_NEW_LINKS, linksMap );
  }

  // ------------------------------------------------------------------------------------
  // ISkRefbook
  //

  @Override
  public String itemClassId() {
    return attrs().getStr( ATRID_ITEM_CLASS_ID );
  }

  @Override
  public <T extends ISkRefbookItem> T findItem( String aItemId ) {
    return coreApi().objService().find( new Skid( itemClassId(), aItemId ) );
  }

  @Override
  public ISkidList listItemIds() {
    return coreApi().objService().listSkids( itemClassId(), false );
  }

  @Override
  public <T extends ISkRefbookItem> IStridablesList<T> listItems() {
    return new StridablesList<>( coreApi().objService().listObjs( itemClassId(), false ) );
  }

  @Override
  public ISkRefbookItem defineItem( IDtoFullObject aItemInfo ) {
    // check preconditions
    ISkRefbookItem oldItem = coreApi().objService().find( new Skid( itemClassId(), aItemInfo.strid() ) );
    SkExtServiceRefbooks rbServ = (SkExtServiceRefbooks)coreApi().services().getByKey( ISkRefbookService.SERVICE_ID );
    TsValidationFailedRtException.checkError( rbServ.svs().validator().canDefineItem( this, aItemInfo, oldItem ) );
    // prepare event
    SkEventBuilder eventBuilder = new SkEventBuilder();
    eventBuilder.setEventGwid( Gwid.createEvent( classId(), strid(), EVID_REFBOOK_ITEM_CHANGE ) );
    fillEventWithOldValues( eventBuilder, oldItem );
    // create/edit item
    rbServ.pauseCoreValidationAndEvents();
    Skid skid = new Skid( itemClassId(), aItemInfo.strid() );
    ISkRefbookItem rbItem;
    try {
      rbItem = DtoFullObject.defineFullObject( coreApi(), aItemInfo );
      fillEventWithNewValues( eventBuilder, rbItem );
    }
    finally {
      rbServ.resumeCoreValidationAndEvents();
    }
    // fire event
    ECrudOp op = oldItem != null ? ECrudOp.EDIT : ECrudOp.CREATE;
    eventBuilder.eventParams().setValobj( EVPRMID_CRUD_OP, op );
    eventBuilder.eventParams().setValobj( EVPRMID_ITEM_SKID, skid );
    rbServ.eventer.fireItemsChanged( strid(), new SingleItemList<>( eventBuilder.getEvent() ) );
    return rbItem;
  }

  @Override
  public void removeItem( String aItemId ) {
    // check preconditions
    SkExtServiceRefbooks rs = (SkExtServiceRefbooks)coreApi().services().getByKey( ISkRefbookService.SERVICE_ID );
    TsValidationFailedRtException.checkError( rs.svs().validator().canRemoveItem( this, aItemId ) );
    // prepare event
    ISkRefbookItem oldItem = coreApi().objService().find( new Skid( itemClassId(), aItemId ) );
    SkEventBuilder eventBuilder = new SkEventBuilder();
    eventBuilder.setEventGwid( Gwid.createEvent( classId(), strid(), EVID_REFBOOK_ITEM_CHANGE ) );
    fillEventWithOldValues( eventBuilder, oldItem );
    // remove item
    Skid skid = new Skid( itemClassId(), aItemId );
    rs.pauseCoreValidationAndEvents();
    try {
      coreApi().objService().removeObject( skid );
    }
    finally {
      rs.resumeCoreValidationAndEvents();
    }
    // inform on item removal event
    eventBuilder.eventParams().setValobj( EVPRMID_CRUD_OP, ECrudOp.REMOVE );
    eventBuilder.eventParams().setValobj( EVPRMID_ITEM_SKID, skid );
    rs.eventer.fireItemsChanged( strid(), new SingleItemList<>( eventBuilder.getEvent() ) );
  }

  @Override
  public void removeAllItems() {
    IStridablesList<ISkRefbookItem> items = listItems();
    // check preconditions
    SkExtServiceRefbooks rs = (SkExtServiceRefbooks)coreApi().services().getByKey( ISkRefbookService.SERVICE_ID );
    for( ISkRefbookItem rbi : items ) {
      TsValidationFailedRtException.checkError( rs.svs().validator().canRemoveItem( this, rbi.id() ) );
    }
    IListEdit<SkEvent> events = new ElemArrayList<>( items.size() );
    rs.pauseCoreValidationAndEvents();
    try {
      // remove item one by one
      for( ISkRefbookItem rbi : items ) {
        SkEventBuilder eventBuilder = new SkEventBuilder();
        eventBuilder.setEventGwid( Gwid.createEvent( classId(), strid(), EVID_REFBOOK_ITEM_CHANGE ) );
        fillEventWithOldValues( eventBuilder, rbi );
        Skid skid = new Skid( itemClassId(), rbi.id() );
        eventBuilder.eventParams().setValobj( EVPRMID_CRUD_OP, ECrudOp.REMOVE );
        eventBuilder.eventParams().setValobj( EVPRMID_ITEM_SKID, skid );
        // remove item
        coreApi().objService().removeObject( skid );
        events.add( eventBuilder.getEvent() );
      }
      // fire removal events at once
      rs.eventer.fireItemsChanged( strid(), events );
    }
    finally {
      rs.resumeCoreValidationAndEvents();
    }
  }

}
