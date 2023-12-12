package org.toxsoft.skf.refbooks.gui.km5;

import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.skf.refbooks.gui.ISkRefbooksGuiConstants.*;
import static org.toxsoft.skf.refbooks.gui.km5.IKM5RefbooksConstants.*;
import static org.toxsoft.skf.refbooks.gui.km5.ISkResources.*;

import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tsgui.m5.model.impl.*;
import org.toxsoft.core.tslib.av.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.km5.*;

/**
 * M5-model of {@link ISkRefbook}.
 *
 * @author hazard157
 */
public class SkRefbookM5Model
    extends KM5ModelBasic<ISkRefbook> {

  /**
   * Attribute {@link ISkRefbook#id()}
   */
  public final IM5AttributeFieldDef<ISkRefbook> REFBOOK_ID = new M5AttributeFieldDef<>( FID_REFBOOK_ID, DDEF_IDPATH, //
      TSID_NAME, STR_REFBOOK, //
      TSID_DESCRIPTION, STR_REFBOOK_D, //
      TSID_ICON_ID, ICONID_REFBOOK //
  ) {

    protected IAtomicValue doGetFieldValue( ISkRefbook aEntity ) {
      return avStr( aEntity.id() );
    }

  };

  /**
   * Constructor.
   *
   * @param aConn {@link ISkConnection} - Sk-connection to be used in constructor
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public SkRefbookM5Model( ISkConnection aConn ) {
    super( MID_KM5RB_REFBOOK, ISkRefbook.class, aConn );
    addFieldDefs( REFBOOK_ID, NAME, DESCRIPTION );
  }

  @Override
  protected IM5LifecycleManager<ISkRefbook> doCreateLifecycleManager( Object aMaster ) {
    return new SkRefbookM5LifecycleManager( this, ISkConnection.class.cast( aMaster ) );
  }

}
