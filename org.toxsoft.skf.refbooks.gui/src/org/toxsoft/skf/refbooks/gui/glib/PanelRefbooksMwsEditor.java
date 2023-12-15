package org.toxsoft.skf.refbooks.gui.glib;

import static org.toxsoft.skf.refbooks.gui.glib.ISkResources.*;

import java.util.*;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.dialogs.*;
import org.toxsoft.core.tsgui.utils.layout.*;
import org.toxsoft.core.tslib.bricks.strid.more.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.api.evserv.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.gui.glib.*;

/**
 * Panel to edit refbook items in MWS.
 * <p>
 * Contains the {@link SashForm} with two parts:
 * <ul>
 * <li>left side - {@link RefbooksListPanel} to select refbook to edit it's items;</li>
 * <li>right side - tabs {@link RefbookItemsListPanel} edits list items of the refbook selected in left list.</li>
 * </ul>
 *
 * @author dima
 */
public class PanelRefbooksMwsEditor
    extends SkPanel
    implements ISkRefbookServiceListener {

  /*
   * Визуальный список выбора журнала для редактирования
   */
  private final RefbooksListPanel refbooksListPanel;

  private final CTabFolder refbookEditorsFolder;

  /**
   * When this flag is <code>true</code> selection events are ingored in the handler
   * {@link #whenRefbooksListSelectionChanges()}.
   * <p>
   * Flag set/reset in {@link #whenRefbooksListSelectionChanges()}.
   */
  private boolean ignoreSelectionChange = false;

  /**
   * Constructor.
   * <p>
   * Constructor stores reference to the context, does not creates copy.
   * <p>
   * The connection ID is the key to get connection from the {@link ISkConnectionSupplier#allConns()} map.
   *
   * @param aParent {@link Composite} - parent component
   * @param aContext {@link ITsGuiContext} - the context
   * @param aUsedConnId {@link IdChain} - ID of connection to be used
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public PanelRefbooksMwsEditor( Composite aParent, ITsGuiContext aContext, IdChain aUsedConnId ) {
    super( aParent, aContext, aUsedConnId );
    this.setLayout( new BorderLayout() );
    SashForm sfMain = new SashForm( this, SWT.HORIZONTAL );
    refbooksListPanel = new RefbooksListPanel( sfMain, tsContext(), getUsedConnectionId(), false );

    // right pane
    refbookEditorsFolder = new CTabFolder( sfMain, SWT.NONE );

    sfMain.setWeights( 3000, 7000 );
    // setup
    refbooksListPanel.addSelectionListener( ( s, i ) -> whenRefbooksListSelectionChanges() );
    refbookService().eventer().addListener( this );
    refbookEditorsFolder.setSelection( 0 );
  }

  // ------------------------------------------------------------------------------------
  // implementation
  //
  private ISkRefbookService refbookService() {
    return coreApi().getService( ISkRefbookService.SERVICE_ID );
  }

  /**
   * Handles selection change in the left panel {@link #refbooksListPanel}.
   */
  private void whenRefbooksListSelectionChanges() {
    if( ignoreSelectionChange ) {
      return;
    }
    ISkRefbook selectedRefbook = refbooksListPanel.selectedItem();
    // ищем в открытых закладках
    Optional<CTabItem> op = refbookTabItem( selectedRefbook );
    if( op.isPresent() ) {
      // справочник уже открыт, обновим его
      CTabItem refbookTabItem = op.get();
      // справочник уже открыт, просто активируем закладку
      refbookEditorsFolder.setSelection( refbookTabItem );
    }
    else {
      // добавим новый справочник
      addNewRefbookEditor( selectedRefbook );
    }
  }

  /**
   * Handles refbook(es) changes in {@link ISkRefbookService}, is called from {@link ISkRefbookServiceListener}.
   *
   * @param aOp {@link ECrudOp} - the kind of change
   * @param aRefbookId String - affected refbook ID or <code>null</code> for batch changes {@link ECrudOp#LIST}
   */
  @Override
  public void onRefbookChanged( ECrudOp aOp, String aRefbookId ) {
    // refresh left panel
    refbooksListPanel.refresh();
    updateRbIfOpen( aRefbookId );
  }

  private void updateRbIfOpen( String aRefbookId ) {
    ISkRefbook changedRefbook = refbookService().findRefbook( aRefbookId );
    // ищем в открытых закладках
    Optional<CTabItem> op = refbookTabItem( changedRefbook );
    if( op.isPresent() ) {
      // справочник уже открыт, обновим его
      CTabItem refbookTabItem = op.get();
      RefbookItemsListPanel panel = (RefbookItemsListPanel)refbookTabItem.getControl();
      panel.setRefbook( changedRefbook );
    }
  }

  private Optional<CTabItem> refbookTabItem( ISkRefbook changedRefbook ) {
    for( CTabItem refbookTabItem : refbookEditorsFolder.getItems() ) {
      if( refbookTabItem.getText().compareTo( changedRefbook.nmName() ) == 0 ) {
        return Optional.of( refbookTabItem );
      }
    }
    return Optional.ofNullable( null );
  }

  @Override
  public void onRefbookItemsChanged( String aRefbookId, IList<SkEvent> aEvents ) {
    updateRbIfOpen( aRefbookId );
  }

  /**
   * Создаем новый редактор справочника и вставляем его в folder
   *
   * @param aSelectedItem выбранный справочник
   */
  protected void addNewRefbookEditor( ISkRefbook aSelectedItem ) {
    CTabItem refbookTab = new CTabItem( refbookEditorsFolder, SWT.NONE | SWT.CLOSE );
    refbookTab.setText( aSelectedItem.nmName() );

    ISkRefbookService skRefbookService = coreApi().getService( ISkRefbookService.SERVICE_ID );
    ISkRefbook skRefbook = skRefbookService.findRefbook( aSelectedItem.id() );
    if( skRefbook == null ) {
      TsDialogUtils.error( getShell(), FMT_ERR_NO_REFBOOK_BY_ID, aSelectedItem.id() );
      return;
    }
    // создаем панель редактирования содержимого справочника
    RefbookItemsListPanel itemsPanel =
        new RefbookItemsListPanel( refbookEditorsFolder, tsContext(), getUsedConnectionId(), false );
    itemsPanel.setRefbook( aSelectedItem );
    refbookTab.setControl( itemsPanel );
  }

}
