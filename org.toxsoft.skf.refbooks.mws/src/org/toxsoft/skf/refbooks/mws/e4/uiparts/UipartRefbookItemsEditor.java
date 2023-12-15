package org.toxsoft.skf.refbooks.mws.e4.uiparts;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.utils.layout.*;
import org.toxsoft.core.tsgui.widgets.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.gui.glib.*;
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

  private Composite bkPanel;

  // ------------------------------------------------------------------------------------
  // SkMwsAbstractPart
  //
  @Override
  protected void doCreateContent( TsComposite aParent ) {
    bkPanel = new Composite( aParent, SWT.NONE );
    bkPanel.setLayout( new BorderLayout() );
  }

  // ------------------------------------------------------------------------------------
  // API
  //

  /**
   * Sets the refbook to edit items.
   * <p>
   * Method is called from {@link WsRefbooksManagementService#showRefbookUipart(ISkRefbook)}.
   *
   * @param aRefbook {@link ISkRefbook} - the refbook
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public void setRefbook( ISkRefbook aRefbook ) {
    TsNullArgumentRtException.checkNull( aRefbook );
    // создаем панель редактирования содержимого справочника
    RefbookItemsListPanel itemsPanel = new RefbookItemsListPanel( bkPanel, tsContext() );
    itemsPanel.setRefbook( aRefbook );
    itemsPanel.setLayoutData( BorderLayout.CENTER );
  }

}
