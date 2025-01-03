package org.toxsoft.skf.refbooks.lib.impl;

import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.api.*;
import org.toxsoft.uskat.core.api.ugwis.*;
import org.toxsoft.uskat.core.impl.*;

/**
 * Initialization and utility methods.
 *
 * @author mvk
 */
public class SkfRefbookUtils {

  /**
   * Core handler to register all registered Sk-connection bound {@link ISkUgwiKind} when connection opens.
   */
  private static final ISkCoreExternalHandler coreRegistrationHandler = aCoreApi -> {
    ISkRefbookService refbookService = aCoreApi.findService( ISkRefbookService.SERVICE_ID );
    if( refbookService == null ) {
      refbookService = aCoreApi.addService( SkExtServiceRefbooks.CREATOR );
      // TODO:
    }
  };

  /**
   * The plugin initialization must be called before any action to access classes in this plugin.
   */
  public static void initialize() {
    // TODO:
    // TsValobjUtils.registerKeeperIfNone( ESkAlarmSeverity.KEEPER_ID, ESkAlarmSeverity.KEEPER );
    // SkCoreUtils.registerSkServiceCreator( SkAlarmService.CREATOR );
    SkCoreUtils.registerCoreApiHandler( coreRegistrationHandler );
  }

  /**
   * No subclasses.
   */
  private SkfRefbookUtils() {
    // nop
  }

}
