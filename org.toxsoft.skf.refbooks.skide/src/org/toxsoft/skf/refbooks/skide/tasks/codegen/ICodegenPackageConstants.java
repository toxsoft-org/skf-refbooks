package org.toxsoft.skf.refbooks.skide.tasks.codegen;

import static org.toxsoft.core.tslib.av.EAtomicType.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.skf.refbooks.skide.ISkidePluginRefbooksSharedResources.*;

import org.toxsoft.core.tsgui.valed.api.*;
import org.toxsoft.core.tslib.av.impl.*;
import org.toxsoft.core.tslib.av.metainfo.*;
import org.toxsoft.skide.task.codegen.gen.*;
import org.toxsoft.skide.task.codegen.valed.*;

/**
 * Package-private constants.
 *
 * @author hazard157
 */
interface ICodegenPackageConstants {

  String DEFAULT_GW_REFBOOKS_INTERFACE_NAME = "IGreenWorldRefbooks"; //$NON-NLS-1$

  IDataDef OPDEF_GW_REFBOOKS_INTERFACE_NAME = DataDef.create( "GwRefbooksInterfaceName", STRING, //$NON-NLS-1$
      TSID_NAME, STR_OPDEF_GW_REFBOOKS_INTERFACE_NAME, //
      TSID_DESCRIPTION, STR_OPDEF_GW_REFBOOKS_INTERFACE_NAME_D, //
      TSID_IS_NULL_ALLOWED, AV_FALSE, //
      IValedControlConstants.OPDEF_EDITOR_FACTORY_NAME, ValedAvStringJavaTypeName.FACTORY_NAME, //
      ValedStringJavaTypeName.OPDEF_CODEGEN_JAVA_TYPE, avValobj( ECodegenJavaType.INTERFACE ), //
      TSID_DEFAULT_VALUE, DEFAULT_GW_REFBOOKS_INTERFACE_NAME //
  );

}
