package org.toxsoft.skf.refbooks.gui.km5;

import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tsgui.m5.model.impl.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.coll.primtypes.*;
import org.toxsoft.core.tslib.coll.primtypes.impl.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.lib.impl.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.km5.*;

/**
 * M5 models contributor for {@link ISkRefbookService} entities: refbook itself and refbook items classes.
 *
 * @author hazard157
 */
public class KM5RefbooksContributor
    extends KM5AbstractContributor {

  /**
   * Creator singleton.
   */
  public static final IKM5ContributorCreator CREATOR = KM5RefbooksContributor::new;

  /**
   * Constructor.
   *
   * @param aConn {@link ISkConnection} - the connection
   * @param aDomain {@link IM5Domain} - connection domain
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  KM5RefbooksContributor( ISkConnection aConn, IM5Domain aDomain ) {
    super( aConn, aDomain );
  }

  // ------------------------------------------------------------------------------------
  // KM5AbstractContributor
  //

  @Override
  protected IStringList papiCreateModels() {
    if( !coreApi().services().hasKey( ISkRefbookService.SERVICE_ID ) ) {
      coreApi().addService( SkExtServiceRefbooks.CREATOR );
    }
    IStringListEdit mids = new StringArrayList();
    // refbook objects model
    M5Model<ISkRefbook> rbModel = new SkRefbookM5Model( skConn() );
    m5().addModel( rbModel );
    mids.add( rbModel.id() );
    // refbook items objects models
    ISkRefbookService rbServ = coreApi().getService( ISkRefbookService.SERVICE_ID );
    for( ISkRefbook rb : rbServ.listRefbooks() ) {
      M5Model<ISkRefbookItem> itemModel = new SkRefbookItemM5Model( rb.itemClassId(), skConn() );
      m5().addModel( itemModel );
      mids.add( itemModel.id() );
    }
    return mids;
  }

  @Override
  protected void papiUpdateModel( ECrudOp aOp, String aClassId ) {
    // reinit items models
    ISkRefbookService rbServ = coreApi().getService( ISkRefbookService.SERVICE_ID );
    for( ISkRefbook rb : rbServ.listRefbooks() ) {
      M5Model<ISkRefbookItem> itemModel = new SkRefbookItemM5Model( rb.itemClassId(), skConn() );
      if( m5().models().hasKey( itemModel.id() ) ) {
        m5().replaceModel( itemModel );
      }
      else {
        m5().addModel( itemModel );
      }
    }
    // remove models of the removed refbooks
    IStringListEdit modelIdsToRemoveFromM5Domain = new StringArrayList();
    for( String mid : m5().models().keys() ) {
      if( ISkRefbookServiceHardConstants.isProbableRefbookItemClassId( mid ) ) {
        if( rbServ.findRefbookByItemClassId( mid ) == null ) {
          modelIdsToRemoveFromM5Domain.add( mid );
        }
      }
    }
    for( String mid : modelIdsToRemoveFromM5Domain ) {
      m5().removeModel( mid );
    }
  }

}
