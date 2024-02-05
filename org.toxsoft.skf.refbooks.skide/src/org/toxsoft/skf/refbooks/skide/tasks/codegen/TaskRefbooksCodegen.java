package org.toxsoft.skf.refbooks.skide.tasks.codegen;

import static org.toxsoft.core.tslib.bricks.gentask.IGenericTaskConstants.*;
import static org.toxsoft.core.tslib.utils.TsLibUtils.*;
import static org.toxsoft.skf.refbooks.skide.ISkidePluginRefbooksSharedResources.*;
import static org.toxsoft.skf.refbooks.skide.tasks.codegen.ICodegenPackageConstants.*;
import static org.toxsoft.skide.task.codegen.gen.ICodegenConstants.*;
import static org.toxsoft.skide.task.codegen.gen.impl.CodegenUtils.*;

import org.toxsoft.core.tslib.bricks.ctx.*;
import org.toxsoft.core.tslib.bricks.strid.coll.impl.*;
import org.toxsoft.core.tslib.bricks.strid.impl.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.impl.*;
import org.toxsoft.core.tslib.utils.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.skide.main.*;
import org.toxsoft.skide.core.api.*;
import org.toxsoft.skide.core.api.tasks.*;
import org.toxsoft.skide.task.codegen.gen.*;
import org.toxsoft.skide.task.codegen.main.*;
import org.toxsoft.uskat.core.api.sysdescr.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;
import org.toxsoft.uskat.core.gui.conn.*;

/**
 * SkIDE task {@link CodegenTaskProcessor} runner for {@link SkideUnitRefbooks}.
 *
 * @author hazard157
 */
public class TaskRefbooksCodegen
    extends AbstractSkideUnitTaskSync {

  private static final String PREFIX_REFBOOK = "RBID";   //$NON-NLS-1$
  private static final String PREFIX_ITEM    = "ITEMID"; //$NON-NLS-1$

  private static final IMap<ESkClassPropKind, String> PROP_PREFIX_MAP;

  static {
    IMapEdit<ESkClassPropKind, String> map = new ElemMap<>();
    map.put( ESkClassPropKind.ATTR, "RBATRID" ); //$NON-NLS-1$
    map.put( ESkClassPropKind.RIVET, "RBRIVID" ); //$NON-NLS-1$
    map.put( ESkClassPropKind.LINK, "RBLNKID" ); //$NON-NLS-1$
    map.put( ESkClassPropKind.CLOB, "RBCLBID" ); //$NON-NLS-1$
    PROP_PREFIX_MAP = map;
  }

  /**
   * Constructor.
   *
   * @param aOwnerUnit {@link AbstractSkideUnit} - the owner unit
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public TaskRefbooksCodegen( AbstractSkideUnit aOwnerUnit ) {
    super( aOwnerUnit, CodegenTaskProcessor.INSTANCE.taskInfo(),
        new StridablesList<>( OPDEF_GW_REFBOOKS_INTERFACE_NAME, OPDEF_GW_IS_ITEMS_INCLUDED ) );
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //

  private static void writeItemClassProps( ISkRefbook aRb, ISkClassProps<?> aProps,
      IJavaConstantsInterfaceWriter aJw ) {
    String prefix = PROP_PREFIX_MAP.getByKey( aProps.kind() );
    for( IDtoClassPropInfoBase prop : aProps.listSelf() ) {
      String cnRbId = idpath2ConstName( aRb.id() );
      String cnPropId = idpath2ConstName( prop.id() );
      String cn = String.format( "%s_%s___%s", prefix, cnRbId, cnPropId ); //$NON-NLS-1$
      aJw.addConstString( cn, prop.id(), prop.nmName() );
    }
  }

  private static void writeRefbookStruct( ISkRefbook aRb, IJavaConstantsInterfaceWriter aJw ) {
    ISkClassInfo cinf = aRb.coreApi().sysdescr().getClassInfo( aRb.itemClassId() );
    for( ESkClassPropKind k : PROP_PREFIX_MAP.keys() ) {
      ISkClassProps<?> props = cinf.props( k );
      writeItemClassProps( aRb, props, aJw );
    }
  }

  private static void writeRefbookItems( ISkRefbook aRb, IJavaConstantsInterfaceWriter aJw ) {
    for( ISkRefbookItem item : aRb.listItems() ) {
      String cnRbId = idpath2ConstName( aRb.id() );
      String cnItemId = idpath2ConstName( item.id() );
      String cn = String.format( "%s_%s___%s", PREFIX_ITEM, cnRbId, cnItemId ); //$NON-NLS-1$
      aJw.addConstString( cn, item.id(), item.nmName() );
    }
  }

  // ------------------------------------------------------------------------------------
  // AbstractSkideUnitTaskSync
  //

  @Override
  protected void doRunSync( ITsContextRo aInput, ITsContext aOutput ) {
    ILongOpProgressCallback lop = REFDEF_IN_PROGRESS_MONITOR.getRef( aInput );
    ICodegenEnvironment codegenEnv = REFDEF_CODEGEN_ENV.getRef( aInput );
    String interfaceName = OPDEF_GW_REFBOOKS_INTERFACE_NAME.getValue( getCfgOptionValues() ).asString();
    boolean isItemsIncluded = OPDEF_GW_IS_ITEMS_INCLUDED.getValue( getCfgOptionValues() ).asBool();
    IJavaConstantsInterfaceWriter jw = codegenEnv.createJavaInterfaceWriter( interfaceName );
    ISkConnectionSupplier cs = tsContext().get( ISkConnectionSupplier.class );
    ISkRefbookService rbServ = cs.defConn().coreApi().getService( ISkRefbookService.SERVICE_ID );
    // write refbooks
    for( ISkRefbook rb : rbServ.listRefbooks() ) {
      // refbook header
      jw.addCommentLine( StridUtils.printf( StridUtils.FORMAT_ID_NAME, rb ) );
      String cn = makeJavaConstName( PREFIX_REFBOOK, rb.id() );
      jw.addConstString( cn, rb.id(), EMPTY_STRING );
      // refbook structure and items
      writeRefbookStruct( rb, jw );
      if( isItemsIncluded ) {
        writeRefbookItems( rb, jw );
      }
      jw.addSeparatorLine();
    }
    jw.writeFile();
    lop.finished( ValidationResult.info( FMT_INFO_JAVA_INTERFACE_WAS_GENERATED, interfaceName ) );
  }

}
