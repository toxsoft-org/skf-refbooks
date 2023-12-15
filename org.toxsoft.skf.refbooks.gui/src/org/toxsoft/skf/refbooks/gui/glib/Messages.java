package org.toxsoft.skf.refbooks.gui.glib;

import org.eclipse.osgi.util.*;

public class Messages
    extends NLS {

  private static final String BUNDLE_NAME = Messages.class.getPackageName() + ".messages"; //$NON-NLS-1$
  public static String        FMT_ERR_NO_REFBOOK_BY_ID;
  public static String        STR_N_REFBOOK_CONTENT;
  public static String        STR_N_REFBOOK_STRUCT;
  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
  }
}
