package org.toxsoft.skf.refbooks.lib;

import org.toxsoft.core.tslib.bricks.events.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.time.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.bricks.validator.impl.*;
import org.toxsoft.core.tslib.gw.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.api.*;
import org.toxsoft.uskat.core.api.evserv.*;
import org.toxsoft.uskat.core.api.objserv.*;

/**
 * Refbooks support.
 *
 * @author hazard157
 */
public interface ISkRefbookService
    extends ISkService {

  /**
   * The service ID.
   */
  String SERVICE_ID = ISkHardConstants.SK_CORE_SERVICE_ID_PREFIX + ".Refbooks"; //$NON-NLS-1$

  /**
   * Finds the refbook.
   *
   * @param aRefbookId String - the refbook identifier
   * @return {@link ISkRefbookItem} - found refbook or <code>null</code> if none
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  ISkRefbook findRefbook( String aRefbookId );

  /**
   * Finds the refbook by the refbook item class identifier {@link ISkRefbook#itemClassId()}.
   *
   * @param aRefbookItemClassId String - refbook item class identifier
   * @return {@link ISkRefbookItem} - found refbook or <code>null</code> if none
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  ISkRefbook findRefbookByItemClassId( String aRefbookItemClassId );

  /**
   * Return all available refbooks.
   *
   * @return {@link IStridablesList}&lt;{@link ISkRefbook} &gt; - list of all refbooks
   */
  IStridablesList<ISkRefbook> listRefbooks();

  /**
   * Defines (either creates new or changes existing) refbook.
   * <p>
   * Argument must contain only self properties of the class, without parent properties. That is, as the
   * {@link ISkRefbookItem} is direct subclass of the {@link ISkObject}, argument must <b>not</b> contain any property
   * of the class {@link IGwHardConstants#GW_ROOT_CLASS_ID}.
   *
   * @param aDpuRefbookInfo {@link IDtoRefbookInfo} - information about refbook
   * @return {@link ISkRefbook} - created or edited refbook
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsValidationFailedRtException validation failed
   */
  ISkRefbook defineRefbook( IDtoRefbookInfo aDpuRefbookInfo );

  /**
   * Removes refbook.
   *
   * @param aRefbookId String - identifier of the refbook to be removed
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsValidationFailedRtException validation failed
   */
  void removeRefbook( String aRefbookId );

  /**
   * Returns the refbook editing history for specified time interval.
   * <p>
   * Note: the event has structure as specified in {@link ISkRefbookServiceHardConstants#EVDTO_REFBOOK_EDIT},
   *
   * @param aInterval {@link IQueryInterval} - query time interval
   * @param aRefbookId String - ID of the refbook
   * @return {@link ITimedList}&lt;{@link SkEvent}&gt; - list of the reuried events
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsItemNotFoundRtException no such refbook exists
   */
  ITimedList<SkEvent> queryRefbookEditHistory( IQueryInterval aInterval, String aRefbookId );

  // ------------------------------------------------------------------------------------
  // Service support

  /**
   * Returns the service validator.
   *
   * @return {@link ITsValidationSupport}&lt;{@link ISkObjectServiceValidator}&gt; - the service validator
   */
  ITsValidationSupport<ISkRefbookServiceValidator> svs();

  /**
   * Returns the service eventer.
   *
   * @return {@link ITsEventer}&lt;{@link ISkObjectServiceListener}&gt; - the service eventer
   */
  ITsEventer<ISkRefbookServiceListener> eventer();

  // ------------------------------------------------------------------------------------
  // Inline methods for convenience
  //

  @SuppressWarnings( "javadoc" )
  default ISkRefbook getRefbook( String aRefbookId ) {
    return TsItemNotFoundRtException.checkNull( findRefbook( aRefbookId ) );
  }

}
