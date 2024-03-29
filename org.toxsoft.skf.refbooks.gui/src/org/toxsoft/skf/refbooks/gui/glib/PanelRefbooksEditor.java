package org.toxsoft.skf.refbooks.gui.glib;

import static org.toxsoft.skf.refbooks.gui.ISkRefbooksGuiConstants.*;
import static org.toxsoft.skf.refbooks.gui.glib.ISkResources.*;
import static org.toxsoft.skf.refbooks.gui.km5.IKM5RefbooksConstants.*;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.core.tsgui.graphics.icons.*;
import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tsgui.m5.gui.panels.*;
import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tsgui.panels.inpled.*;
import org.toxsoft.core.tsgui.utils.layout.*;
import org.toxsoft.core.tslib.bricks.strid.more.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.skf.refbooks.lib.impl.*;
import org.toxsoft.uskat.core.api.evserv.*;
import org.toxsoft.uskat.core.gui.conn.*;
import org.toxsoft.uskat.core.gui.glib.*;

/**
 * Panel to edit refbook items.
 * <p>
 * Contains the {@link SashForm} with two parts:
 * <ul>
 * <li>left side - {@link RefbooksListPanel} to select refbook to edit it's items;</li>
 * <li>right side - tab 0: {@link RefbookItemsListPanel} edits list items of the refbook selected in left list.</li>
 * <li>right side - tab 1: inplace structure editor of the refbook selected in left list.</li>
 * </ul>
 *
 * @author hazard157
 * @author dima
 */
public class PanelRefbooksEditor
    extends SkPanel
    implements ISkRefbookServiceListener {

  private final RefbooksListPanel               refbooksListPanel;     // left pane: refbooks list editor
  private final RefbookItemsListPanel           itemsPanel;            // right tab 0: refbook items editor
  private final IInplaceEditorPanel             inplaceStructEditor;   // right tab 1: refbook structure editor
  private final IM5EntityPanel<IDtoRefbookInfo> refbookStructEditPane; // inplace editor content

  /**
   * When this flag is <code>true</code> selection events are ignored in the handler
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
    // left pane
    refbooksListPanel = new RefbooksListPanel( sfMain, tsContext(), getUsedConnectionId(), true );
    // right pane
    CTabFolder refbookEditorsFolder = new CTabFolder( sfMain, SWT.NONE );
    // tab 0: items editor
    CTabItem refbookContentTab = new CTabItem( refbookEditorsFolder, SWT.NONE );
    refbookContentTab.setText( STR_REFBOOK_CONTENT );
    refbookContentTab.setToolTipText( STR_REFBOOK_CONTENT_D );
    refbookContentTab.setImage( iconManager().loadStdIcon( ICONID_REFBOOK_ITEMS_LIST, EIconSize.IS_16X16 ) );
    itemsPanel = new RefbookItemsListPanel( refbookEditorsFolder, tsContext(), getUsedConnectionId(), false );
    refbookContentTab.setControl( itemsPanel );
    // tab 1: structure editor
    CTabItem refbookStructTab = new CTabItem( refbookEditorsFolder, SWT.NONE );
    refbookStructTab.setText( STR_REFBOOK_STRUCT );
    refbookStructTab.setToolTipText( STR_REFBOOK_STRUCT_D );
    refbookStructTab.setImage( iconManager().loadStdIcon( ICONID_REFBOOK_EDIT, EIconSize.IS_16X16 ) );
    // inplace editor content
    IM5Model<IDtoRefbookInfo> modelDto = m5().getModel( MID_RBED_DTO_REFBOOK_INFO, IDtoRefbookInfo.class );
    IM5LifecycleManager<IDtoRefbookInfo> lmDto = modelDto.getLifecycleManager( skConn() );
    ITsGuiContext ctxDto = new TsGuiContext( tsContext() );
    refbookStructEditPane = modelDto.panelCreator().createEntityEditorPanel( ctxDto, lmDto );
    refbookStructEditPane.setEditable( false );
    AbstractContentPanel contentPanel = new InplaceContentM5EntityPanelWrapper<>( ctxDto, refbookStructEditPane );
    // inplace editor itself
    inplaceStructEditor = new InplaceEditorContainerPanel( aContext, contentPanel );
    refbookStructTab.setControl( inplaceStructEditor.createControl( refbookEditorsFolder ) );
    // setup
    sfMain.setWeights( 3000, 7000 );
    refbooksListPanel.addTsSelectionListener( ( s, i ) -> whenRefbooksListSelectionChanges() );
    refbookService().eventer().addListener( this );
    refbookEditorsFolder.setSelection( 0 );
    inplaceStructEditor.refresh();
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
    // inform items editor
    itemsPanel.setRefbook( sel );
    if( inplaceStructEditor.isEditing() ) {
      inplaceStructEditor.cancelAndFinishEditing();
    }
    // inform structure editor
    if( sel != null ) {
      IDtoRefbookInfo dto = DtoRefbookInfo.of( sel );
      refbookStructEditPane.setEntity( dto );
    }
    else {
      refbookStructEditPane.setEntity( null );
    }
    inplaceStructEditor.refresh();
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
      if( inplaceStructEditor.isEditing() ) {
        IDtoRefbookInfo dto = DtoRefbookInfo.of( sel );
        refbookStructEditPane.setEntity( dto );
      }
      inplaceStructEditor.refresh();
    }
    finally {
      ignoreSelectionChange = false;
    }
  }

  @Override
  public void onRefbookItemsChanged( String aRefbookId, IList<SkEvent> aEvents ) {
    // nop
  }

}
