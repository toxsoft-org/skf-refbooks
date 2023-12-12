package org.toxsoft.skf.refbooks.gui.km5;

import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.lib.impl.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.km5.*;

/**
 * Model for {@link ISkRefbookItem} of all refbooks.
 * <p>
 * The model ID must be the the refbook item class ID {@link ISkRefbook#itemClassId()}.
 *
 * @author hazard157
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
    // add all other properties of the particular refbook items
    for( IDtoAttrInfo pinf : rbInfo.attrInfos() ) {
      // TODO add refbook-specific attribute fields to M5-model
    }
    for( IDtoClobInfo pinf : rbInfo.clobInfos() ) {
      // TODO add refbook-specific cCLOB fields to M5-model
    }
    for( IDtoRivetInfo pinf : rbInfo.rivetInfos() ) {
      // TODO add refbook-specific rivet fields to M5-model
    }
    for( IDtoLinkInfo pinf : rbInfo.linkInfos() ) {
      // TODO add refbook-specific link fields to M5-model
    }
  }

  @Override
  protected IM5LifecycleManager<ISkRefbookItem> doCreateLifecycleManager( Object aMaster ) {
    return new SkRefbookItemM5LifecycleManager( this, ISkConnection.class.cast( aMaster ) );
  }

}
