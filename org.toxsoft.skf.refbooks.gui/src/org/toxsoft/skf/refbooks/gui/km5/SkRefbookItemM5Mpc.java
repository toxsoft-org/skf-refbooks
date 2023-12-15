package org.toxsoft.skf.refbooks.gui.km5;

import static org.toxsoft.core.tsgui.bricks.actions.ITsStdActionDefs.*;
import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.skf.refbooks.gui.km5.ISkResources.*;

import java.text.*;
import java.util.*;

import org.toxsoft.core.jasperreports.gui.main.*;
import org.toxsoft.core.tsgui.bricks.actions.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.bricks.ctx.impl.*;
import org.toxsoft.core.tsgui.dialogs.*;
import org.toxsoft.core.tsgui.graphics.icons.*;
import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tsgui.m5.gui.mpc.impl.*;
import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tsgui.panels.toolbar.*;
import org.toxsoft.core.tslib.av.impl.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.core.tslib.utils.logs.impl.*;
import org.toxsoft.skf.refbooks.lib.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.conn.*;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.type.*;

/**
 * {@link MultiPaneComponentModown} implementation for {@link SkRefbookItemM5Model}.
 *
 * @author dima
 */
class SkRefbookItemM5Mpc
    extends MultiPaneComponentModown<ISkRefbookItem> {

  /**
   * формат для отображения времени печати
   */
  private static final String timeFormatString = "dd.MM.yy HH:mm"; //$NON-NLS-1$

  /**
   * Сам справочник
   */
  final ISkRefbook skRefbook;

  private static final DateFormat printTimeFmt = new SimpleDateFormat( timeFormatString );

  public static final String ACTID_PRINT_REFBOOK = "org.toxsoft.skf.refbooks.print_refbook"; //$NON-NLS-1$

  public static final ITsActionDef ACDEF_PRINT_REFBOOK = TsActionDef.ofPush1( ACTID_PRINT_REFBOOK, //
      TSID_NAME, STR_ACT_PRINT_REFBOOK, //
      TSID_DEFAULT_VALUE, STR_ACT_PRINT_REFBOOK_D, //
      TSID_ICON_ID, ITsStdIconIds.ICONID_DOCUMENT_PRINT //
  );

  public SkRefbookItemM5Mpc( ITsGuiContext aContext, ISkRefbook aRefbook, IM5Model<ISkRefbookItem> aModel,
      IM5ItemsProvider<ISkRefbookItem> aItemsProvider, IM5LifecycleManager<ISkRefbookItem> aLifecycleManager ) {
    super( aContext, aModel, aItemsProvider, aLifecycleManager );
    skRefbook = aRefbook;
    // TODO повесить на double click что-то полезное
    // IMultiPaneComponentConstants.OPDEF_DBLCLICK_ACTION_ID.setValue( tsContext().params(),
    // avStr( ACTID_PRINT_REFBOOK ) );
  }

  @Override
  protected ITsToolbar doCreateToolbar( ITsGuiContext aContext, String aName, EIconSize aIconSize,
      IListEdit<ITsActionDef> aActs ) {
    // добавляем кнопку печати
    aActs.add( ACDEF_SEPARATOR );
    aActs.add( ACDEF_PRINT_REFBOOK );
    return super.doCreateToolbar( aContext, aName, aIconSize, aActs );
  }

  @Override
  protected void doProcessAction( String aActionId ) {
    switch( aActionId ) {
      case ACTID_PRINT_REFBOOK: {
        printRefbook();
        break;
      }
      default:
        throw new TsNotAllEnumsUsedRtException( aActionId );
    }
  }

  @Override
  protected void doUpdateActionsState( boolean aIsAlive, boolean aIsSel, ISkRefbookItem aSel ) {
    toolbar().setActionEnabled( ACTID_PRINT_REFBOOK, true );
  }

  private void printRefbook() {
    try {
      ISkConnectionSupplier connectionSup = eclipseContext().get( ISkConnectionSupplier.class );
      ISkConnection connection = connectionSup.defConn();

      ITsGuiContext printContext = new TsGuiContext( tsContext() );

      String title = String.format( PRINT_REFBOOK_TITLE_FORMAT, skRefbook.nmName() );

      IJasperReportConstants.REPORT_TITLE_M5_ID.setValue( printContext.params(), AvUtils.avStr( title ) );

      // выясняем текущего пользователя FIXME
      // ISkUser user = S5ConnectionUtils.getConnectedUser( connection.coreApi() );
      // String userName = user.nmName().trim().length() > 0 ? user.nmName() : user.login();
      String userName = "root";

      IJasperReportConstants.LEFT_BOTTOM_STR_M5_ID.setValue( printContext.params(),
          AvUtils.avStr( AUTHOR_STR + userName ) );
      IJasperReportConstants.RIGHT_BOTTOM_STR_M5_ID.setValue( printContext.params(),
          AvUtils.avStr( DATE_STR + printTimeFmt.format( new Date() ) ) );

      printContext.params().setStr( IJasperReportConstants.REPORT_DATA_HORIZONTAL_TEXT_ALIGN_ID,
          HorizontalTextAlignEnum.LEFT.getName() );

      final JasperPrint jasperPrint = ReportGenerator.generateJasperPrint( printContext, model(), itemsProvider() );
      JasperReportDialog.showPrint( printContext, jasperPrint );
    }
    catch( Exception ex ) {
      LoggerUtils.errorLogger().error( ex );
      TsDialogUtils.error( getShell(), ex );
    }

  }

}
