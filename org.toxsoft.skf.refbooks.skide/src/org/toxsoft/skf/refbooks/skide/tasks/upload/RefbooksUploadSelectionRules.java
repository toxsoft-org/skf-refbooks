package org.toxsoft.skf.refbooks.skide.tasks.upload;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.ctx.*;
import org.toxsoft.skide.core.api.tasks.*;

/**
 * Rules for selecting refbooks/items by the user for uploading to the server.
 *
 * @author hazard157
 */
public class RefbooksUploadSelectionRules {

  /**
   * TODO VALED to edit the rules
   */

  /**
   * Constructor.
   */
  public RefbooksUploadSelectionRules() {
    // TODO Auto-generated constructor stub
  }

  // ------------------------------------------------------------------------------------
  // API
  //

  public boolean isRefbookToBeUploaded( String aRefbookId ) {

    // TODO RefbooksUploadSelectionRules.isRefbookToBeUploaded()

    return true;
  }

  public boolean isRefbookToBeClearedBeforeUpload( String aRefbookId ) {

    // TODO RefbooksUploadSelectionRules.isRefbookToBeUploaded()

    return true;
  }

  public boolean isExistingRefbookToBeRemoved( String aRefbookId ) {

    // TODO RefbooksUploadSelectionRules.isRefbookToBeUploaded()

    return false;
  }

  /**
   * Saves rules configuration values to the options set.
   * <p>
   * Common usage is to prepare option set to be saved to the storage
   * {@link AbstractSkideUnitTask#setCfgOptionValues(IOptionSet)}.
   *
   * @param aOps {@link IOptionSetEdit} - the editable option set
   */
  public void saveToOptions( IOptionSetEdit aOps ) {
    // TODO UserDefinedUploadClassesFilter.saveToOptions()
  }

  /**
   * Loads rules configuration values from the options set.
   * <p>
   * Common usage is to load values task the input arguments {@link ITsContext#params()}.
   *
   * @param aOps {@link IOptionSet} - the option set
   */
  public void loadFromOptions( IOptionSet aOps ) {
    // TODO UserDefinedUploadClassesFilter.loadFromOptions()
  }

}
