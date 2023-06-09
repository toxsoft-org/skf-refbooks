package org.toxsoft.skf.refbooks.gui.km5;

import org.toxsoft.core.tsgui.m5.IM5Domain;
import org.toxsoft.core.tslib.coll.primtypes.IStringList;
import org.toxsoft.skf.refbooks.lib.ISkRefbookService;
import org.toxsoft.uskat.core.connection.ISkConnection;
import org.toxsoft.uskat.core.gui.km5.KM5AbstractContributor;

/**
 * M5 models contributor for {@link ISkRefbookService} entities: refbook itself and refbook items classes.
 *
 * @author hazard157
 * @author dima // ts4 transition
 */
public class KM5UnitRefbooks
    extends KM5AbstractContributor {

  public KM5UnitRefbooks( ISkConnection aConn, IM5Domain aDomain ) {
    super( aConn, aDomain );
    // TODO Auto-generated constructor stub
  }

  @Override
  protected IStringList papiCreateModels() {
    // TODO Auto-generated method stub
    return IStringList.EMPTY;
  }

  // protected KM5UnitRefbooks( String aUnitId ) {
  // super( aUnitId );
  // // TODO Auto-generated constructor stub
  // }
  //
  // @Override
  // protected void doInitModels() {
  // // модель самого понятия "справочник"
  // m5().addModel( new SkRefbookKM5Model( skConn() ) );
  // // модели элемента каждого справочника
  // ISkRefbookService rs = coreApi().getService( ISkRefbookService.SERVICE_ID );
  // IList<ISkRefbook> rbList = rs.listRefbooks();
  // for( ISkRefbook rb : rbList ) {
  // m5().addModel( new SkRefbookItemKM5Model( rb, skConn() ) );
  // }
  // }

}
