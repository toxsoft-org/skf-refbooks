package org.toxsoft.skf.refbooks.gui.glib;

import static org.toxsoft.skf.refbooks.gui.km5.IKM5RefbooksConstants.*;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.widgets.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
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
 * <li>right side - {@link RefbookItemsListPanel} edits list of the refbook selected in left list.</li>
 * </ul>
 *
 * @author hazard157
 * @author dima
 */
public class PanelRefbookItemsEditor
    extends SkPanel
    implements ISkRefbookServiceListener {

  /*
   * Визуальный список выбора журнала для редактирования
   */
  private final RefbooksListPanel               refbooksListPanel;
  private final IM5EntityPanel<IDtoRefbookInfo> refbookEditPane;
  private final IInplaceEditorPanel             inplaceEditor;

  // private final RefbookItemsListPanel itemsPanel;

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
  public PanelRefbookItemsEditor( Composite aParent, ITsGuiContext aContext, IdChain aUsedConnId ) {
    super( aParent, aContext, aUsedConnId );
    this.setLayout( new BorderLayout() );
    SashForm sfMain = new SashForm( this, SWT.HORIZONTAL );
    refbooksListPanel = new RefbooksListPanel( sfMain, tsContext(), getUsedConnectionId(), false );

    // itemsPanel = new RefbookItemsListPanel()
    // right pane
    IM5Model<IDtoRefbookInfo> modelDto = m5().getModel( MID_RBED_DTO_REFBOOK_INFO, IDtoRefbookInfo.class );
    IM5LifecycleManager<IDtoRefbookInfo> lmDto = modelDto.getLifecycleManager( skConn() );
    ITsGuiContext ctxDto = new TsGuiContext( tsContext() );
    refbookEditPane = modelDto.panelCreator().createEntityEditorPanel( ctxDto, lmDto );
    refbookEditPane.setEditable( false );
    AbstractContentPanel contentPanel = new InplaceContentM5EntityPanelWrapper<>( ctxDto, refbookEditPane );
    inplaceEditor = new InplaceEditorContainerPanel( aContext, contentPanel );
    inplaceEditor.createControl( sfMain );

    sfMain.setWeights( 3000, 7000 );
    // setup
    refbooksListPanel.addTsSelectionListener( ( s, i ) -> whenRefbooksListSelectionChanges() );
    refbookService().eventer().addListener( this );
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
    if( inplaceEditor.isEditing() ) {
      inplaceEditor.cancelAndFinishEditing();
    }
    if( sel != null ) {
      IDtoRefbookInfo dto = DtoRefbookInfo.of( sel );
      refbookEditPane.setEntity( dto );
    }
    else {
      refbookEditPane.setEntity( null );
    }
    inplaceEditor.refresh();
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
    String selRefbookId = sel.id();
    ignoreSelectionChange = true;
    try {
      refbooksListPanel.refresh();
      sel = refbookService().findRefbook( selRefbookId );
      refbooksListPanel.setSelectedItem( sel );
      if( inplaceEditor.isEditing() ) {
        IDtoRefbookInfo dto = DtoRefbookInfo.of( sel );
        refbookEditPane.setEntity( dto );
      }
    }
    finally {
      ignoreSelectionChange = false;
    }

  }

  @Override
  public void onRefbookItemsChanged( String aRefbookId, IList<SkEvent> aEvents ) {
    // TODO Auto-generated method stub

  }

}
