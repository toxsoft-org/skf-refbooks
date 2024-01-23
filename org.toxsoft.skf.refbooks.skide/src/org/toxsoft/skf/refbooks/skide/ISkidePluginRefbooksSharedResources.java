package org.toxsoft.skf.refbooks.skide;

import org.toxsoft.skf.refbooks.skide.main.*;
import org.toxsoft.skf.refbooks.skide.tasks.codegen.*;

/**
 * Localizable resources.
 *
 * @author hazard157
 */
@SuppressWarnings( "javadoc" )
public interface ISkidePluginRefbooksSharedResources {

  /**
   * SkidePluginProject
   */
  String STR_SKIDE_PLUGIN_REFBOOKS   = Messages.getString( "STR_SKIDE_PLUGIN_REFBOOKS" );   //$NON-NLS-1$
  String STR_SKIDE_PLUGIN_REFBOOKS_D = Messages.getString( "STR_SKIDE_PLUGIN_REFBOOKS_D" ); //$NON-NLS-1$

  /**
   * {@link SkideUnitRefbooks}
   */
  String STR_SKIDE_REFBOOKS   = Messages.getString( "STR_SKIDE_REFBOOKS" );   //$NON-NLS-1$
  String STR_SKIDE_REFBOOKS_D = Messages.getString( "STR_SKIDE_REFBOOKS_D" ); //$NON-NLS-1$

  /**
   * ICodegenPackageConstants
   */
  String STR_OPDEF_GW_REFBOOKS_INTERFACE_NAME   = Messages.getString( "STR_OPDEF_GW_REFBOOKS_INTERFACE_NAME" );   //$NON-NLS-1$
  String STR_OPDEF_GW_REFBOOKS_INTERFACE_NAME_D = Messages.getString( "STR_OPDEF_GW_REFBOOKS_INTERFACE_NAME_D" ); //$NON-NLS-1$

  /**
   * {@link TaskRefbooksCodegen}
   */
  String FMT_INFO_JAVA_INTERFACE_WAS_GENERATED = Messages.getString( "FMT_INFO_JAVA_INTERFACE_WAS_GENERATED" ); //$NON-NLS-1$

}
