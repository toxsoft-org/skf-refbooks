package org.toxsoft.skf.refbooks.gui.km5;

import static org.toxsoft.core.tsgui.m5.IM5Constants.*;
import static org.toxsoft.core.tsgui.m5.gui.mpc.IMultiPaneComponentConstants.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;

import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.m5.*;
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
    // CLOBs
    for( IDtoClobInfo clobIinf : rbInfo.clobInfos() ) {
      IM5FieldDef<? extends ISkObject, String> fd = new KM5ClobFieldDef<>( clobIinf );
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

}
