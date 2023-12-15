package org.toxsoft.skf.refbooks.mws.e4.services;

import org.toxsoft.skf.refbooks.lib.*;

/**
 * Manages the refbooks perspecive logic, this is something like "perspective controller".
 *
 * @author hazard157
 */
public interface IWsRefbooksManagementService {

  /**
   * Opens new or activates already open UIpart with the specified refbook.
   * <p>
   * <code>null</code> argument is ignored.
   *
   * @param aRefbook {@link ISkRefbook} - the refbook to open, may be <code>null</code>
   */
  void showRefbookUipart( ISkRefbook aRefbook );

}
