package org.toxsoft.skf.refbooks.skide.tasks.upload;

import static org.toxsoft.core.tslib.bricks.gentask.IGenericTaskConstants.*;
import static org.toxsoft.skf.refbooks.skide.ISkidePluginRefbooksSharedResources.*;
import static org.toxsoft.skide.plugin.exconn.main.UploadToServerTaskProcessor.*;

import org.toxsoft.core.tslib.bricks.ctx.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.strid.coll.impl.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.utils.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.lib.impl.*;
import org.toxsoft.skf.refbooks.skide.main.*;
import org.toxsoft.skide.core.api.*;
import org.toxsoft.skide.core.api.tasks.*;
import org.toxsoft.skide.plugin.exconn.main.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.impl.dto.*;

/**
 * SkIDE task {@link UploadToServerTaskProcessor} runner for {@link SkideUnitRefbooks}.
 *
 * @author hazard157
 */
public class TaskRefbooksUpload
    extends AbstractSkideUnitTaskSync {

  private final RefbooksUploadSelectionRules uploadRules = new RefbooksUploadSelectionRules();

  private ISkCoreApi srcCoreApi  = null;
  private ISkCoreApi destCoreApi = null;

  private int uploadedRefbooksCount = 0;
  private int uploadedItemsCount    = 0;

  /**
   * Constructor.
   *
   * @param aOwnerUnit {@link AbstractSkideUnit} - the owner unit
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public TaskRefbooksUpload( AbstractSkideUnit aOwnerUnit ) {
    super( aOwnerUnit, UploadToServerTaskProcessor.INSTANCE.taskInfo(), //
        new StridablesList<>( /* No cfg ops */ ) );
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  private void uploadRefbooks() {
    ISkRefbookService srcRbServ = srcCoreApi.getService( ISkRefbookService.SERVICE_ID );
    ISkRefbookService destRbServ = destCoreApi.getService( ISkRefbookService.SERVICE_ID );
    // iterate over all source refbooks
    IStridablesList<ISkRefbook> llSourceRefbooks = srcRbServ.listRefbooks();
    for( ISkRefbook rbSrc : llSourceRefbooks ) {
      if( !uploadRules.isRefbookToBeUploaded( rbSrc.id() ) ) { // bypass refbook if not marked for upload
        continue;
      }
      // clear existing refbook if needed
      ISkRefbook rbDest = destRbServ.findRefbook( rbSrc.id() );
      if( rbDest != null && uploadRules.isRefbookToBeClearedBeforeUpload( rbDest.id() ) ) {
        rbDest.removeAllItems();
      }
      // create/edit refbook
      IDtoRefbookInfo dtoRefbook = DtoRefbookInfo.of( rbSrc );
      rbDest = destRbServ.defineRefbook( dtoRefbook );
      ++uploadedRefbooksCount;
      // upload items
      for( ISkRefbookItem rbi : rbSrc.listItems() ) {
        IDtoFullObject dtoItem = DtoFullObject.createDtoFullObject( rbi.skid(), srcCoreApi );
        rbDest.defineItem( dtoItem );
        ++uploadedItemsCount;
      }
    }
    // remove destination refbooks as specified
    IStridablesList<ISkRefbook> llDestRefbooks = destRbServ.listRefbooks();
    for( ISkRefbook rbDest : llDestRefbooks ) {
      if( !llSourceRefbooks.hasKey( rbDest.id() ) ) { // only consider refbooks NOT present in source RBservice
        if( uploadRules.isExistingRefbookToBeRemoved( rbDest.id() ) ) { // remove specified refbooks
          destRbServ.removeRefbook( rbDest.id() );
        }
      }
    }
  }

  // ------------------------------------------------------------------------------------
  // AbstractSkideUnitTaskSync
  //

  @SuppressWarnings( "boxing" )
  @Override
  protected void doRunSync( ITsContextRo aInput, ITsContext aOutput ) {
    ILongOpProgressCallback lop = REFDEF_IN_PROGRESS_MONITOR.getRef( aInput );
    uploadedRefbooksCount = 0;
    uploadedItemsCount = 0;
    uploadRules.loadFromOptions( aInput.params() );
    ISkConnection srcConn = tsContext().get( ISkConnectionSupplier.class ).defConn();
    srcCoreApi = srcConn.coreApi();
    ISkConnection destConn = REFDEF_IN_OPEN_SK_CONN.getRef( aInput );
    destCoreApi = destConn.coreApi();
    uploadRefbooks();
    lop.finished( ValidationResult.info( FMT_INFO_REFBOOKS_UPLOADED, uploadedRefbooksCount, uploadedItemsCount ) );
  }

}
