package org.toxsoft.skf.refbooks.gui.glib;

import static org.toxsoft.skf.refbooks.gui.glib.ISkResources.*;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.graphics.icons.*;
import org.toxsoft.core.tsgui.utils.layout.*;
import org.toxsoft.core.tslib.bricks.strid.more.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.gui.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.api.evserv.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.gui.glib.*;

/**
 * Panel to edit refbook items.
 * <p>
 * Contains the {@link SashForm} with two parts:
 * <ul>
 * <li>left side - {@link RefbooksListPanel} to select refbook to edit it's items;</li>
 * <li>right side - tab 0 {@link RefbookItemsListPanel} edits list items of the refbook selected in left list.</li>
 * <li>right side - tab 1 {@link RefbookStructPanel} edits struct of the refbook selected in left list.</li>
 * </ul>
 *
 * @author hazard157
 * @author dima
 */
public class PanelRefbooksEditor
    extends SkPanel
    implements ISkRefbookServiceListener {

  /*
   * Визуальный список выбора журнала для редактирования
   */
  private final RefbooksListPanel     refbooksListPanel;
  private final RefbookStructPanel    structPanel;
  private final RefbookItemsListPanel itemsPanel;

  // private final IM5EntityPanel<IDtoRefbookInfo> refbookEditPane;
  // private final IInplaceEditorPanel inplaceEditor;
  // private final CTabItem refbookContentTab;
  // private final CTabFolder refbookEditorsFolder;

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
  public PanelRefbooksEditor( Composite aParent, ITsGuiContext aContext, IdChain aUsedConnId ) {
    super( aParent, aContext, aUsedConnId );
    this.setLayout( new BorderLayout() );
    SashForm sfMain = new SashForm( this, SWT.HORIZONTAL );
    refbooksListPanel = new RefbooksListPanel( sfMain, tsContext(), getUsedConnectionId(), false );

    // right pane
    CTabFolder refbookEditorsFolder = new CTabFolder( sfMain, SWT.NONE );

    // создаем панель редактирования содержимого справочника
    CTabItem refbookContentTab = new CTabItem( refbookEditorsFolder, SWT.NONE );
    refbookContentTab.setText( STR_REFBOOK_CONTENT );
    refbookContentTab.setToolTipText( STR_REFBOOK_CONTENT_D );
    refbookContentTab
        .setImage( iconManager().loadStdIcon( ISkRefbooksGuiConstants.ICONID_REFBOOK_ITEMS_LIST, EIconSize.IS_16X16 ) );
    itemsPanel = new RefbookItemsListPanel( refbookEditorsFolder, tsContext(), getUsedConnectionId(), false );
    refbookContentTab.setControl( itemsPanel );

    CTabItem refbookStructTab = new CTabItem( refbookEditorsFolder, SWT.NONE );
    refbookStructTab.setText( STR_REFBOOK_STRUCT );
    refbookStructTab.setToolTipText( STR_REFBOOK_STRUCT_D );
    refbookStructTab
        .setImage( iconManager().loadStdIcon( ISkRefbooksGuiConstants.ICONID_REFBOOK_EDIT, EIconSize.IS_16X16 ) );
    // создаем панель редактирования структуры справочника
    structPanel = new RefbookStructPanel( refbookEditorsFolder, tsContext(), getUsedConnectionId() );
    refbookStructTab.setControl( structPanel );

    // TODO перенести код в RefbookStructPanel
    // IM5Model<IDtoRefbookInfo> modelDto = m5().getModel( MID_RBED_DTO_REFBOOK_INFO, IDtoRefbookInfo.class );
    // IM5LifecycleManager<IDtoRefbookInfo> lmDto = modelDto.getLifecycleManager( skConn() );
    // ITsGuiContext ctxDto = new TsGuiContext( tsContext() );
    // refbookEditPane = modelDto.panelCreator().createEntityEditorPanel( ctxDto, lmDto );
    // refbookEditPane.setEditable( false );
    // AbstractContentPanel contentPanel = new InplaceContentM5EntityPanelWrapper<>( ctxDto, refbookEditPane );
    // inplaceEditor = new InplaceEditorContainerPanel( aContext, contentPanel );
    // // Закрепляем редактор структуры за новой закладкой
    // refbookStructTab.setControl( inplaceEditor.createControl( refbookEditorsFolder ) );

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
    ISkRefbook sel = refbooksListPanel.selectedItem();
    // извещаем панель редактора содержимого справочника
    // addNewRefbookEditor( sel );
    itemsPanel.setRefbook( sel );
    // if( inplaceEditor.isEditing() ) {
    // inplaceEditor.cancelAndFinishEditing();
    // }
    // извещаем панель редактора структуры справочника
    if( sel != null ) {
      structPanel.setRefbookId( sel.id() );
      // IDtoRefbookInfo dto = DtoRefbookInfo.of( sel );
      // refbookEditPane.setEntity( dto );
    }
    else {
      // refbookEditPane.setEntity( null );
    }
    // inplaceEditor.refresh();
  }

  /**
   * Handles refbook(es) changes in {@link ISkRefbookService}, is called from {@link ISkRefbookServiceListener}.
   *
   * @param aOp {@link ECrudOp} - the kind of change
   * @param aRefbookId String - affected refbook ID or <code>null</code> for batch changes {@link ECrudOp#LIST}
   */
  @Override
  public void onRefbookChanged( ECrudOp aOp, String aRefbookId ) {
    ISkRefbook sel = refbooksListPanel.selectedItem();
    // no selected refbook means that there is nothing in right panel, just refresh left panel
    if( sel == null ) {
      refbooksListPanel.refresh();
      return;
    }
    ignoreSelectionChange = true;
    try {
      refbooksListPanel.refresh();
      refbooksListPanel.setSelectedItem( sel );
      // if( inplaceEditor.isEditing() ) {
      // IDtoRefbookInfo dto = DtoRefbookInfo.of( sel );
      // refbookEditPane.setEntity( dto );
      // }
    }
    finally {
      ignoreSelectionChange = false;
    }

  }

  @Override
  public void onRefbookItemsChanged( String aRefbookId, IList<SkEvent> aEvents ) {
    // TODO Auto-generated method stub

  }

  /**
   * Создаем новый редактор справочника и вставляем его в folder
   *
   * @param aSelectedItem выбранный справочник
   */
  // protected void addNewRefbookEditor( ISkRefbook aSelectedItem ) {
  // // TODO перенести код в RefbookItemsListPanel
  // ISkRefbookService skRefbookService = coreApi().getService( ISkRefbookService.SERVICE_ID );
  // ISkRefbook skRefbook = skRefbookService.findRefbook( aSelectedItem.id() );
  // if( skRefbook == null ) {
  // TsDialogUtils.error( getShell(), FMT_ERR_NO_REFBOOK_BY_ID, aSelectedItem.id() );
  // return;
  // }
  // IM5Model<ISkRefbookItem> model = m5().getModel( aSelectedItem.itemClassId(), ISkRefbookItem.class );
  // // инициализация GUI
  // ITsGuiContext ctx = new TsGuiContext( tsContext() );
  // IM5LifecycleManager<ISkRefbookItem> lm = model.getLifecycleManager( skConn() );
  // IM5CollectionPanel<ISkRefbookItem> panel = model.panelCreator().createCollEditPanel( ctx, lm.itemsProvider(), lm );
  //
  // panel.createControl( refbookEditorsFolder );
  // // Закрепляем созданный справочник за закладкой
  // refbookContentTab.setControl( panel.getControl() );
  // }

}
