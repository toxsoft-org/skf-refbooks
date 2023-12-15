package org.toxsoft.skf.refbooks.mws.e4.uiparts;

import org.toxsoft.core.tsgui.widgets.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.mws.e4.services.*;
import org.toxsoft.uskat.core.gui.e4.uiparts.*;

/**
 * UIpart to display an editable list of the specified refbook items.
 *
 * @author hazard157
 */
public class UipartRefbookItemsEditor
    extends SkMwsAbstractPart {

  // ------------------------------------------------------------------------------------
  // SkMwsAbstractPart
  //

  @Override
  protected void doCreateContent( TsComposite aParent ) {

    // TODO Auto-generated method stub

  }

  // ------------------------------------------------------------------------------------
  // API
  //

  /**
   * Sets the refbook to edit items.
   * <p>
   * Method is called from {@link WsRefbooksManagementService#showRefbookUipart(String)}.
   *
   * @param aRefbook {@link ISkRefbook} - the refbook
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public void setRefbook( ISkRefbook aRefbook ) {
    TsNullArgumentRtException.checkNull( aRefbook );

    // TODO UipartRefbookItemsEditor.setRefbook()

  }

}
